package com.zfsoft.hrm.baseinfo.infoclass.entities;

/** 
 * 信息属性展现配置
 * @author jinjj
 * @date 2012-11-13 上午09:31:18 
 *  
 */
public class InfoPropertyView {

	private String classId;
	
	private String propertyId;
	
	private String username = "";
	
	private boolean allow = true;
	
	private String propertyName;
	
	public String getClassId() {
		return classId;
	}

	/**
	 * 信息类ID
	 * @param classId
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getPropertyId() {
		return propertyId;
	}

	/**
	 * 属性ID
	 * @param propertyId
	 */
	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public String getUsername() {
		return username;
	}

	/**
	 * 用户名
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAllow() {
		return allow;
	}

	public void setAllow(boolean allow) {
		this.allow = allow;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	
}
