package com.zfsoft.mobile.pushmsg.entity;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-22
 * @version V1.0.0
 */
public class PushRegistrationQuery {
	private String registrationId;
	private String appType;
	private Boolean exists;
	private String[] userIds;
	private String[] groupIds;
	private String sbType;
	/**
	 * 返回
	 */
	public String getRegistrationId() {
		return registrationId;
	}
	/**
	 * 设置
	 * @param registrationID 
	 */
	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}
	/**
	 * 返回
	 */
	public String[] getUserIds() {
		return userIds;
	}
	/**
	 * 设置
	 * @param userId 
	 */
	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}
	/**
	 * 返回
	 */
	public String[] getGroupIds() {
		return groupIds;
	}
	/**
	 * 设置
	 * @param groupId 
	 */
	public void setGroupIds(String[] groupIds) {
		this.groupIds = groupIds;
	}
	/**
	 * 返回
	 */
	public Boolean getExists() {
		return exists;
	}
	/**
	 * 设置
	 * @param exists 
	 */
	public void setExists(Boolean exists) {
		this.exists = exists;
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
