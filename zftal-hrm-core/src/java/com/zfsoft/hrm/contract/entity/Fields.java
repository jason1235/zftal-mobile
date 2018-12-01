package com.zfsoft.hrm.contract.entity;

import java.io.Serializable;

/**
 * 合同导入
 */
public class Fields implements Serializable{
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = 7866704236594912105L;
	private String table_name;       //表名
	private String column_name;		 //字段名
	private String comments;		 //描述
	
	private String data_type;		 //字段类型
	private String data_length;		 //字段长度
	
	public String getData_type() {
		return data_type;
	}
	public void setData_type(String data_type) {
		this.data_type = data_type;
	}
	public String getData_length() {
		return data_length;
	}
	public void setData_length(String data_length) {
		this.data_length = data_length;
	}
	public String getTable_name() {
		return table_name;
	}
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
}
