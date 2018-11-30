package com.zfsoft.hrm.dybillgrade.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.dybill.xml.XmlBillClass;
import com.zfsoft.hrm.dybill.xml.XmlBillClasses;
import com.zfsoft.hrm.dybill.xml.XmlValueEntity;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeConfig;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeResult;
import com.zfsoft.hrm.dybillgrade.entity.SpBillGradeResultCondition;
import com.zfsoft.hrm.dybillgrade.service.ISpBillGradeService;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-12
 * @version V1.0.0
 */
public class SpBillGradeAction extends HrmAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5117253453346095308L;
	private SpBillGradeConfig config;
	private String instanceId;
	private String id;
	private ISpBillConfigService spBillConfigService;
	private ISpBillInstanceService spBillInstanceService;
	private ISpBillGradeService spBillGradeService;
	
	public String detail(){
		SpBillGradeResult spBillGradeResult= spBillGradeService.getGradeResult(id);
		SpBillInstance instance = spBillInstanceService.getSpBillInstanceById(spBillGradeResult.getBillConfigId(), spBillGradeResult.getBillInstanceId());
		SpBillConfig spBillConfig = spBillConfigService.getSpBillConfigByVersion(spBillGradeResult.getBillConfigId(), instance.getVersion());
		XmlBillClasses billClasses = spBillConfig.getXmlBillClasses();
		for (XmlBillClass billClass : billClasses.getBillClasses()) {
			List<SpBillGradeResultCondition> list = spBillGradeResult.getResultConditionMap().get(billClass.getId());
			if(list == null || list.isEmpty()) continue;
			for (SpBillGradeResultCondition spBillGradeResultCondition : list) {
				for (XmlValueEntity entity : spBillGradeResultCondition.getXmlValueEntities()) {
					entity.setXmlBillClass(billClass);
				}
			}
		}
		getValueStack().set("billClassList", billClasses.getBillClasses());
		getValueStack().set("spBillGradeResult", spBillGradeResult);
		return "detail";
	}
	
	public String doGrade(){
		SpBillGradeResult spBillGradeResult = spBillGradeService.doGrade(config.getId(), instanceId, true);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("spBillGradeResult", spBillGradeResult);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String grade(){
		SpBillGradeResult spBillGradeResult = spBillGradeService.doGrade(config.getId(), instanceId, true);
		id = spBillGradeResult.getId();
		detail();
		return "detail";
	}

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	 * 返回
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * 设置
	 * @param instanceId 
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * 设置
	 * @param spBillConfigService 
	 */
	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}

	/**
	 * 设置
	 * @param spBillInstanceService 
	 */
	public void setSpBillInstanceService(
			ISpBillInstanceService spBillInstanceService) {
		this.spBillInstanceService = spBillInstanceService;
	}

	/**
	 * 设置
	 * @param spBillGradeService 
	 */
	public void setSpBillGradeService(ISpBillGradeService spBillGradeService) {
		this.spBillGradeService = spBillGradeService;
	}

}
