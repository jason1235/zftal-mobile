package com.zfsoft.hrm.usermanage.entity;

/**
 * 
 * @author ChenMinming
 * @date 2014-1-13
 * @version V1.0.0
 */
public class DeleteItem {
	private String tableName;
	private String fieldName;
	private String value;
	
	public DeleteItem(){};
	
	public DeleteItem(String tableName, String fieldName, String value) {
		super();
		this.tableName = tableName;
		this.fieldName = fieldName;
		this.value = value;
	}

	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	

}
