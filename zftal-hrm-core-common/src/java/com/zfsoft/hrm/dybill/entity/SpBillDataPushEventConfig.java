package com.zfsoft.hrm.dybill.entity;

import java.util.List;

import com.zfsoft.dao.annotation.MyBatisBean;
import com.zfsoft.dao.annotation.SQLField;
import com.zfsoft.dao.annotation.Table;
import com.zfsoft.hrm.dybill.enums.DataPushEventOpType;
import com.zfsoft.hrm.dybill.enums.DataPushEventType;

/**
 * 数据推送事件配置对象
 * @className: SpBillDataPushEventConfig 
 * @author Patrick Shen shenluwei@126.com
 * @date 2013-8-9 下午02:19:20
 */
@Table("sp_bill_datapush_event_config")
public class SpBillDataPushEventConfig extends MyBatisBean{
	@SQLField(key=true)
	private String id;						//标号
	@SQLField
	private String name;					//事件名称
	@SQLField("bill_config_id")
	private String billConfigId;			//动态表单配置编号
	@SQLField("bill_class_id")
	private Long billClassId;				//动态表单编号
	@SQLField("event_type")
	private DataPushEventType eventType;	//事件类型
	@SQLField("info_class_id")
	private String infoClassId;				//信息类编号
	@SQLField("event_op_type")
	private DataPushEventOpType eventOpType;//事件操作类型
	@SQLField("where_expression")
	private String whereExpression;			//更新时的条件表达式
	@SQLField("local_table")
	private String localTable; 				//事件类型为信息类推送时使用
	
	private String billConfigName;
	
	private String billClassName;
	
	private String infoClassName;
	
	private List<SpBillDataPushProperty> properties;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBillConfigId() {
		return billConfigId;
	}

	public void setBillConfigId(String billConfigId) {
		this.billConfigId = billConfigId;
	}

	public Long getBillClassId() {
		return billClassId;
	}

	public void setBillClassId(Long billClassId) {
		this.billClassId = billClassId;
	}

	public DataPushEventType getEventType() {
		return eventType;
	}

	public void setEventType(DataPushEventType eventType) {
		this.eventType = eventType;
	}

	public String getInfoClassId() {
		return infoClassId;
	}

	public void setInfoClassId(String infoClassId) {
		this.infoClassId = infoClassId;
	}

	public DataPushEventOpType getEventOpType() {
		return eventOpType;
	}

	public void setEventOpType(DataPushEventOpType eventOpType) {
		this.eventOpType = eventOpType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWhereExpression() {
		return whereExpression;
	}

	public void setWhereExpression(String whereExpression) {
		this.whereExpression = whereExpression;
	}

	public String getLocalTable() {
		return localTable;
	}

	public void setLocalTable(String localTable) {
		this.localTable = localTable;
	}

	public List<SpBillDataPushProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<SpBillDataPushProperty> properties) {
		this.properties = properties;
	}

	public String getBillConfigName() {
		return billConfigName;
	}

	public void setBillConfigName(String billConfigName) {
		this.billConfigName = billConfigName;
	}

	public String getBillClassName() {
		return billClassName;
	}

	public void setBillClassName(String billClassName) {
		this.billClassName = billClassName;
	}

	public String getInfoClassName() {
		return infoClassName;
	}

	public void setInfoClassName(String infoClassName) {
		this.infoClassName = infoClassName;
	}
	
}
