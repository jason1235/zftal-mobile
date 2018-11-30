package com.zfsoft.hrm.baseinfo.forminfo.entities;

import java.io.Serializable;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

/**
 * 登记类别元数据Entity
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-8
 * @version V1.0.0
 */
public class FormInfoMetadata implements Serializable{
	
	private static final long serialVersionUID = 4405704779427431586L;
	private String guid;
	
	private String formInfoTypeId;		//登记类别表和信息类关联表编号
	
	private InfoProperty infoProperty;	//信息类属性

	private Boolean viewable = true;	//可显示
	
	private Boolean editable = true;	//可编辑
	
	private Boolean need = false;		//必填
	
	private String defaultValue = "";	//默认值
	
	private boolean checked = false;

	public String getFormInfoTypeId() {
		return formInfoTypeId;
	}

	public void setFormInfoTypeId(String formInfoTypeId) {
		this.formInfoTypeId = formInfoTypeId;
	}

	public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public InfoProperty getInfoProperty() {
		return infoProperty;
	}

	public void setInfoProperty(InfoProperty infoProperty) {
		this.infoProperty = infoProperty;
	}

	public Boolean getViewable() {
		return viewable;
	}

	public void setViewable(Boolean viewable) {
		this.viewable = viewable;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}

	public Boolean getNeed() {
		return need;
	}

	public void setNeed(Boolean need) {
		this.need = need;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public void copyToValueInfoProperty(){
		if(this.getInfoProperty()==null)return;
		this.getInfoProperty().setDefaultValue(this.getDefaultValue());
		this.getInfoProperty().setEditable(this.getEditable());
		this.getInfoProperty().setViewable(this.getViewable());
		this.getInfoProperty().setNeed(this.getNeed());
	}
	public void copyInfoPropertyValueToThis(){
		if(this.getInfoProperty()==null)return;
		this.setDefaultValue(this.getInfoProperty().getDefaultValue());
		this.setEditable(this.getInfoProperty().getEditable());
		this.setViewable(this.getInfoProperty().getViewable());
		this.setNeed(this.getInfoProperty().getNeed());
	}
	
}
