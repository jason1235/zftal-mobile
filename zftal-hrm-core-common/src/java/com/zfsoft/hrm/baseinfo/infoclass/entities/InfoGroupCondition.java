package com.zfsoft.hrm.baseinfo.infoclass.entities;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.hrm.baseinfo.code.entities.Item;

public class InfoGroupCondition {
	private String guid; 
	private String name;//组合查询名称
	private String catalogName;//组合查询类目
	private String fieldName;//组合查询字段
	private String codeTableName;//引用代码
	private List<Item> values;
	
	private boolean checked;
	
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getCodeTableName() {
		return codeTableName;
	}
	public void setCodeTableName(String codeTableName) {
		this.codeTableName = codeTableName;
	}
	
	public List<Item> getValues() {
		if(values==null)values=new ArrayList<Item>();
		return values;
	}
	public void setValues(List<Item> values) {
		this.values = values;
	}
}
