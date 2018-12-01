package com.zfsoft.hrm.dybill.action;

import java.util.List;

import org.springframework.util.StringUtils;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.dybill.entity.SpBillConfig;
import com.zfsoft.hrm.dybill.entity.SpBillDataPushEventConfig;
import com.zfsoft.hrm.dybill.entity.SpBillDataPushProperty;
import com.zfsoft.hrm.dybill.enums.BillConfigStatus;
import com.zfsoft.hrm.dybill.enums.DataPushEventType;
import com.zfsoft.hrm.dybill.service.ISpBillConfigService;
import com.zfsoft.hrm.dybill.service.ISpBillDataPushEventConfigService;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;

/**
 * 表单数据推送事件配置
 * @className: SpBillDataPushEventConfigAction 
 * @author Patrick Shen shenluwei@126.com
 * @date 2013-8-12 上午09:33:21
 */
public class SpBillDataPushEventConfigAction extends HrmAction{

	private static final long serialVersionUID = -3082269058552483587L;
	
	private ISpBillConfigService spBillConfigService;
	
	private ISpBillDataPushEventConfigService spBillDataPushEventConfigService;
	
	private SpBillDataPushEventConfig config;
	
	private SpBillDataPushProperty property;
	
	private String oper;
	/**
	 * 
	 * @methodName list_config 
	 * @return String
	 */
	public String list_config(){
		List<SpBillDataPushEventConfig> configs=
			spBillDataPushEventConfigService.getList(new SpBillDataPushEventConfig());
		for (SpBillDataPushEventConfig spBillDataPushEventConfig : configs) {
			spBillDataPushEventConfig.setBillConfigName(
					spBillConfigService.getSpBillConfigById(spBillDataPushEventConfig.getBillConfigId()).getName());
			spBillDataPushEventConfig.setBillClassName(spBillConfigService.getXmlBillClassById(spBillDataPushEventConfig.getBillConfigId(),
					spBillDataPushEventConfig.getBillClassId()).getName());
			if(StringUtils.hasText(spBillDataPushEventConfig.getInfoClassId())){
				spBillDataPushEventConfig.setInfoClassName(InfoClassCache.getInfoClass(spBillDataPushEventConfig.getInfoClassId()).getName());
			}
		}
		this.getValueStack().set("configs", configs);
		return "list_config";
	}
	/**
	 * 
	 * @methodName list_property 
	 * @return String
	 */
	public String list_property(){
		List<SpBillDataPushProperty> properties=
			spBillDataPushEventConfigService.getPropertyByConfigId(config.getId());
		config=spBillDataPushEventConfigService.getById(config.getId());
		
		for (SpBillDataPushProperty spBillDataPushProperty : properties) {
			spBillDataPushProperty.setBillPropertyName(
					spBillConfigService.getXmlBillPropertyById(config.getBillConfigId(), config.getBillClassId(),
							spBillDataPushProperty.getBillPropertyId()).getName());
			if(config.getInfoClassId()!=null){
				if(InfoClassCache.getInfoClass(config.getInfoClassId()).getPropertyById(
						spBillDataPushProperty.getLocalPropertyId())==null)continue;
				spBillDataPushProperty.setInfoPropertyName(InfoClassCache.getInfoClass(config.getInfoClassId()).getPropertyById(
						spBillDataPushProperty.getLocalPropertyId()).getName());
			}
		}
		this.getValueStack().set("properties", properties);
		return "list_property";
	}
	/**
	 * 
	 * @methodName edit_config 
	 * @return String
	 */
	public String edit_config(){
		SpBillConfig spBillConfig=new SpBillConfig();
		spBillConfig.setBillType(null);
		spBillConfig.setStatus(BillConfigStatus.USING);
		List<SpBillConfig> billConfigs=spBillConfigService.getSpBillConfigList(spBillConfig);
		this.getValueStack().set("billConfigs", billConfigs);
		this.getValueStack().set("infoClasses", InfoClassCache.getInfoClasses());
		this.getValueStack().set("businessInfoClasses", InfoClassCache.getInfoClasses("business"));
		if("modify".equals(oper)){
			oper="modify";
			config=spBillDataPushEventConfigService.getById(config.getId());
		}else{
			oper="add";
		}
		return "edit_config";
	}
	/**
	 * 
	 * @methodName edit_property 
	 * @return String
	 */
	public String edit_property(){
		config=spBillDataPushEventConfigService.getById(config.getId());
		List<XmlBillProperty> billProperties=spBillConfigService.getXmlBillPropertyList(config.getBillConfigId(), config.getBillClassId());
		this.getValueStack().set("billProperties", billProperties);
		if(config.getInfoClassId()!=null){
			this.getValueStack().set("infoProperties", InfoClassCache.getInfoClass(config.getInfoClassId()).getProperties());
		}else{
			this.getValueStack().set("infoProperties", null);
		}
		
		if("modify".equals(oper)){
			oper="modify";
			property=spBillDataPushEventConfigService.getPropertyById(property.getId());
		}else{
			oper="add";
		}
		return "edit_property";
	}
	/**
	 * 
	 * @methodName save_config 
	 * @return String
	 */
	public String save_config(){
		if(config.getEventType()==DataPushEventType.PUSH_TO_INFOCLASS){
			config.setLocalTable(null);
		}else{
			config.setInfoClassId(null);
		}
		if("modify".equals(oper)){
			spBillDataPushEventConfigService.modifyDataPushEvent(config);
		}else{
			spBillDataPushEventConfigService.addDataPushEvent(config);
		}
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	/**
	 * 
	 * @methodName save_property 
	 * @return String
	 */
	public String save_property(){
		if("modify".equals(oper)){
			spBillDataPushEventConfigService.modifyDataPushProperty(property);
		}else{
			spBillDataPushEventConfigService.addDataPushProperty(property);
		}
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	/**
	 * 
	 * @methodName remove_config 
	 * @return String
	 */
	public String remove_config(){
		spBillDataPushEventConfigService.removeDataPushEvent(config.getId());
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	/**
	 * 
	 * @methodName remove_property 
	 * @return String
	 */
	public String remove_property(){
		spBillDataPushEventConfigService.removeDataPushProperty(property.getId());
		this.setSuccessMessage("保存成功");
		this.getValueStack().set(DATA, this.getMessage());
		return DATA;
	}
	
	
	public SpBillDataPushEventConfig getConfig() {
		return config;
	}

	public void setConfig(SpBillDataPushEventConfig config) {
		this.config = config;
	}

	public SpBillDataPushProperty getProperty() {
		return property;
	}

	public void setProperty(SpBillDataPushProperty property) {
		this.property = property;
	}
	
	public String getOper() {
		return oper;
	}
	public void setOper(String oper) {
		this.oper = oper;
	}
	public void setSpBillDataPushEventConfigService(
			ISpBillDataPushEventConfigService spBillDataPushEventConfigService) {
		this.spBillDataPushEventConfigService = spBillDataPushEventConfigService;
	}
	public void setSpBillConfigService(ISpBillConfigService spBillConfigService) {
		this.spBillConfigService = spBillConfigService;
	}
	
	
}
