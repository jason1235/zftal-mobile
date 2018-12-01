package com.zfsoft.mobile.peEvaluation.query;

import com.zfsoft.dao.query.BaseQuery;

public class ClassScoreQuery extends BaseQuery{
	
	private String columnStr; //列名
	private String columnData; //列值
	private String dataType;   //0平时测评,1国测
	private String classId;    //班级ID
	private String schoolNumber; //学籍号
	private String operator;     //比较运算符
	private String orderKey;	//排序顺序
	
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getColumnData() {
		return columnData;
	}
	public void setColumnData(String columnData) {
		this.columnData = columnData;
	}
	public String getSchoolNumber() {
		return schoolNumber;
	}
	public void setSchoolNumber(String schoolNumber) {
		this.schoolNumber = schoolNumber;
	}
	public void setColumnStr(String columnStr) {
		this.columnStr = columnStr;
	}
	public String getColumnStr() {
		return columnStr;
	}
	public void setColumnsStr(String columnStr) {
		this.columnStr = columnStr;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getOrderKey() {
		return orderKey;
	}
	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	
	
	
}
