package com.zfsoft.mobile.configuration.entity;

/**
 * 
 * @author Administrator
 * 系统设置表(M_SYSTEM_SETTINGS)
 *
 */
public class NewsConfig {
	
	private String key; // sk
	private String value; // sv
	private String comment; // sc
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
}
