package com.zfsoft.hrm.message.query;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * 消息查询实体
 * @author jinjj
 * @date 2012-8-27 上午09:46:32 
 *  
 */
public class MessageQuery extends BaseQuery {

	private static final long serialVersionUID = 1616921466911801734L;

	private String receiver;//消息接收人工号
	
	private String sender;
	
	private String name;
	
	private String type;//消息类型
	
	private String status;

	/**
	 * 接收人工号
	 * @return
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * 接收人工号
	 * @param receiver
	 */
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	/**
	 * 消息类型
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 消息类型
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 消息状态  null:全部 0未读 1已读
	 * @return
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 消息标题
	 * @return
	 */
	public String getName() {
		return name;
	}

	public String getStatus() {
		return status;
	}

	/**
	 * 发送人
	 * @return
	 */
	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
}
