package com.zfsoft.hrm.sms.entities;

import java.util.Date;


/**
 * 
 * @author ChenMinming
 * @date 2013-10-9
 * @version V1.0.0
 */
public class SMSTemplate {
	
	private String id;
	//通知类型（唯一键）  取值参见com.zfsoft.hrm.sms.entities.SMSSendTypeEnum
	private String sendType;
	//模板内容
	private String content;
	//默认内容
	private String defaultCont;
	//最后修改时间
	private Date lastModify;
	/**
	 * 返回
	 */
	public String getSendType() {
		return sendType;
	}
	/**
	 * 设置
	 * @param sendType 
	 */
	public void setSendType(String sendType) {
		this.sendType = sendType;
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
	public String getDefaultCont() {
		return defaultCont;
	}
	/**
	 * 设置
	 * @param defaultCont 
	 */
	public void setDefaultCont(String defaultCont) {
		this.defaultCont = defaultCont;
	}
	/**
	 * 返回
	 */
	public Date getLastModify() {
		return lastModify;
	}
	/**
	 * 设置
	 * @param lastModify 
	 */
	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}
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
	
	
	
}
