package com.zfsoft.hrm.dybill.action;
/**
 * 审批表单实例
 * @author Patrick Shen
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.entity.SpBillInstanceLog;
import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.hrm.dybill.enums.PrivilegeType;
import com.zfsoft.hrm.dybill.enums.ScanStyleType;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillDefineCatchService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceLogService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.dybill.util.BillValueUtil;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybill.xml.XmlValueClass;
import com.zfsoft.hrm.dybill.xml.XmlValueClasses;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybill.xml.XmlValueProperty;
import com.zfsoft.util.base.StringUtil;
/**
 * 审批表单实例
 * 新建http://localhost:8080/zftal-sum-zpack/bill/instance_addNewBillInstance.html?spBillConfig.id=37F38D4EA5844B66B5A636251DDF7508
 * 修改 查看http://localhost:8080/zftal-sum-zpack/bill/instance_list.html?
 * spBillConfig.id=37F38D4EA5844B66B5A636251DDF7508&spBillInstance.id=D05388C3FADE4B3B9F965150C32F3252
 * &privilegeExpression=NORMAL[1365082181283-SEARCH,1365477704525-SEARCH_ADD_DELETE]
 * @author Patrick Shen
 */
public class SpBillInstanceAction extends HrmAction {
	
	private static final long serialVersionUID = 6854631681595616817L;
	
	private ISpBillInstanceService spBillInstanceService;
	private ISpBillInstanceLogService spBillInstanceLogService;
	private ISpBillConfigService spBillConfigService;
	private IDynaBeanService dynaBeanService;
	private ISpBillDefineCatchService spBillDefineCatchService;
	private BillValueUtil billValueUtil;
	private SpBillConfig spBillConfig;
	private SpBillInstance spBillInstance;
	private XmlBillClass xmlBillClassBean;
	private XmlValueClass valueClassBean;
	private XmlValueEntity valueEntity;
	private Boolean localEdit=false;//是否允许编辑抓取信息
	private Boolean innerClick=false;//是否默认不点开编辑（localEdit:true;innerClick:false时点开）
	private Boolean saveLog=false;//是否记录修改日志
	private String staffId;
	/**
	 * 范例1365082181283-SEARCH,1365082181617-SEARCH_ADD_DELETE
	 * 或者 ALL-SEARCH
	 */
	private String privilegeExpression;//子表单展示控制，null表示全部展示，否则按billClassId展示匹配项
	private String op="add";
	
	private void getViewData() {
		spBillInstance=spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(), spBillInstance.getId());
		//获取要展示的表单序列
		List<XmlBillClass> viewBillClasses=new ArrayList<XmlBillClass>();
		
