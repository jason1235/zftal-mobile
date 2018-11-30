package com.zfsoft.wechat.login.entity;

/**
 * 
 * @author ChenMinming
 * @date 2015-6-26
 * @version V1.0.0
 */
public class WeChatBinding {
	private String bindId;
	private String yhm;
	private String mm;
	/**
	 * 返回
	 */
	public String getBindId() {
		return bindId;
	}
	/**
	 * 设置
	 * @param bindId 
	 */
	public void setBindId(String bindId) {
		this.bindId = bindId;
	}
	/**
	 * 返回
	 */
	public String getYhm() {
		return yhm;
	}
	/**
	 * 设置
	 * @param yhm 
	 */
	public void setYhm(String yhm) {
		this.yhm = yhm;
	}
	/**
	 * 返回
	 */
	public String getMm() {
		return mm;
	}
	/**
	 * 设置
	 * @param mm 
	 */
	public void setMm(String mm) {
		this.mm = mm;
	}
	
}
