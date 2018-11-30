package com.zfsoft.hrm.dybill.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class XmlCatchField {
	private Long billPropertyId;

	private String fieldName;

	private XmlBillProperty billProperty;

	/**
	 * 返回
	 */
	@XmlAttribute
	public Long getBillPropertyId() {
		return billPropertyId;
	}

	/**
	 * 设置
	 * 
	 * @param billPropertyId
	 */
	public void setBillPropertyId(Long billPropertyId) {
		this.billPropertyId = billPropertyId;
	}

	/**
	 * 返回
	 */
	@XmlTransient
	public XmlBillProperty getBillProperty() {
		return billProperty;
	}

	/**
	 * 设置
	 * 
	 * @param billProperty
	 */
	public void setBillProperty(XmlBillProperty billProperty) {
		if (this.billPropertyId == null && billProperty != null) {
			this.billPropertyId = billProperty.getId();
		}
		this.billProperty = billProperty;
	}

	/**
	 * 返回
	 */
	@XmlAttribute
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 设置
	 * 
	 * @param fieldName
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

}