		List<XmlBillClass> billClasses=
			spBillConfigService.getXmlBillClassListByVersion(spBillConfig.getId(), spBillInstance.getVersion(), privilegeExpression);
		//获取要展示的表单序列对应的值
		List<XmlValueEntity> valueEntities;
		Map<Long,List<XmlValueEntity>> classValueEntityMap=new HashMap<Long, List<XmlValueEntity>>();
		XmlValueClasses xmlValueClasses = spBillInstance.getXmlValueClasses();
		for (XmlBillClass xmlBillClass : billClasses) {
			XmlValueClass xmlValueClass = xmlValueClasses.getValueClassByClassId(xmlBillClass.getId());
			if(xmlValueClass==null||xmlValueClass.getValueEntities()==null){
				valueEntities = new ArrayList<XmlValueEntity>();
			}else{
				valueEntities = xmlValueClass.getValueEntities();
				XmlBillProperty xmlBillProperty;
				for (XmlValueEntity xmlValueEntity : valueEntities) {
					xmlValueEntity.setXmlBillClass(xmlBillClass);
					//判空
					if(xmlValueEntity.getValueProperties()==null){
						continue;
					}
					for (XmlValueProperty xmlValueProperty : xmlValueEntity.getValueProperties()) {
						xmlBillProperty = xmlBillClass.getBillPropertyById(xmlValueProperty.getBillPropertyId());
						if(xmlBillProperty!=null){
							xmlValueProperty.setBillProperty(xmlBillProperty);
						}
					}
				}
			}
//渲染表单的时候进行判断 如果当前条目数小于自动抓取数 则进行自动抓取来补充
//			if(xmlBillClass.getCatchRecordNum()>0&&valueEntities.size()==0){
//				xmlValueClass=spBillInstanceService.appendCatchRecord(spBillConfig.getId(),spBillInstance.getId(),
//						xmlBillClass,getStaffId());
//				valueEntities=xmlValueClass.getValueEntities();
//			}
			classValueEntityMap.put(xmlBillClass.getId(), valueEntities);
			if(!(xmlBillClass.getPrivilegeType().equals(PrivilegeType.SEARCH)&&(valueEntities==null||valueEntities.isEmpty()))){
				viewBillClasses.add(xmlBillClass);
			}
		}
		this.getValueStack().set("billClasses", viewBillClasses);
		this.getValueStack().set("localEdit", localEdit);
		this.getValueStack().set("saveLog", saveLog);
		this.getValueStack().set("classValueEntityMap", classValueEntityMap);
	}
	                       
	public String list(){
		getViewData();
		return LIST_PAGE;
	}

	public String logView(){
		SpBillInstanceLog log=spBillInstanceLogService.findById(spBillInstance.getId());
		spBillInstance = spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(), log.getBillInstanceId());
		XmlValueClasses valueClasses = log.getXmlValueClasses();
		//获取要展示的表单序列
		List<XmlBillClass> billClasses=
			spBillConfigService.getXmlBillClassListByVersion(spBillConfig.getId(), spBillInstance.getVersion(), ModeType.ALL_SEARCH.toString());
		//获取要展示的表单序列对应的值
		List<XmlValueEntity> valueEntities;
		Map<Long,List<XmlValueEntity>> classValueEntityMap=new HashMap<Long, List<XmlValueEntity>>();
		for (XmlBillClass xmlBillClass : billClasses) {
			XmlValueClass valueClass=valueClasses.getValueClassByClassId(xmlBillClass.getId());
			if(valueClass!=null&&valueClass.getValueEntities()!=null){
				valueEntities = valueClass.getValueEntities();
				for (XmlValueEntity xmlValueEntity : valueEntities) {
					xmlValueEntity.setXmlBillClass(xmlBillClass);
					//判空
					if(xmlValueEntity.getValueProperties()==null){
						continue;
					}
					for (XmlValueProperty xmlValueProperty : xmlValueEntity.getValueProperties()) {
						XmlBillProperty xmlBillProperty = xmlBillClass.getBillPropertyById(xmlValueProperty.getBillPropertyId());
						if(xmlBillProperty!=null){
							xmlValueProperty.setBillProperty(xmlBillProperty);
						}
					}
				}
			}
			else{
				valueEntities = new ArrayList<XmlValueEntity>();
			}
			classValueEntityMap.put(xmlBillClass.getId(), valueEntities);
		}
		this.getValueStack().set("billClasses", billClasses);
		this.getValueStack().set("localEdit", Boolean.FALSE);
		this.getValueStack().set("saveLog", Boolean.FALSE);
		this.getValueStack().set("classValueEntityMap", classValueEntityMap);
		return LIST_PAGE;
	}
	
	public String addNewBillInstance(){
		spBillInstance=spBillInstanceService.getNewSpBillInstance(spBillConfig.getId());
		getViewData();
		return LIST_PAGE;
	}
	
	public void dealValueEntity(XmlValueEntity valueEntity) {
		spBillInstance=spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(), spBillInstance.getId());
		if(valueEntity.getId()==null){
			valueEntity.setId(System.currentTimeMillis());
		}
		PrivilegeType privilegeType=xmlBillClassBean.getPrivilegeType();
		xmlBillClassBean=spBillConfigService.getXmlBillClassByVersion(spBillConfig.getId(),spBillInstance.getVersion(),
				xmlBillClassBean.getId());
		xmlBillClassBean.setPrivilegeType(privilegeType);
		if(valueEntity.getXmlBillClass()==null){
			valueEntity.setXmlBillClass(xmlBillClassBean);
		}
		if(valueEntity.getValueProperties()==null){
			valueEntity.setValueProperties(new ArrayList<XmlValueProperty>());
		}
		this.getValueStack().set("localEdit", localEdit);
		this.getValueStack().set("saveLog", saveLog);
		this.getValueStack().set("billClass", xmlBillClassBean);
	}

	public String cancel() {
		spBillInstance=spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(), spBillInstance.getId());
		PrivilegeType privilegeType=xmlBillClassBean.getPrivilegeType();
		xmlBillClassBean=spBillConfigService.getXmlBillClassByVersion(spBillConfig.getId(),spBillInstance.getVersion(),
				xmlBillClassBean.getId());
		xmlBillClassBean.setPrivilegeType(privilegeType);
		if(xmlBillClassBean.getScanStyle()==ScanStyleType.LIST){
			List<XmlValueEntity> valueEntities=spBillInstanceService.getXmlValueEntityList(spBillConfig.getId(),
					spBillInstance.getId(), xmlBillClassBean.getId());
			this.getValueStack().set("valueEntities", valueEntities);
			return "entity_list_view";
		}else{
			valueEntity=spBillInstanceService.getXmlValueEntityById(spBillConfig.getId(),spBillInstance.getId(),
					xmlBillClassBean.getId(),valueEntity.getId());
			dealValueEntity(valueEntity);
			return "entity_view";
		}
	}
	
	public String add() {
		valueEntity=new XmlValueEntity();
		dealValueEntity(valueEntity);
		this.getValueStack().set("localEdit", localEdit);
		return "entity_edit";
	}

	public String modify() {
		op="modify";
		valueEntity=
			spBillInstanceService.getXmlValueEntityById(spBillConfig.getId(),
					spBillInstance.getId(), xmlBillClassBean.getId(), valueEntity.getId());
		 dealValueEntity(valueEntity);
		 
		 return "entity_edit";
	}

	public String remove() {
		spBillInstanceService.removeXmlValueInstance(spBillConfig.getId(),spBillInstance.getId(), xmlBillClassBean.getId(), valueEntity.getId());
		if(saveLog){
			SpBillInstance instance = spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(),spBillInstance.getId());
			spBillInstanceLogService.insertInstanceLog(instance,getUser().getYhm());
		}
		this.setSuccessMessage("删除成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}

	public String save() {
		spBillInstance=spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(), spBillInstance.getId());
		PrivilegeType privilegeType=xmlBillClassBean.getPrivilegeType();
		xmlBillClassBean=spBillConfigService.getXmlBillClassByVersion(spBillConfig.getId(),spBillInstance.getVersion(),xmlBillClassBean.getId());
		xmlBillClassBean.setPrivilegeType(privilegeType);
		List<XmlValueProperty> values;
		try{
			if(!localEdit){
				values=billValueUtil.getValuesAndCheck(spBillConfig.getId(),spBillInstance.getVersion(),xmlBillClassBean.getId(),
					this.getRequest());
			}else{
				if(!"add".equals(op)){
					valueEntity=spBillInstanceService.getXmlValueEntityById(spBillConfig.getId(),spBillInstance.getId(),
							xmlBillClassBean.getId(),valueEntity.getId());
				}else{
					valueEntity.getValueProperties();
				}
				values=billValueUtil.getValuesAndCheckLocal(spBillConfig.getId(),spBillInstance.getVersion(),xmlBillClassBean.getId(),valueEntity,
					this.getRequest());
			}
		}catch(RuntimeException e){
			this.setErrorMessage(e.getMessage());
			this.getValueStack().set(DATA, this.getMessage());
			return DATA;
		}
		if("add".equals(op)){
			spBillInstanceService.addXmlValueInstances(spBillConfig.getId(),spBillInstance.getId(),
					xmlBillClassBean.getId(), values, valueEntity);
		}else{
			if(localEdit){
				spBillInstanceService.modifyXmlValueInstanceLocal(spBillConfig.getId(),spBillInstance.getId(),
						xmlBillClassBean.getId(),valueEntity.getId(), values);
			}else{
				spBillInstanceService.modifyXmlValueInstance(spBillConfig.getId(),spBillInstance.getId(),
						xmlBillClassBean.getId(),valueEntity.getId(), values);
			}
		}
		if(saveLog){
			SpBillInstance instance = spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(),spBillInstance.getId());
			spBillInstanceLogService.insertInstanceLog(instance,getUser().getYhm());
		}
		valueEntity=spBillInstanceService.getXmlValueEntityById(spBillConfig.getId(),spBillInstance.getId(),
				xmlBillClassBean.getId(),valueEntity.getId());
		
		dealValueEntity(valueEntity);
		 
		if(xmlBillClassBean.getScanStyle()==ScanStyleType.LIST){
			List<XmlValueEntity> valueEntities=spBillInstanceService.getXmlValueEntityList(spBillConfig.getId(),
						spBillInstance.getId(), xmlBillClassBean.getId());
			this.getValueStack().set("valueEntities", valueEntities);
			return "entity_list_view";
		}
		return "entity_view";
	}
	
	
	public String infoChoice(){
		spBillInstance=spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(), spBillInstance.getId());
		PrivilegeType privilegeType=xmlBillClassBean.getPrivilegeType();
		xmlBillClassBean=spBillConfigService.getXmlBillClassByVersion(spBillConfig.getId(),spBillInstance.getVersion(),xmlBillClassBean.getId());
		xmlBillClassBean.setPrivilegeType(privilegeType);
		List<DynaBean> dynaBeanList;
		if(xmlBillClassBean.getDefineCatch()!=null){
			dynaBeanList = findDynaBeansByDefineCatch(null);
		}
		else if(!StringUtil.isEmpty(xmlBillClassBean.getClassId())){
			dynaBeanList = findDynaBeansByClassId(null);
		}else{
			throw new RuleException();
		}
		Map<String,Boolean> choiceEntities=new HashMap<String, Boolean>();
		Map<String,Boolean> selfEntities=new HashMap<String, Boolean>();
		List<XmlValueEntity> valueEntities=spBillInstanceService.getXmlValueEntityList(spBillConfig.getId(),
				spBillInstance.getId(), xmlBillClassBean.getId());
		if(valueEntities!=null){
			for (XmlValueEntity xmlValueEntity : valueEntities) {
				if(!StringUtil.isEmpty(xmlValueEntity.getInfoEntityId())){
					choiceEntities.put(xmlValueEntity.getInfoEntityId(), true);
				}else{
					selfEntities.put(xmlValueEntity.getInfoEntityId(), true);
				}
			}
		}
		this.getValueStack().set("choiceNum",choiceEntities.size());
		this.getValueStack().set("selfNum", selfEntities.size());
		this.getValueStack().set("dynaBeanList", dynaBeanList);
		this.getValueStack().set("choiceEntities", choiceEntities);
		this.getValueStack().set("localEdit", localEdit);
		this.getValueStack().set("saveLog", saveLog);
		return "entity_choice";
	}
	
	private List<DynaBean> findDynaBeansByClassId(String globalid){
		InfoClass infoClass=InfoClassCache.getInfoClass(xmlBillClassBean.getClassId());
		DynaBeanQuery query=new DynaBeanQuery(infoClass);
		query.setHistory(true);
		String express = "gh='"+getStaffId()+"'";
		if(!StringUtil.isEmpty(globalid)){
			express=" globalid = '"+globalid+"'";
		}
		query.setExpress(express);
		List<DynaBean> dynaBeanList=dynaBeanService.findList(query);
		return dynaBeanList;
	}
	
	private List<DynaBean> findDynaBeansByDefineCatch(String globalid){
		
		if(!StringUtil.isEmpty(globalid)){
			String express = xmlBillClassBean.getDefineCatch().getExpression();
			express = xmlBillClassBean.getDefineCatch().
					getUniqueField()+"= '"+globalid+"' and "+express;
			xmlBillClassBean.getDefineCatch().setExpression(express);
		}
		spBillConfig = spBillConfigService.getSpBillConfigById(spBillConfig.getId());
		spBillInstance = spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(), spBillInstance.getId());
		List<DynaBean> dynaBeanList=spBillDefineCatchService.getListToView(xmlBillClassBean.getFullDefineCatch(), spBillConfig, spBillInstance);
		return dynaBeanList;
	}

	public String addChoice(){
		spBillInstance=spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(), spBillInstance.getId());
		PrivilegeType privilegeType=xmlBillClassBean.getPrivilegeType();
		xmlBillClassBean=spBillConfigService.getXmlBillClassByVersion(spBillConfig.getId(),spBillInstance.getVersion(),xmlBillClassBean.getId());
		xmlBillClassBean.setPrivilegeType(privilegeType);
		//InfoClass infoClass=InfoClassCache.getInfoClass(xmlBillClass.getClassId());
		//DynaBean dybean=new DynaBean(infoClass);
		//dybean.setValue("globalid", valueEntity.getInfoEntityId());
		valueEntity.setId(System.currentTimeMillis());
		//dybean=dynaBeanService.findById(dybean);
		List<DynaBean> dynaBeanList;
		if(xmlBillClassBean.getDefineCatch()!=null){
			dynaBeanList = findDynaBeansByDefineCatch(valueEntity.getInfoEntityId());
		}
		else if(!StringUtil.isEmpty(xmlBillClassBean.getClassId())){
			dynaBeanList = findDynaBeansByClassId(valueEntity.getInfoEntityId());
		}else{
			throw new RuleException();
		}
		DynaBean dybean=null;
		if(dynaBeanList !=null && !dynaBeanList.isEmpty()){
			dybean = dynaBeanList.get(0);
		}
		spBillInstanceService.addXmlValueInstances(spBillConfig.getId(),spBillInstance.getId(),
				xmlBillClassBean.getId(),
				billValueUtil.getValuesAndCheck(spBillConfig.getId(),spBillInstance.getVersion(),xmlBillClassBean.getId(), dybean), valueEntity);
		if(saveLog){
			SpBillInstance instance = spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(),spBillInstance.getId());
			spBillInstanceLogService.insertInstanceLog(instance,getUser().getYhm());
		}
		valueEntity=spBillInstanceService.getXmlValueEntityById(spBillConfig.getId(),spBillInstance.getId(),
				xmlBillClassBean.getId(),valueEntity.getId());
		
		dealValueEntity(valueEntity);
		this.getValueStack().set("saveLog", saveLog);
		if(xmlBillClassBean.getScanStyle()==ScanStyleType.LIST){
			List<XmlValueEntity> valueEntities=spBillInstanceService.getXmlValueEntityList(spBillConfig.getId(),
						spBillInstance.getId(), xmlBillClassBean.getId());
			this.getValueStack().set("valueEntities", valueEntities);
			return "entity_list_view";
		}
		return "entity_view";
	}
	
	public String removeChoice(){
		spBillInstance=spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(), spBillInstance.getId());
		xmlBillClassBean=spBillConfigService.getXmlBillClassByVersion(spBillConfig.getId(),spBillInstance.getVersion(),xmlBillClassBean.getId());
		valueEntity=spBillInstanceService.getXmlValueEntityByInfoEntityId(spBillConfig.getId(),spBillInstance.getId(),
				xmlBillClassBean.getId(), valueEntity.getInfoEntityId());
		spBillInstanceService.removeXmlValueInstance(spBillConfig.getId(),spBillInstance.getId(), xmlBillClassBean.getId(), valueEntity.getId());
		if(saveLog){
			SpBillInstance instance = spBillInstanceService.getSpBillInstanceById(spBillConfig.getId(),spBillInstance.getId());
			spBillInstanceLogService.insertInstanceLog(instance,getUser().getYhm());
		}
		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("message", this.getMessage());
		map.put("valueEntityId", valueEntity.getId());
		this.setSuccessMessage("删除成功");
		this.getValueStack().set(DATA, map);
		return DATA;
	}
	
	public SpBillConfig getSpBillConfig() {
		return spBillConfig;
	}
	public void setSpBillConfig(SpBillConfig spBillConfig) {
		this.spBillConfig = spBillConfig;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public SpBillInstance getSpBillInstance() {
		return spBillInstance;
	}

	public void setSpBillInstance(SpBillInstance spBillInstance) {
		this.spBillInstance = spBillInstance;
	}

	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}

	public void setSpBillInstanceService(
			ISpBillInstanceService spBillInstanceService) {
		this.spBillInstanceService = spBillInstanceService;
	}

	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}
	
	public void setBillValueUtil(BillValueUtil billValueUtil) {
		this.billValueUtil = billValueUtil;
	}
	public XmlValueClass getValueClassBean() {
		return valueClassBean;
	}
	public void setValueClassBean(XmlValueClass valueClass) {
		this.valueClassBean = valueClass;
	}
	public XmlValueEntity getValueEntity() {
		return valueEntity;
	}
	public void setValueEntity(XmlValueEntity valueEntity) {
		this.valueEntity = valueEntity;
	}
	public String getPrivilegeExpression() {
		return privilegeExpression;
	}
	public void setPrivilegeExpression(String privilegeExpression) {
		this.privilegeExpression = privilegeExpression;
	}
	public XmlBillClass getXmlBillClassBean() {
		return xmlBillClassBean;
	}
	public void setXmlBillClassBean(XmlBillClass xmlBillClass) {
		this.xmlBillClassBean = xmlBillClass;
	}

	public Boolean getLocalEdit() {
		return localEdit;
	}

	public void setLocalEdit(Boolean localEdit) {
		this.localEdit = localEdit;
	}

	public Boolean getInnerClick() {
		return innerClick;
	}

	public void setInnerClick(Boolean innerClick) {
		this.innerClick = innerClick;
	}

	/**
	 * 设置
	 * @param spBillDefineCatchService 
	 */
	public void setSpBillDefineCatchService(
			ISpBillDefineCatchService spBillDefineCatchService) {
		this.spBillDefineCatchService = spBillDefineCatchService;
	}

	
	public void setSpBillInstanceLogService(
			ISpBillInstanceLogService spBillInstanceLogService) {
		this.spBillInstanceLogService = spBillInstanceLogService;
	}

	public Boolean getSaveLog() {
		return saveLog;
	}

	public void setSaveLog(Boolean saveLog) {
		this.saveLog = saveLog;
	}
	
	public String getStaffId() {
		if(StringUtil.isEmpty(staffId)){
			staffId = SessionFactory.getUser().getYhm();
		}
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
}
