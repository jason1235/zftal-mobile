package com.zfsoft.hrm.normal.resume.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.entities.AncdModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.html.ParseFactory;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanLogService;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.finfo.util.FormInfoUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.util.VerifyUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.ICodeConstants;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.core.util.SQLGenerateUtil;
import com.zfsoft.hrm.normal.info.entity.OverallInfo;
import com.zfsoft.hrm.normal.resume.dto.StaffInfoDto;
import com.zfsoft.hrm.normal.resume.service.svcinterface.IStaffInfoService;
import com.zfsoft.util.base.StringUtil;

/** 
 * 人员信息管理
 * @author jinjj
 * @date 2012-8-21 下午03:17:40 
 *  
 */
public class StaffInfoBatchAction extends HrmAction {

	private static final long serialVersionUID = 7508782995353592541L;
	private IStaffInfoService staffInfoService;
	private IDynaBeanService dynaBeanService;
	private IDynaBeanBusiness dynaBeanBusiness;
	private String classId;
	private String instanceId;
	private InfoClass clazz;
	private String modeType;//left一人至少一条 right记录为主
	private String closeField;//失效的选项
	private List<StaffInfoDto> staffInfoDtos=new ArrayList<StaffInfoDto>();
	private List<InfoProperty> viewables=new ArrayList<InfoProperty>();
	private List<InfoProperty> editables=new ArrayList<InfoProperty>();
	private PageList<StaffInfoDto> staffInfoDtoPageList=new PageList<StaffInfoDto>();
	private DynaBeanQuery query=new DynaBeanQuery();
	private OverallInfo overall=new OverallInfo();
	private DynaBean model;
	private String op="add";
	private String px="pxm";
	private String jbxxb="C393FE11C4DC8E46E040007F01003F39";
	private String ids;
	private String batchFieldName;
	boolean bol = false;
	private String asc;
	private String sortFieldName;
	
	private int showMore=-1;
	
	public String listContract() throws Exception{
		list();
		return LIST_PAGE;
	}
	
