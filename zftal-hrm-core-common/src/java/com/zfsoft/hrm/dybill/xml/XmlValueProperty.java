package com.zfsoft.hrm.dybill.xml;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
/**
 * 审批表单实例
 * 版权声明 
 * 作者 沈鲁威 
 * 时间 2013-06-08
 * @author Patrick Shen
 */
@XmlRootElement
public class XmlValueProperty {
	
	private Long billPropertyId;
	
	private String value;
	
	private String newValue;
	
	private XmlBillProperty billProperty;
	
	@XmlAttribute
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	@XmlAttribute
	public Long getBillPropertyId() {
		return billPropertyId;
	}
	public void setBillPropertyId(Long billPropertyId) {
		this.billPropertyId = billPropertyId;
	}
	
	@XmlAttribute
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@XmlTransient
	public XmlBillProperty getBillProperty() {
		return billProperty;
	}
	public void setBillProperty(XmlBillProperty billProperty) {
		this.billProperty = billProperty;
	}
	
}
