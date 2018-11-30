package com.zfsoft.hrm.baseinfo.custommenu.entity;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;

public class Condition {
	
	private String name;// 标示
	private String value;// 值
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getCodeId(){
		if(!StringUtils.isEmpty(getName()))
			return InfoClassCache.getOverallInfoClass().getPropertyById(getName()) == null ? null : InfoClassCache.getOverallInfoClass().getPropertyById(getName()).getCodeId();
		else
			return null;
	}
}