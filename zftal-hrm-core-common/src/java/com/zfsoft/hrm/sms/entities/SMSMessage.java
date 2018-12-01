package com.zfsoft.hrm.sms.entities;

/**
 * 
 * @author ChenMinming
 * @date 2013-10-9
 * @version V1.0.0
 */
public class SMSMessage {
	//要发送的手机号码，多个号码时请用英文逗号分隔
	private String mobiles;
	//短信内容
	private String content;
	//系统分配给外部系统的接口ID
	private String smid;
	//系统分配给外部系统的接口密码
	private String key;
	//发送状态，0为待发送，1为已发送，负数为出错
	private String state="0";
	/**
	 * 返回
	 */
	public String getMobiles() {
		return mobiles;
	}
	/**
	 * 设置
	 * @param mobiles 
	 */
	public void setMobiles(String mobiles) {
		this.mobiles = mobiles;
	}
	/**
	 * 返回
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置
	 * @param content 
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 返回
	 */
	public String getSmid() {
		return smid;
	}
	/**
	 * 设置
	 * @param smid 
	 */
	public void setSmid(String smid) {
		this.smid = smid;
	}
	/**
	 * 返回
	 */
	public String getKey() {
		return key;
	}
	/**
	 * 设置
	 * @param key 
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * 返回
	 */
	public String getState() {
		return state;
	}
	/**
	 * 设置
	 * @param state 
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	
}
