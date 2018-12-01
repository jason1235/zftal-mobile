package com.zfsoft.hrm.summary.roster.util;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

/**
 * 花名册高级查询条件
 * 
 * @author gonghui
 * 2014-1-10
 */
public class RosterQueryCondForAdv {
	private String classId;//信息类id
	private String configId;//信息类属性id
	private String fieldValue;//属性值
	private String operator;//比较符号
	private String parenthesisBefore;//左括号
	private String parenthesisAfter;//右括号
	private String logicalRel;//逻辑关系
	
	private InfoClass clazz;
	private InfoProperty infoProperty;
	
	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getParenthesisBefore() {
		return parenthesisBefore;
	}
	public void setParenthesisBefore(String parenthesisBefore) {
		this.parenthesisBefore = parenthesisBefore;
	}
	public String getParenthesisAfter() {
		return parenthesisAfter;
	}
	public void setParenthesisAfter(String parenthesisAfter) {
		this.parenthesisAfter = parenthesisAfter;
	}
	public String getLogicalRel() {
		return logicalRel;
	}
	public void setLogicalRel(String logicalRel) {
		this.logicalRel = logicalRel;
	}
	public InfoProperty getInfoProperty() {
		return infoProperty;
	}
	public void setInfoProperty(InfoProperty infoProperty) {
		this.infoProperty = infoProperty;
	}
	public InfoClass getClazz() {
		return clazz;
	}
	public void setClazz(InfoClass clazz) {
		this.clazz = clazz;
	}
	
}
