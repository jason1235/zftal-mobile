package com.zfsoft.hrm.dybillgrade.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.enums.BillConfigStatus;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillClasses;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeCondition;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConditionProperty;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConditionQuery;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConfig;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConfigQuery;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeLevel;
import com.zfsoft.hrm.dybillgrade.enums.GradeBusinessEnums;
import com.zfsoft.hrm.dybillgrade.enums.GradePropertyLogicalEnum;
import com.zfsoft.hrm.dybillgrade.enums.GradePropertyOperatorEnum;
import com.zfsoft.hrm.dybillgrade.service.ISpBillGradeConfigService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-4
 * @version V1.0.0
 */
public class SpBillGradeConfigAction extends HrmAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5465465465488946L;
	private ISpBillGradeConfigService spBillGradeConfigService;
	private ISpBillConfigService spBillConfigService;
	private PageList<SpBillGradeConfig> configPageList;
	private PageList<SpBillGradeCondition> conditionPageList;
	private SpBillGradeConfigQuery configQuery=new SpBillGradeConfigQuery();
	private SpBillGradeConditionQuery conditionQuery=new SpBillGradeConditionQuery();
	private SpBillGradeCondition condition;
	private SpBillGradeConfig config;
	
	private String oper;
	
	public String list_config(){
		configPageList = spBillGradeConfigService.findSpBillGradeConfigPageList(configQuery);
//		int beginIndex = configPageList.getPaginator().getBeginIndex();
//		configPageList.setPaginator(configQuery);
		SpBillConfig spBillConfig=new SpBillConfig();
		spBillConfig.setBillType(null);
		spBillConfig.setStatus(BillConfigStatus.USING);
		List<SpBillConfig> billConfigs=spBillConfigService.getSpBillConfigList(spBillConfig);
		Map<String, String> billConfigMap = new HashMap<String, String>();
		for (SpBillConfig config : billConfigs) {
			billConfigMap.put(config.getId(), config.getName());
		}
		getValueStack().set("billConfigMap", billConfigMap);
		return "list_config";
	}

	public String edit_config(){
		SpBillConfig spBillConfig=new SpBillConfig();
		spBillConfig.setBillType(null);
		spBillConfig.setStatus(BillConfigStatus.USING);
		List<SpBillConfig> billConfigs=spBillConfigService.getSpBillConfigList(spBillConfig);
		this.getValueStack().set("billConfigs", billConfigs);
		this.getValueStack().set("types", GradeBusinessEnums.values());
		if("modify".equals(oper)){
			oper="modify";
			config=spBillGradeConfigService.getById(config.getId());
		}else{
			oper="add";
		}
		return "edit_config";
	}

	public String save_config(){
		SpBillGradeConfig query = new SpBillGradeConfig();
		query.setBillConfigId(config.getBillConfigId());
		query.setBusinessCode(config.getBusinessCode());
		List<SpBillGradeConfig>  list = spBillGradeConfigService.findList(query);
		if(list!=null&&!list.isEmpty()){
			if((list.size()>1)||(!list.get(0).getId().equals(config.getId()))){
				setErrorMessage("该业务模块下已经存在此表单的评分配置，请勿重复添加！");
				getValueStack().set(DATA, getMessage());
				return DATA;
			}
		}
		if(StringUtil.isEmpty(config.getId())){
			spBillGradeConfigService.save(config);
		}else{
			spBillGradeConfigService.update(config);
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String remove_config(){
		spBillGradeConfigService.delete(config.getId());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String level_config(){
		config = spBillGradeConfigService.getById(config.getId());
		return "level_config";
	}
	
	public String level_save(){
		SpBillGradeConfig gradeConfig = spBillGradeConfigService.getById(config.getId());
		gradeConfig.setLevelConfig(config.getLevelConfig());
		if(config.getLevelConfig()!=null){
			List<SpBillGradeLevel> levels =  config.getLevelConfig().getLevelList();
			for (int i = levels.size(); i >0 ; i--) {
				SpBillGradeLevel l =levels.get(i-1);
				if(l==null||StringUtil.isEmpty(l.getDesc())){
					levels.remove(l);
				}else if(StringUtil.isEmpty(l.getId())){
					l.setId(new Date().getTime()+"_"+i);
				}
			}
			Collections.sort(levels, new Comparator<SpBillGradeLevel>() {
				@Override
				public int compare(SpBillGradeLevel o1, SpBillGradeLevel o2) {
					return o2.getScorePoint()-o1.getScorePoint();
				}
			});
		}
		spBillGradeConfigService.update(gradeConfig);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	
	public String list_property(){
		conditionPageList = spBillGradeConfigService.findSpBillGradeConditionPageList(conditionQuery);
		config=spBillGradeConfigService.getById(conditionQuery.getConfigId());
		SpBillConfig billConfig=spBillConfigService.getSpBillConfigById(config.getBillConfigId());
		List<XmlBillClass> classList = billConfig.getXmlBillClasses().getBillClasses();
		Map<String, String> classNameMap=new HashMap<String, String>();
		Map<String, Map<Long, XmlBillProperty>> classPropertyMap=new HashMap<String, Map<Long, XmlBillProperty>>();
		for (SpBillGradeCondition spBillGradeCondition : conditionPageList) {
			String id=spBillGradeCondition.getBillClassId();
			if(null==classNameMap.get(id)){
				XmlBillClass billClass = null;
				for (XmlBillClass xmlBillClass : classList) {
					if(id.equals(xmlBillClass.getId().toString())){
						billClass=xmlBillClass;
						break;
					}
				}
				if (null != billClass) {
					classNameMap.put(id, billClass.getName());
					Map<Long, XmlBillProperty> map = new HashMap<Long, XmlBillProperty>();
					for (XmlBillProperty p : billClass.getBillPropertys()) {
						map.put(p.getId(), p);
					}
					classPropertyMap.put(id, map);
					spBillGradeCondition.fillProperties(map);
				}
			}else{
				spBillGradeCondition.fillProperties(classPropertyMap.get(id));
			}
			
		}
		getValueStack().set("classNameMap", classNameMap);
		return "list_property";
	}
	
	public String edit_property(){
		SpBillConfig spBillConfig=new SpBillConfig();
		spBillConfig.setBillType(null);
		spBillConfig.setStatus(BillConfigStatus.USING);
		config=spBillGradeConfigService.getById(condition.getConfigId());
		SpBillConfig billConfig=spBillConfigService.getSpBillConfigById(config.getBillConfigId());
		XmlBillClasses billClasses = billConfig.getXmlBillClasses();
		if(!StringUtil.isEmpty(condition.getId()))
			condition = spBillGradeConfigService.getConditionById(condition.getId());
		XmlBillClass billClass= null;
		if(!StringUtil.isEmpty(condition.getId())){
			for (XmlBillClass xmlBillClass : billClasses.getBillClasses()) {
				if(condition.getBillClassId().equals(xmlBillClass.getId().toString())){
					billClass=xmlBillClass;
					break;
				}
			}
		}
		if(billClass != null){
			Map<Long, XmlBillProperty> map = new HashMap<Long, XmlBillProperty>();
			for (XmlBillProperty p : billClass.getBillPropertys()) {
				map.put(p.getId(), p);
			}
			condition.fillProperties(map);
		}
		
		this.getValueStack().set("classes", billClasses.getBillClasses());
		this.getValueStack().set("logincalList", GradePropertyLogicalEnum.values());
		this.getValueStack().set("operatorList", GradePropertyOperatorEnum.values());
		return "edit_property";
	}
	
	public String findPropertyList(){
		SpBillConfig spBillConfig = spBillConfigService.getSpBillConfigById(config.getBillConfigId());
		XmlBillClass c =spBillConfig.getXmlBillClasses().getBillClassById(Long.valueOf(condition.getBillClassId()));
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("propertyList", c.getBillPropertys());
		this.getValueStack().set(DATA, data);
		return DATA;
	}
	
	
	public String save_property(){
		String[] propertyId= getRequest().getParameterValues("configId");
		String[] fieldValues= getRequest().getParameterValues("fieldValue");
		String[] operators= getRequest().getParameterValues("operator");
		String[] parenthesisBefores= getRequest().getParameterValues("parenthesisBefore");
		String[] parenthesisAfters= getRequest().getParameterValues("parenthesisAfter");
		String[] logicalRels= getRequest().getParameterValues("logicalRel");
		List<SpBillGradeConditionProperty> pList=new ArrayList<SpBillGradeConditionProperty>();
		if(propertyId!=null&&propertyId.length>0){
			for (int i=0;i<propertyId.length;i++) {
				SpBillGradeConditionProperty p=new SpBillGradeConditionProperty();
				p.setPropertyId(Long.valueOf(propertyId[i]));
				p.setFieldValue(fieldValues[i]);
				p.setLogicalRel(logicalRels[i]);
				p.setOperator(operators[i]);
				p.setParenthesisAfter(parenthesisAfters[i]);
				p.setParenthesisBefore(parenthesisBefores[i]);
				pList.add(p);
			}
		}
		condition.setProperties(pList);
		if(!condition.runCondition(false,null)){
			setErrorMessage("条件配置有误！请仔细检查括号是否有遗漏");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		if(StringUtil.isEmpty(condition.getId())){
			spBillGradeConfigService.saveCondition(condition);
		}else{
			spBillGradeConfigService.updateCondition(condition);
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String remove_property(){
		spBillGradeConfigService.deleteCondition(condition.getId());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	/**
	 * 设置
	 * @param spBillConfigService 
	 */
	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}

	/**
	 * 返回
	 */
	public String getOper() {
		return oper;
	}

	/**
	 * 设置
	 * @param oper 
	 */
	public void setOper(String oper) {
		this.oper = oper;
	}

	/**
	 * 返回
	 */
	public SpBillGradeCondition getCondition() {
		return condition;
	}

	/**
	 * 设置
	 * @param condition 
	 */
	public void setCondition(SpBillGradeCondition condition) {
		this.condition = condition;
	}

	/**
	 * 返回
	 */
	public SpBillGradeConfig getConfig() {
		return config;
	}

	/**
	 * 设置
	 * @param config 
	 */
	public void setConfig(SpBillGradeConfig config) {
		this.config = config;
	}

	/**
	 * 设置
	 * @param spBillGradeConfigService 
	 */
	public void setSpBillGradeConfigService(
			ISpBillGradeConfigService spBillGradeConfigService) {
		this.spBillGradeConfigService = spBillGradeConfigService;
	}

	/**
	 * 返回
	 */
	public SpBillGradeConfigQuery getConfigQuery() {
		return configQuery;
	}

	/**
	 * 设置
	 * @param configQuery 
	 */
	public void setConfigQuery(SpBillGradeConfigQuery configQuery) {
		this.configQuery = configQuery;
	}

	/**
	 * 返回
	 */
	public PageList<SpBillGradeConfig> getConfigPageList() {
		return configPageList;
	}

	/**
	 * 返回
	 */
	public SpBillGradeConditionQuery getConditionQuery() {
		return conditionQuery;
	}

	/**
	 * 设置
	 * @param conditionQuery 
	 */
	public void setConditionQuery(SpBillGradeConditionQuery conditionQuery) {
		this.conditionQuery = conditionQuery;
	}

	/**
	 * 返回
	 */
	public PageList<SpBillGradeCondition> getConditionPageList() {
		return conditionPageList;
	}
	
	
	
}
