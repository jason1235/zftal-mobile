package com.zfsoft.hrm.report.xentity;

import javax.xml.bind.annotation.XmlRootElement;

import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;

/**
 * 
 * @author ChenMinming
 * @date 2015-1-16
 * @version V1.0.0
 */
@XmlRootElement
public class ReportViewField {
	private String fieldDesc;
	private String fieldType;
	private String fieldName;
	private String fieldFormat;
	/**
	 * 返回
	 */
	public String getFieldDesc() {
		return fieldDesc;
	}
	/**
	 * 设置
	 * @param fieldDesc 
	 */
	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}
	/**
	 * 返回
	 */
	public String getFieldType() {
		return fieldType;
	}
	/**
	 * 设置
	 * @param fieldType 
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	/**
	 * 返回
	 */
	public String getFieldName() {
		return fieldName;
	}
	/**
	 * 设置
	 * @param fieldName 
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	/**
	 * 返回
	 */
	public String getFieldFormat() {
		return fieldFormat;
	}
	/**
	 * 设置
	 * @param fieldFormat 
	 */
	public void setFieldFormat(String fieldFormat) {
		this.fieldFormat = fieldFormat;
	}
	
	public String getFieldFormatText() {
		if("CODE".equals(fieldType)){
			Catalog c = CodeUtil.getCatalog(fieldFormat);
			if(c==null) return "";
			return c.getName();
		}
		return "";
	}
}
