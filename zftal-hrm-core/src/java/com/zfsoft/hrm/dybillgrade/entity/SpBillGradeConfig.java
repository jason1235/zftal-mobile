package com.zfsoft.hrm.dybillgrade.entity;

import java.util.List;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
import com.zfsoft.util.jaxb.JaxbUtil;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-5
 * @version V1.0.0
 */
@Table("sp_bill_grade_config")
public class SpBillGradeConfig  extends MyBatisBean{
	
	@SQLField(key=true)
	private String id;
	@SQLField
	private String name;
	@SQLField("business_code")
	private String businessCode;
	@SQLField("sp_config_id")
	private String billConfigId;
	
	private List<SpBillGradeCondition> conditions;
	@SQLField("level_detail")
	private String level_detail;
	
	private SpBillGradeLevelConfig levelConfig;
	/**
	 * 返回
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 返回
	 */
	public String getBusinessCode() {
		return businessCode;
	}
	
	public String getBusinessCodeText() {
		try{
			return com.zfsoft.hrm.dybillgrade.enums.GradeBusinessEnums.valueOf(businessCode).getText();
		}catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 设置
	 * @param businessCode 
	 */
	public void setBusiness_code(String businessCode) {
		this.businessCode = businessCode;
	}
	/**
	 * 设置
	 * @param businessCode 
	 */
	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}
	/**
	 * 返回
	 */
	public String getBillConfigId() {
		return billConfigId;
	}
	/**
	 * 设置
	 * @param billConfigId 
	 */
	public void setBillConfigId(String billConfigId) {
		this.billConfigId = billConfigId;
	}
	public void setSp_config_id(String billConfigId) {
		this.billConfigId = billConfigId;
	}
	/**
	 * 返回
	 */
	public List<SpBillGradeCondition> getConditions() {
		return conditions;
	}
	/**
	 * 设置
	 * @param conditions 
	 */
	public void setConditions(List<SpBillGradeCondition> conditions) {
		this.conditions = conditions;
	}
	/**
	 * 返回
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 返回
	 */
	public String getLevel_detail() {
		if(levelConfig==null){
			levelConfig = new SpBillGradeLevelConfig();
		}
		level_detail = JaxbUtil.getXmlFromObject(levelConfig);
		return level_detail;
	}
	/**
	 * 设置
	 * @param levelDetail 
	 */
	public void setLevel_detail(String levelDetail) {
		level_detail = levelDetail;
		levelConfig = JaxbUtil.getObjectFromXml(level_detail, SpBillGradeLevelConfig.class);
	}
	/**
	 * 返回
	 */
	public SpBillGradeLevelConfig getLevelConfig() {
		return levelConfig;
	}
	/**
	 * 设置
	 * @param levelConfig 
	 */
	public void setLevelConfig(SpBillGradeLevelConfig levelConfig) {
		this.levelConfig = levelConfig;
	}
	
	
}
