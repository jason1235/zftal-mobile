package com.zfsoft.hrm.baseinfo.forminfo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.forminfo.dao.daointerface.IFormInfoMetadataDao;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoClass;
import com.zfsoft.hrm.baseinfo.forminfo.entities.FormInfoMetadata;
import com.zfsoft.hrm.baseinfo.forminfo.service.svcinterface.IFormInfoUtilService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.util.VerifyUtil;
import com.zfsoft.hrm.baseinfo.org.config.IOrgConstent;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.file.util.ImageDBUtil;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.TimeUtil;
import com.zfsoft.util.encrypt.Encrypt;

public class FormInfoUtilServiceImpl implements IFormInfoUtilService {

	private IFormInfoMetadataDao formInfoMetadataDao;
	private IDynaBeanService dynaBeanService; 
	
	@Override
	public DynaBean getDynaBeanByInfoClass(
			String infoClassId, String formInfoTypeId,String dept) {
		DynaBean dynaBean;
		List<FormInfoMetadata> formInfoMetadatas=formInfoMetadataDao.findByInfoClassOfType(formInfoTypeId, infoClassId);
		String defGh="";
		InfoClass infoClass=InfoClassCache.getInfoClass(infoClassId).clone();//生产动态bean
		String str = SubSystemHolder.getPropertiesValue("gh_auto_rule");
		boolean flag=StringUtil.isEmpty(str)||"off".equals(str);
		if (!flag&&"JBXXB".equals(infoClass.getIdentityName())) {
			if(StringUtil.isEmpty(dept))
				if("number".equals(str)){
					defGh = getDefGh("",null);
				}else if(str.indexOf(",")!=-1){
					String s[] = str.split(",");
					String start=s[0];
					Integer length = null;
					if(s.length>1){
						try{
							length = Integer.valueOf(s[1]);
						}catch(NumberFormatException e){}
					}
					defGh = getDefGh(start,length);
				}else{
					defGh = getDefGh(TimeUtil.getYear(),4);
				}
			else
				defGh = getDefGh(dept,4);
		}
		List<InfoProperty> properties=new ArrayList<InfoProperty>();
		for(FormInfoMetadata formInfoMetadata:formInfoMetadatas){
			formInfoMetadata.setInfoProperty(infoClass.getPropertyByName(formInfoMetadata.getInfoProperty().getFieldName()));
			formInfoMetadata.copyToValueInfoProperty();
			properties.add(formInfoMetadata.getInfoProperty());
		}
		
		
		infoClass.setProperties(properties);
		
		dynaBean=new DynaBean(infoClass);
		//对基本信息表的职工号进行特殊处理
		if ("JBXXB".equals(infoClass.getIdentityName())) {
			InfoProperty gh = infoClass.getPropertyByName("gh");
			if(gh==null){
				gh = InfoClassCache.getInfoClassByName("JBXXB").getPropertyByName("gh");
				properties.add(gh);
			}
			gh.setNeed(true);
			gh.setEditable(true);
			gh.setViewable(true);
			infoClass.setProperties(properties);
		}
		for(InfoProperty property:infoClass.getProperties()){//给动态Bean赋值
			dynaBean.setValue(property.getFieldName(), property.getDefaultValue());
			
			if(property.getEditable()){
				if (!StringUtil.isEmpty(defGh)&&"gh".equals(property.getFieldName())) {
					dynaBean.setValue(property.getFieldName(), defGh);
				}else if (!StringUtil.isEmpty(dept)&&"dwh".equals(property.getFieldName())) {
					dynaBean.setValue(property.getFieldName(), dept);
				}else{
					dynaBean.setValue(property.getFieldName(), property.getDefaultValue());
				}
			}
		}
		return dynaBean;
	}
	private String getDefGh(String start,Integer num){
		if(StringUtil.isEmpty(start)) start="";
		DynaBeanQuery query = new DynaBeanQuery(InfoClassCache.getInfoClass(IOrgConstent.BASEINFO_CLASS_ID));
		String number = "*";
		if(num!=null){
			number ="{"+num+"}";
		}
		String express = "regexp_like (gh,'^"+start+"[0-9]"+number+"$')";
		query.setExpress(express);
		query.setOrderStr("to_number(substr(gh,"+(start.length()+1)+")) desc");
		List<DynaBean> list = dynaBeanService.findList(query);
		if (list.size()>0) {
			try {
				String ghStr = list.get(0).getValue("gh").toString();
				int ghNum = ghStr.substring(start.length()).length();
				int gh = Integer.valueOf(ghStr.substring(start.length()));
				if(Integer.valueOf(gh).toString().length()<ghNum){
					num=ghNum;
				}
				gh++;
				String defGh=gh+"";
				if(num!=null){
//					if(gh<10*num){
						for (; defGh.length()<num;) {
							defGh="0"+defGh;
						}
						
//					}
				}else{}
				return start+defGh;
			} catch(Exception e) {
			}
		}else{
			int gh=1;
			String defGh=gh+"";
			if(num!=null){
				if(gh<10*num){
					for (; defGh.length()<num;) {
						defGh="0"+defGh;
					}
					
				}
			}else{
				defGh = "0001";
			}
			return start+defGh;
		}
		return "";
	}
	@Override
	public FormInfoClass getAddForm(FormInfoClass result) {
		//得到配置的登记表元数据
		List<FormInfoMetadata> formInfoMetadatas=formInfoMetadataDao.findByFormInfoTypeId(result.getFormInfoTypeId());
		result.setMetadatas(formInfoMetadatas);
		
		//获得登记表对应信息类的属性映射
		Map<String, List<InfoProperty>> infoClassPropMap = generateClassPropMap(formInfoMetadatas);
		
		//取得所有的信息类
		List<InfoClass> infoClazzes= InfoClassCache.getInfoClasses( IConstants.INFO_CATALOG_TYPE_DEFAULT );
		
		for( InfoClass infoClass : infoClazzes ) {
			if(infoClassPropMap.get(infoClass.getGuid())==null)continue;
			infoClass.setProperties(infoClassPropMap.get(infoClass.getGuid()));
			result.getInfoClazzes().add(infoClass);
		}
		
		return result;
	}
	
