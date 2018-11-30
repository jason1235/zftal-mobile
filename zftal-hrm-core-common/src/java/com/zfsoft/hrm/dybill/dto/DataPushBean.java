package com.zfsoft.hrm.dybill.dto;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.dybill.entity.SpBillDataPushProperty;

public class DataPushBean {
	
	private List<SpBillDataPushProperty> properties;
	
	private Map<Long, String> valueMap;
	
	private String tableName;
	
	private String expression;


	public List<SpBillDataPushProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<SpBillDataPushProperty> properties) {
		this.properties = properties;
	}

	public Map<Long, String> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<Long, String> valueMap) {
		this.valueMap = valueMap;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	
}
