package com.zfsoft.mobile.pushmsg.entity;

/**
 * 
 * @author ChenMinming
 * @date 2015-5-9
 * @version V1.0.0
 */
public class AppTypeConfig {
	private String appType;
	private String appKey;
	private String masterSecret;
	private String detail;
	private String pushPlatform; //推送平台
	
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
	/**
	 * 返回
	 */
	public String getAppKey() {
		return appKey;
	}
	/**
	 * 设置
	 * @param appKey 
	 */
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	/**
	 * 返回
	 */
	public String getMasterSecret() {
		return masterSecret;
	}
	/**
	 * 设置
	 * @param masterSecret 
	 */
	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}
	/**
	 * 返回
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * 设置
	 * @param detail 
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getPushPlatform() {
		return pushPlatform;
	}
	public void setPushPlatform(String pushPlatform) {
		this.pushPlatform = pushPlatform;
	}
}