	@Override
	public FormInfoClass addDynaBean(FormInfoClass model,
			FormInfoClass formInfoClass,DynaBean dynaBean,HttpServletRequest request) {
		
		model.copy(formInfoClass);
		
		for(InfoProperty infoProperty:dynaBean.getClazz().getProperties()){
			dynaBean.setValue(infoProperty.getFieldName(), request.getParameter(infoProperty.getFieldName()));
		}
		
		dynaBean.setIndex(model.getDynaBeans().size());
		model.addDynaBean(dynaBean);//增加动态bean的登记表实例
	
		return model;
	}
	@Override
	public String modifyDynaBean(DynaBean dynaBean,HttpServletRequest request) {
		String str="";
		String value=null;
		for(InfoProperty infoProperty:dynaBean.getClazz().getProperties()){
			if(infoProperty.getEditable()){
				value=request.getParameter(infoProperty.getFieldName());
				if((value==null||value.trim().equals(""))&&infoProperty.getNeed()){
					str+=infoProperty.getName()+"不能为空;<br/>";
				}
				else{
					if(infoProperty.getFieldName().equals("gh")&&(value!=null&&!value.trim().equals(""))){
						Pattern pattern = Pattern.compile("^[0-9a-zA-Z]+$");
						Matcher matcher = pattern.matcher(value.toString());
						if(!matcher.matches()){
							str="工号只能由数字及字母组成;<br/>"+str;
							continue;
						}
					}
					String error=VerifyUtil.parse(infoProperty, value);
					if(!StringUtil.isEmpty(error)){
						VerifyUtil.parse(infoProperty, value);
						str+=error+";<br/>";
					}
					if(infoProperty.getFieldName().equals("zp")){
						dynaBean.setValue(infoProperty.getFieldName(), request.getParameter(infoProperty.getFieldName()));
					}else{
						dynaBean.setValue(infoProperty.getFieldName(), request.getParameter(infoProperty.getFieldName()));
					}
				}
			}
		}
		if(str.equals("")){
			return "success";
		}
		return str;
	}
	@Override
	public boolean findUser(FormInfoClass formInfoClass){
		String userName = "";
		DynaBean jbxx=null;
		for( DynaBean dynaBean : formInfoClass.getDynaBeans() ) {
			if ("JBXXB".equals(dynaBean.getClazz().getIdentityName())) {
				jbxx = dynaBean;
				if( dynaBean.getValue("xm") != null && !"".equals( dynaBean.getValue("xm") ) ) {
					userName = dynaBean.getValue("xm").toString();
					break;
				}
			}
		}
		if (jbxx!=null) {
			DynaBeanQuery query = new DynaBeanQuery(jbxx.getClazz());
			query.setExpress("xm = #{params.xm}");
			query.setParam("xm", userName);
			if(dynaBeanService.findCount(query)>0){
				dynaBeanService.findList(query);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String saveDynaBeans(FormInfoClass formInfoClass) {
		
		String staffid = "";
		String sfzh = "";
		
		for( DynaBean dynaBean : formInfoClass.getDynaBeans() ) {
			if( dynaBean.getValue("gh") != null && !"".equals( dynaBean.getValue("gh") ) ) {
				staffid = dynaBean.getValue("gh").toString();
				if ("1".equals(dynaBean.getValue("sfzjlxm").toString())) {
					sfzh = dynaBean.getValue("sfzjh").toString();
				}
				break;
			}
		}
		
		if( "".equals( staffid ) ) {
			throw new RuleException("职工号不得为空！");
		}
		
		dynaBeanService.addPerson( staffid );
		
		for(DynaBean dynaBean : formInfoClass.getDynaBeans()){
			dynaBean.setValue( "gh", staffid );
			dynaBean.setEditables(dynaBean.getClazz().getProperties());
//			dynaBean.setValue("dqztm", "11");
			dynaBeanService.addRecord( dynaBean );
		}

		formInfoClass.removeAllDynaBean();
		
		Map<String, String> param = new HashMap<String, String>();
		if (!StringUtils.isEmpty(sfzh)) {
			if (sfzh.length() < 6) {
				throw new RuleException("身份证长度格式不正确！");
			}
			param.put("mm", Encrypt.encrypt(sfzh.substring(sfzh.length() - 6)));
			param.put("zgh", staffid);
			formInfoMetadataDao.updateMm(param);
		}
		
		return staffid;
	}

	@Override
	public FormInfoClass getDynaBeans(String formInfoId) {
		return null;
	}
	@Override
	public void removeDynaBeanPic(List<DynaBean> dynaBeans) {
		for(DynaBean dynaBean : dynaBeans){
			removeDynaBeanPic(dynaBean);
		}
	}
	@Override
	public void removeDynaBeanPic(DynaBean dynaBean) {
		for(InfoProperty prop : dynaBean.getClazz().getProperties()){
			if("IMAGE".equals(prop.getFieldType())){
				if(dynaBean.getValues().get(prop.getFieldName())!=null){
					ImageDBUtil.deleteFileToDB(dynaBean.getValues().get(prop.getFieldName()).toString());
				}
			}
		}
	}
	
	private Map<String, List<InfoProperty>> generateClassPropMap(
			List<FormInfoMetadata> formInfoMetadatas) {
		Map<String,List<InfoProperty>> infoClassPropMap=new HashMap<String, List<InfoProperty>>();
		
		for(FormInfoMetadata formInfoMetadata:formInfoMetadatas){
			formInfoMetadata.setInfoProperty(InfoClassCache.getInfoClass(
					formInfoMetadata.getInfoProperty().getClassId() )
					.getPropertyByName(formInfoMetadata.getInfoProperty().getFieldName()));
			if(formInfoMetadata.getInfoProperty()==null)continue;
			formInfoMetadata.copyToValueInfoProperty();
			if(infoClassPropMap.get(formInfoMetadata.getInfoProperty().getClassId())==null){
				infoClassPropMap.put(formInfoMetadata
						.getInfoProperty().getClassId(), new ArrayList<InfoProperty>());
			}
			infoClassPropMap
			.get(formInfoMetadata
					.getInfoProperty().getClassId())
					.add(formInfoMetadata.getInfoProperty());
		}
		return infoClassPropMap;
	}
	
	
	public void setFormInfoMetadataDao(IFormInfoMetadataDao formInfoMetadataDao) {
		this.formInfoMetadataDao = formInfoMetadataDao;
	}
	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}
	
}
