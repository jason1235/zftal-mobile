package com.zfsoft.hrm.ddselect.entities;

import java.io.Serializable;

/**
 * 
 * @author yxlong
 * 2013-8-2
 */
public class Fields implements Serializable {

	private static final long serialVersionUID = -5955296328803342082L;
	
	private String tableName;
	
	private String fieldName;
	
	private String fieldChineseName;
	
	private String fieldtype;
	
	private String fieldLength;
	
	private String fieldDefalutValue;
	
	private VacantEnum vacant;

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the fieldChineseName
	 */
	public String getFieldChineseName() {
		return fieldChineseName;
	}

	/**
	 * @param fieldChineseName the fieldChineseName to set
	 */
	public void setFieldChineseName(String fieldChineseName) {
		this.fieldChineseName = fieldChineseName;
	}

	/**
	 * @return the fieldtype
	 */
	public String getFieldtype() {
		return fieldtype;
	}

	/**
	 * @param fieldtype the fieldtype to set
	 */
	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

	/**
	 * @return the fieldLength
	 */
	public String getFieldLength() {
		return fieldLength;
	}

	/**
	 * @param fieldLength the fieldLength to set
	 */
	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}

	/**
	 * @return the fieldDefalutValue
	 */
	public String getFieldDefalutValue() {
		return fieldDefalutValue;
	}

	/**
	 * @param fieldDefalutValue the fieldDefalutValue to set
	 */
	public void setFieldDefalutValue(String fieldDefalutValue) {
		this.fieldDefalutValue = fieldDefalutValue;
	}

	/**
	 * @return the vacant
	 */
	public VacantEnum getVacant() {
		return vacant;
	}

	/**
	 * @param vacant the vacant to set
	 */
	public void setVacant(VacantEnum vacant) {
		this.vacant = vacant;
	}
}
