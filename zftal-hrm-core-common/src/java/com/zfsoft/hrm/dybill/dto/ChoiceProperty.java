package com.zfsoft.hrm.dybill.dto;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.dybill.xml.XmlBillProperty;

public class ChoiceProperty {
	private InfoProperty infoProperty;
	private XmlBillProperty billProperty;
	private Boolean checked=false;
	
	public InfoProperty getInfoProperty() {
		return infoProperty;
	}
	public void setInfoProperty(InfoProperty infoProperty) {
		this.infoProperty = infoProperty;
	}
	public XmlBillProperty getBillProperty() {
		return billProperty;
	}
	public void setBillProperty(XmlBillProperty billProperty) {
		this.billProperty = billProperty;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
}
