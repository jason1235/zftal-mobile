package com.zfsoft.hrm.message.entity;

import java.util.Date;

/** 
 * 消息状态
 * @author jinjj
 * @date 2012-7-30 下午03:22:57 
 *  
 */
public class MessageStatus {

	private String guid;
	
	private String messageId;
	
	private String receiver;
	
	private Date readTime;

	/**
	 * 唯一编号
	 * @return
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * 唯一编号
	 * @param guid
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 消息ID
	 * @return
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * 消息ID
	 * @param messagId
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * 接收人
	 * @return
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * 接收人
	 * @param receiver
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	/**
	 * 阅读时间
	 * @return
	 */
	public Date getReadTime() {
		return readTime;
	}

	/**
	 * 阅读时间
	 * @param readTime
	 */
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	
}