	/**
	 * 列表导出
	 * @return
	 * @throws Exception
	 */
	public String export() throws Exception{
		if(staffInfoDtoPageList.getPaginator()==null){
			staffInfoDtoPageList.setPaginator(new Paginator());
		}
		clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
		
		if (!StringUtil.isEmpty(sortFieldName)&&clazz.getPropertyByName(sortFieldName)!=null) {
			if("up".equals(asc)){
				px = sortFieldName;
			}else{
				px = sortFieldName+" desc";
			}
		}else{
			if(jbxxb.equals(classId)){   //个人概括按排序码排序
				px="o.pxm";
			}else{
				px="";
			}
		}
		
		WritableWorkbook wwb = Workbook.createWorkbook( getResponse().getOutputStream() );
		WritableSheet sheet1 = wwb.createSheet(clazz.getName(), 0);
		//头三行固定为 序号 工号 姓名
		int i=0;
		//序号批注设置为信息类guid 后续导入时使用该批注确定修改的信息类类型
		sheet1.addCell(new Label(0,0,"序号"));
		i++;
		viewables=new ArrayList<InfoProperty>();
		StringBuilder sbMoreConds=new StringBuilder();
		for (InfoProperty infoProperty : StaffInfoDto.getViewableProperties(clazz)) {
			if(!Type.PHOTO.equals(infoProperty.getFieldType())&&
					!Type.IMAGE.equals(infoProperty.getFieldType())&&
					!Type.FILE.equals(infoProperty.getFieldType())){
				viewables.add(infoProperty);
				sheet1.addCell(new Label(i,0,infoProperty.getName()));
				i++;
				
				String fieldName="t."+infoProperty.getFieldName();
				if(infoProperty.getVirtual()){
					fieldName = infoProperty.getReferFunc()+"("+infoProperty.getRefer()+")";
				}
				if(!("gh".equalsIgnoreCase(infoProperty.getFieldName())
						||"xm".equalsIgnoreCase(infoProperty.getFieldName())
						||"dwm".equalsIgnoreCase(infoProperty.getFieldName())
						||"dqztm".equalsIgnoreCase(infoProperty.getFieldName())
						||"bzlbm".equalsIgnoreCase(infoProperty.getFieldName()))){
					if(Type.DATE.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
							||Type.MONTH.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
							||Type.YEAR.equalsIgnoreCase(infoProperty.getTypeInfo().getName())){
						String[] values=getRequest().getParameterValues(infoProperty.getFieldName());
						if(values==null){
							continue;
						}
						if(StringUtils.isNotEmpty(values[0])){
							sbMoreConds.append(" and ");
							sbMoreConds.append(fieldName);
							sbMoreConds.append(">= to_date('");
							sbMoreConds.append(values[0]);
							sbMoreConds.append("','");
							sbMoreConds.append(infoProperty.getTypeInfo().getFormat());
							sbMoreConds.append("')");
						}
						if(StringUtils.isNotEmpty(values[1])){
							sbMoreConds.append(" and ");
							sbMoreConds.append(fieldName);
							sbMoreConds.append("<= to_date('");
							sbMoreConds.append(values[1]);
							sbMoreConds.append("','");
							sbMoreConds.append(infoProperty.getTypeInfo().getFormat());
							sbMoreConds.append("')");
						}
					}else{
						String value=getRequest().getParameter(infoProperty.getFieldName());
						if(StringUtils.isEmpty(value)){
							continue;
						}
						if(Type.CODE.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
								||Type.SIGLE_SEL.equalsIgnoreCase(infoProperty.getTypeInfo().getName())){
							sbMoreConds.append(" and ");
							sbMoreConds.append(fieldName);
							sbMoreConds.append("='");
							sbMoreConds.append(value);
							sbMoreConds.append("'");
						}else if(Type.NUMBER.equalsIgnoreCase(infoProperty.getTypeInfo().getName())){
							sbMoreConds.append(" and ");
							sbMoreConds.append(fieldName);
							sbMoreConds.append("=");
							sbMoreConds.append(value);
							sbMoreConds.append("");
						}else{
							sbMoreConds.append(" and ");
							sbMoreConds.append(fieldName);
							sbMoreConds.append(" like '%");
							sbMoreConds.append(value.replaceAll("'", "''"));
							sbMoreConds.append("%'");
						}
					}
				}
			}
		}
		
		editables=clazz.getEditables();
		query.setClazz(clazz);
		String express=getExpress();
		if(sbMoreConds.length()>0){
			express+=sbMoreConds.toString();
		}
		query.setExpress(express);
		query.setOrderStr(px);   //排序字段设值
		//出于内存考虑 导出设置最大值  最大不能超过9000行 后续考虑配置该值
		query.setPerPageSize(9000);
		if(StringUtil.isEmpty(modeType))modeType = "right";
		staffInfoDtos=staffInfoService.getStaffOverAllInfoPageList(classId,query,modeType);
		//response输出流处理
		//设置编码、类型、文件名
		getResponse().reset();
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/vnd.ms-excel");
		String useragent = getRequest().getHeader("user-agent");
		String disposition = DownloadFilenameUtil.fileDisposition(useragent, clazz.getName()+".xls");
		getResponse().setHeader("Content-Disposition", disposition);
		
		for (int j = 1; j <= staffInfoDtos.size(); j++) {
			i=0;
			StaffInfoDto dto = staffInfoDtos.get(j-1);
			sheet1.addCell(new Label(i,j,j+""));
			for(InfoProperty p:viewables){
				i++;
				String value;
				if ( Type.CODE.equals( p.getFieldType() )||
						Type.YEAR.equals(p.getFieldType())||
						Type.DATE.equals(p.getFieldType())||
						Type.MONTH.equals(p.getFieldType())) {
					value = dto.getValueMap().get(p.getClassFiledName());
				} else if ( Type.SIGLE_SEL.equals( p.getFieldType() ) ) {
					if ("1".equals(dto.getDynaBean().getValueString(p.getFieldName()))) {
						value="是";
					}else{
						value="否";
					}
				}else{
					value= dto.getValueMap().get(p.getClassFiledName());
				}
				sheet1.addCell(new Label(i,j,value));
			}
		}
		wwb.write();
		wwb.close();
		return null;
	}
	
