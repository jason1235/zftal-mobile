package com.zfsoft.mobile.pushmsg.entity;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-22
 * @version V1.0.0
 */
public class PushRegistration {
	private String id;
	private String userId;
	private String registrationId;
	private String appType;
	private String sbType;// 设备类型:MIUI|EMUI|JPUSH
	/**
	 * 返回
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 返回
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置
	 * @param userId 
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 返回
	 */
	public String getRegistrationId() {
		return registrationId;
	}
	/**
	 * 设置
	 * @param registrationId 
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	/**
	 * 返回
	 */
	public String getAppType() {
		return appType;
	}
	/**
	 * 设置
	 * @param appType 
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}
	public String getSbType() {
		return sbType;
	}
	public void setSbType(String sbType) {
		this.sbType = sbType;
	}
}
