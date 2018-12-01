package com.zfsoft.hrm.dybill.entity;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
/**
 * 数据推送属性对象
 * @className: SpBillDataPushProperty 
 * @author Patrick Shen shenluwei@126.com
 * @date 2013-8-9 下午02:18:54
 */
@Table("sp_bill_datapush_property")
public class SpBillDataPushProperty extends MyBatisBean{
	/**
	 * 编号
	 */
	@SQLField(key=true)
	private String id;
	/**
	 * 事件配置编号
	 */
	@SQLField("event_config_id")
	private String eventConfigId;
	/**
	 * 源属性
	 */
	@SQLField("bill_property_id")
	private Long billPropertyId;
	/**
	 * 目标属性
	 */
	@SQLField("local_property_id")
	private String localPropertyId;
	
	/**
	 * 源名称
	 */
	private String billPropertyName;
	
	/**
	 * 源名称
	 */
	private String infoPropertyName;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEventConfigId() {
		return eventConfigId;
	}
	public void setEventConfigId(String eventConfigId) {
		this.eventConfigId = eventConfigId;
	}
	public Long getBillPropertyId() {
		return billPropertyId;
	}
	public void setBillPropertyId(Long billPropertyId) {
		this.billPropertyId = billPropertyId;
	}
	
	public String getBillPropertyName() {
		return billPropertyName;
	}
	public void setBillPropertyName(String billPropertyName) {
		this.billPropertyName = billPropertyName;
	}
	public String getLocalPropertyId() {
		return localPropertyId;
	}
	public void setLocalPropertyId(String localPropertyId) {
		this.localPropertyId = localPropertyId;
	}
	public String getInfoPropertyName() {
		return infoPropertyName;
	}
	public void setInfoPropertyName(String infoPropertyName) {
		this.infoPropertyName = infoPropertyName;
	}
	
	
}