	/**
	 * 加载信息数据
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception{
		if(!bol){
			if(getSession().getAttribute("OVERALL")==null){
				overall.setStatus("11");
			}else{
				overall=(OverallInfo)getSession().getAttribute("OVERALL");
			}
		}
		getSession().setAttribute("OVERALL", overall);
		if(staffInfoDtoPageList.getPaginator()==null){
			staffInfoDtoPageList.setPaginator(new Paginator());
		}
		Paginator paginator=staffInfoDtoPageList.getPaginator();
		clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
		if (!StringUtil.isEmpty(sortFieldName)&&sortFieldName.split("\\.").length==2) {
			String[] arr=sortFieldName.split("\\.");
			String fieldName;
			if("overall".equals(arr[0].toLowerCase())){
				fieldName="o."+arr[1];
			}else{
				fieldName="t."+arr[1];
			}
			if("up".equals(asc)){
				px = fieldName;
			}else{
				px = fieldName+" desc";
			}
		}else{
			px="o.pxm";
		}
			
		viewables=new ArrayList<InfoProperty>();
		List<InfoProperty> conditions=new ArrayList<InfoProperty>();
		Map<String,Object> valuesMap=new HashMap<String, Object>();
		
		StringBuilder sbMoreConds=new StringBuilder();
		for (InfoProperty infoProperty : StaffInfoDto.getViewableProperties(clazz)) {
			if(!Type.PHOTO.equals(infoProperty.getFieldType())&&
					!Type.IMAGE.equals(infoProperty.getFieldType())&&
					!Type.FILE.equals(infoProperty.getFieldType())){
				viewables.add(infoProperty);
				
				String fieldName="t."+infoProperty.getFieldName();
				if(infoProperty.getVirtual()){
					fieldName = infoProperty.getDisplayFormula("t");
				}
				if(!("gh".equalsIgnoreCase(infoProperty.getFieldName())
						||"xm".equalsIgnoreCase(infoProperty.getFieldName())
						||"dwm".equalsIgnoreCase(infoProperty.getFieldName())
						||"dqztm".equalsIgnoreCase(infoProperty.getFieldName())
						||"bzlbm".equalsIgnoreCase(infoProperty.getFieldName()))){
					conditions.add(infoProperty);
					if(Type.DATE.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
							||Type.MONTH.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
							||Type.YEAR.equalsIgnoreCase(infoProperty.getTypeInfo().getName())){
						String[] values=getRequest().getParameterValues(infoProperty.getFieldName());
						if(values==null){
							continue;
						}
						valuesMap.put(infoProperty.getFieldName(), values);
						if(StringUtils.isNotEmpty(values[0])){
							sbMoreConds.append(" and ");
							sbMoreConds.append(fieldName);
							sbMoreConds.append(">= to_date('");
							sbMoreConds.append(values[0]);
							sbMoreConds.append("','");
							sbMoreConds.append(infoProperty.getTypeInfo().getFormat());
							sbMoreConds.append("')");
						}
						if(StringUtils.isNotEmpty(values[1])){
							sbMoreConds.append(" and ");
							sbMoreConds.append(fieldName);
							sbMoreConds.append("<= to_date('");
							sbMoreConds.append(values[1]);
							sbMoreConds.append("','");
							sbMoreConds.append(infoProperty.getTypeInfo().getFormat());
							sbMoreConds.append("')");
						}
					}else{
						String value=getRequest().getParameter(infoProperty.getFieldName());
						if(StringUtils.isEmpty(value)){
							continue;
						}
						valuesMap.put(infoProperty.getFieldName(), value);
						if(Type.CODE.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
								||Type.SIGLE_SEL.equalsIgnoreCase(infoProperty.getTypeInfo().getName())){
							sbMoreConds.append(" and ");
							sbMoreConds.append(fieldName);
							sbMoreConds.append("='");
							sbMoreConds.append(value);
							sbMoreConds.append("'");
						}else if(Type.NUMBER.equalsIgnoreCase(infoProperty.getTypeInfo().getName())){
							sbMoreConds.append(" and ");
							sbMoreConds.append(fieldName);
							sbMoreConds.append("=");
							sbMoreConds.append(value);
							sbMoreConds.append("");
						}else{
							sbMoreConds.append(" and ");
							sbMoreConds.append(fieldName);
							sbMoreConds.append(" like '%");
							sbMoreConds.append(value.replaceAll("'", "''"));
							sbMoreConds.append("%'");
						}
					}
				}
			}
		}
		
		editables=clazz.getEditables();
		query.setClazz(clazz);
		String express=getExpress();
		if(sbMoreConds.length()>0){
			express+=sbMoreConds.toString();
		}
		query.setExpress(express);
		query.setOrderStr(px);   //排序字段设值
		if(StringUtil.isEmpty(modeType))modeType = "right";
		staffInfoDtos=staffInfoService.getStaffOverAllInfoPageList(classId,query,modeType);
		staffInfoDtoPageList.addAll(staffInfoDtos);
		paginator.setItems(query.getTotalItem());
		paginator.setItemsPerPage(query.getPerPageSize());
		paginator.setPage(query.getToPage());
		String permission = null;
		if (getUser().getJsdms().contains("admin")) {
			permission = "_zj_xg_sc_"; 
		}else{
			try{
				List<AncdModel> buttons = (List<AncdModel>)getValueStack().findValue("ancdModelList");
				if(buttons!=null){
					for(AncdModel m:buttons){
						permission += "_"+m.getCzdm()+"_";
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				permission = "";
			}
		}
		int contentHight=(clazz.getEditables().size()/2)*30;
		contentHight=contentHight>500?500:contentHight;
		this.getValueStack().set("permission", permission);
		this.getValueStack().set("heightOffset", contentHight);
		this.getValueStack().set("conditions", conditions);
		this.getValueStack().set("valuesMap", valuesMap);
		this.getValueStack().set("classId", classId);
//		List classes=InfoClassCache.getInfoClasses();
		return LIST_PAGE;
	}
	
	public String showLog(){
		
		query.setOrderStr("t.operation_time_ desc");
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		query.setClazz(clazz);
		StringBuilder sb = new StringBuilder();
		SQLGenerateUtil.appendLike(sb, "t.globalid", instanceId);
		query.setExpress(sb.toString());
		IDynaBeanLogService dynaBeanLogService = SpringHolder.getBean("baseDynaBeanLogService",IDynaBeanLogService.class);
		
		PageList<DynaBean>  logList = dynaBeanLogService.getPagingList(query);
		getValueStack().set("logList", logList);
		return "showlog";
	}
	
	private String getExpress(){
		String express="1=1";
		
		if(!StringUtil.isEmpty(overall.getStatus())){
		//	express+= " and exists (select 1 from overall ov where ov.gh=o.gh and ov.dqztm = '"+overall.getStatus()+"')";
			express+= " and o.dqztm = '" + overall.getStatus()+"'";
		}
		
		String deptFilter=DeptFilterUtil.getCondition("o", "dwm");
		if(!StringUtil.isEmpty(deptFilter)){
			express+= " and ("+deptFilter+")";
		}
		
		if(!StringUtil.isEmpty(overall.getUserId())){
			express+= " and t.gh like '%"+overall.getUserId()+"%'";
		}
		if(!StringUtil.isEmpty(overall.getUserName())){
		//	express+= " and exists (select 1 from overall ovxm where ovxm.gh=o.gh and ovxm.xm like '%"+overall.getUserName()+"%') ";
			express+= " and o.xm like '%"+overall.getUserName()+"%'";
		}
		
		if(!StringUtil.isEmpty(overall.getDepartmentId())){
		/*	express+= " and exists (select 1 from overall ovdw where ovdw.gh=o.gh and ovdw.dwm in" +
					" (select HRM_BZGL_ZZJGB.bmdm as dwm from HRM_BZGL_ZZJGB where HRM_BZGL_ZZJGB.bmdm = "+overall.getDepartmentId()
					+" or HRM_BZGL_ZZJGB.sjbmdm="+overall.getDepartmentId()+"))";*/
			express+= " and o.dwm = '"+ overall.getDepartmentId()+"'";
		}
		if(!StringUtil.isEmpty(overall.getFormationType())){
		//	express+= " and exists (select 1 from overall ovxm where ovxm.gh=o.gh and ovxm.bzlbm = '"+overall.getFormationType()+"') ";
			express+= " and o.bzlbm = '"+overall.getFormationType()+"'";
		}
		/*if(!StringUtil.isEmpty(overall.getSex())){
			express += " and exists ( select 1 from overall ovxb where ovxb.gh = o.gh and ovxb.xbm = '"+overall.getSex()+"')";
		}
		
		if(!StringUtil.isEmpty(overall.getPostId())){
			express += " and exists ( select 1 from overall ovgw  where ovgw.gh = o.gh and ovgw.gwlb = '"+overall.getPostId()+"')";
		}
		
		if(!StringUtil.isEmpty(overall.getFormationType())){
			express += " and exists ( select 1 from overall ovbz where ovbz.gh = o.gh and ovbz.bzlbm = '"+overall.getFormationType()+"')";
		}
		
		if(!StringUtil.isEmpty(overall.getDutyLevel())){
			express += " and exists ( select 1 from overall ovzw where ovzw.gh = o.gh and ovzw.zwjbdm = '"+overall.getDutyLevel()+"')";
		}
		
		if(!StringUtil.isEmpty(overall.getPostTitle())){
			express += " and exists ( select 1 from overall ovzc where ovzc.gh = o.gh and ovzc.zc = '"+overall.getPostTitle()+"')";
		}
		
		if(!StringUtil.isEmpty(overall.getStartDateString()) && !StringUtil.isEmpty(overall.getEndDateString()) ){
			express += " and exists ( select 1 from overall ovsj where ovsj.gh = o.gh and ovsj.jxsj >= to_date('"+overall.getStartDateString()+"','yyyy-MM-dd') and" +
					" ovsj.jxsj <= to_date('"+overall.getEndDateString()+"','yyyy-MM-dd'))";
		}else if(!StringUtil.isEmpty(overall.getStartDateString()) && StringUtil.isEmpty(overall.getEndDateString())){
			express += " and exists ( select 1 from overall ovsj where ovsj.gh = o.gh and ovsj.jxsj >= to_date('"+overall.getStartDateString()+"','yyyy-MM-dd'))";
		}else if(StringUtil.isEmpty(overall.getStartDateString()) && !StringUtil.isEmpty(overall.getEndDateString())){
			express += " and exists ( select 1 from overall ovsj where ovsj.gh = o.gh and ovsj.jxsj <= to_date('"+overall.getEndDateString()+"','yyyy-MM-dd'))";
		}*/
		
		return express;
	}
	public String add(){
		clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
		editables=new ArrayList<InfoProperty>();
		InfoProperty zp = null;
		String flg = "";
		for (InfoProperty p : clazz.getProperties()) {
			if((p.getEditable()||p.getViewable())&&!"gh".equals(p.getFieldName())){
				if ("zp".equals(p.getFieldName())) {
					zp = p;
				} else {
					editables.add(p);
				}
			}
			
			if ("dwh".equals(p.getFieldName())) {
				flg = "dwh";
			}
		}
		
		if (zp != null) {
			if (StringUtils.isEmpty(flg)) {
				getValueStack().set("rowspan", 5);
			} else {
				getValueStack().set("rowspan", 4);
			}
		}
		getValueStack().set("zpInfo", zp);
		model=new DynaBean(clazz);
		return EDIT_PAGE;
	}
	public String modify(){
		op="modify";
		clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
		model=new DynaBean(clazz);
		String gh;
		if(!StringUtil.isEmpty(instanceId)){
			model.setValue("globalid", instanceId);
			model=staffInfoService.getById(model);
			gh=model.getValue("gh").toString();
		}else{
			gh=this.getRequest().getParameter("gh");
		}
		
		DynaBean overall=dynaBeanService.findUniqueByParam("gh",gh);
		if(overall.getValue("xm") != null){
			this.getValueStack().set("xm", overall.getValue("xm"));
		}
		this.getValueStack().set("gh", gh );
		if(overall.getValue("dwm")!=null){
			this.getValueStack().set("dwmc", CodeUtil.getItemValue(ICodeConstants.DM_DEF_ORG, overall.getValue("dwm").toString()));
		}
		String couldModify = (String)getRequest().getParameter("editable");
		if (!"couldModify".equals(couldModify)) {
			this.getValueStack().set("view", "view");
		}
		editables=new ArrayList<InfoProperty>();
		
		InfoProperty zp = null;
		String flg = "";
		for (InfoProperty p : clazz.getProperties()) {
			if((p.getEditable()||p.getViewable())&&!"gh".equals(p.getFieldName())){
				if ("zp".equals(p.getFieldName())) {
					zp = p;
				} else {
					editables.add(p);
				}
			}
			
			if ("dwh".equals(p.getFieldName())) {
				flg = "dwh";
			}
		}
		
		if (zp != null) {
			if (StringUtils.isEmpty(flg)) {
				getValueStack().set("rowspan", 5);
			} else {
				getValueStack().set("rowspan", 4);
			}
		}
		
		getValueStack().set("zpInfo", zp);
		return EDIT_PAGE;
	}
	
	public String batchEditView(){
		clazz = FormInfoUtil.reFillPropertyByRole(SessionFactory.getUser().getJsdms(), classId);
		editables=clazz.getEditables();
		List<InfoProperty> plist = new ArrayList<InfoProperty>();
		for (InfoProperty p : editables) {
			if ("PHOTO".equals(p.getFieldType())
					||"IMAGE".equals(p.getFieldType())
					||p.getUnique()) {
				continue;
			}
			plist.add(p);
		}
		getValueStack().set("plist", plist);
		return "batchedit";
	}
	public String findProperties(){
		clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
		String html = ParseFactory.EditParse(clazz.getPropertyByName(batchFieldName), "");
		Map<String, String> data = new HashMap<String, String>();
		data.put("edithtml", html);
		getValueStack().set(DATA, data);
		return DATA;
	}
	public String batchModify(){
		clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
		String value=this.getRequest().getParameter(batchFieldName);
		InfoProperty infoProperty = clazz.getPropertyByName(batchFieldName); 
		if(infoProperty.getNeed()&&StringUtil.isEmpty(value)){
			this.setErrorMessage(infoProperty.getName()+"为必填");
			this.getValueStack().set(DATA, getMessage());
			return DATA;
		}
		String error=VerifyUtil.parse(infoProperty, value);
		if(!StringUtil.isEmpty(error)){
			this.setErrorMessage(error);
			this.getValueStack().set(DATA, getMessage());
			return DATA;
		}
		Map<String, Object> values = new HashMap<String, Object>();
		model=new DynaBean(clazz);
		for (String id : ids.split(",")) {
			if (id==null||id.trim().equals("")) {
				continue;
			}
			model.setValue("globalid", id.trim());	
			model=dynaBeanService.findById(model);
			values.put(infoProperty.getFieldName(), value);
			dynaBeanBusiness.modifyRecord(model, values, false);
		}
		setSuccessMessage("修改成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	public String save(){
		clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
		if(op.equals("add")){
			model=new DynaBean(clazz);
		}else{
			model=new DynaBean(clazz);
			if(StringUtil.isEmpty(instanceId)){
				model=new DynaBean(clazz);
				op="add";
			}else{			
				model.setValue("globalid", instanceId);	
				model=dynaBeanService.findById(model);
			}
		}
		String ghs = this.getRequest().getParameter("gh");
		if(StringUtil.isEmpty(ghs)){
			throw new RuleException("工号不能为空");
		}
		for (String gh : ghs.split(";")) {
			model.setValue("gh", gh);
			for (InfoProperty infoProperty : model.getEditables()) {
				String value=this.getRequest().getParameter(infoProperty.getFieldName());
				if(infoProperty.getNeed()&&StringUtil.isEmpty(value)){
					this.setErrorMessage(infoProperty.getName()+"为必填");
					this.getValueStack().set(DATA, getMessage());
					return DATA;
				}
				String error=VerifyUtil.parse(infoProperty, value);
				if(!StringUtil.isEmpty(error)){
					this.setErrorMessage(error);
					this.getValueStack().set(DATA, getMessage());
					return DATA;
				}
				model.setValue(infoProperty.getFieldName(), value);
			}
			if(op.equals("add")){
				dynaBeanService.addRecord(model);
			}else{
				dynaBeanService.modifyRecord(model);
			}
		}
		
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	public String remove(){
		clazz = InfoClassCache.getInfoClass(classId);
		model=new DynaBean(clazz);
		model.setValue("globalid", instanceId);
		model=dynaBeanService.findById(model);
		dynaBeanService.removeRecord(model);
		this.setSuccessMessage("操作成功");
		this.getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public void setStaffInfoService(IStaffInfoService staffInfoService) {
		this.staffInfoService = staffInfoService;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public List<StaffInfoDto> getStaffInfoDtos() {
		return staffInfoDtos;
	}
	public void setStaffInfoDtos(List<StaffInfoDto> staffInfoDtos) {
		this.staffInfoDtos = staffInfoDtos;
	}

	public InfoClass getClazz() {
		return clazz;
	}

	public List<InfoProperty> getViewables() {
		return viewables;
	}
	public void setViewables(List<InfoProperty> viewables) {
		this.viewables = viewables;
	}
	public void setClazz(InfoClass clazz) {
		this.clazz = clazz;
	}
	public String getModeType() {
		return modeType;
	}
	public void setModeType(String modeType) {
		this.modeType = modeType;
	}
	public PageList<StaffInfoDto> getStaffInfoDtoPageList() {
		return staffInfoDtoPageList;
	}
	public void setStaffInfoDtoPageList(PageList<StaffInfoDto> staffInfoDtoPageList) {
		this.staffInfoDtoPageList = staffInfoDtoPageList;
	}
	public DynaBeanQuery getQuery() {
		return query;
	}
	public void setQuery(DynaBeanQuery query) {
		this.query = query;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public List<InfoProperty> getEditables() {
		return editables;
	}
	public void setEditables(List<InfoProperty> editables) {
		this.editables = editables;
	}

	public DynaBean getModel() {
		return model;
	}
	public void setModel(DynaBean model) {
		this.model = model;
	}
	public String getInstanceId() {
		return instanceId;
	}
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}
	public OverallInfo getOverall() {
		return overall;
	}
	public void setOverall(OverallInfo overall) {
		this.overall = overall;
	}
	public String getPx() {
		return px;
	}
	public void setPx(String px) {
		this.px = px;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getBatchFieldName() {
		return batchFieldName;
	}
	public void setBatchFieldName(String batchFieldName) {
		this.batchFieldName = batchFieldName;
	}
	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

	/**
	 * @return the bol
	 */
	public boolean isBol() {
		return bol;
	}

	/**
	 * @param bol the bol to set
	 */
	public void setBol(boolean bol) {
		this.bol = bol;
	}

	/**
	 * 返回
	 */
	public String getAsc() {
		return asc;
	}

	/**
	 * 设置
	 * @param asc 
	 */
	public void setAsc(String asc) {
		this.asc = asc;
	}

	/**
	 * 返回
	 */
	public String getSortFieldName() {
		return sortFieldName;
	}

	/**
	 * 设置
	 * @param sortFieldName 
	 */
	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public int getShowMore() {
		return showMore;
	}

	public void setShowMore(int showMore) {
		this.showMore = showMore;
	}

	/**
	 * 返回
	 */
	public String getCloseField() {
		return closeField;
	}

	/**
	 * 设置
	 * @param closeField 
	 */
	public void setCloseField(String closeField) {
		this.closeField = closeField;
	}
	
}
