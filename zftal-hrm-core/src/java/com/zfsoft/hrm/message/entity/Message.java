package com.zfsoft.hrm.message.entity;

import java.util.Arrays;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/** 
 * 消息提示
 * @author jinjj
 * @date 2012-7-30 下午03:17:40 
 *  
 */
public class Message {

	private String guid;
	
	private String title;
	
	private String sender;
	
	private String receiver;
	
	private String content;
	
	private Date sendTime;
	
	private Date readTime;
	
	private String role;
	
	private String roleMc;
	
	private String receiverType;
	
	public String getThreeReceivers(){
		if(StringUtils.isEmpty(receiver)){
			return "";
		}
		String[] re=receiver.split(";");
		if(re.length<=4){
			return receiver;
		}else{
			StringBuilder sb=new StringBuilder(Arrays.toString(re));
			for (int i = 0; i < 4; i++) {
				sb.append(re[i]);
				sb.append(";");
			}
			return sb.deleteCharAt(sb.length()-1).toString();
		}
	}

	/**
	 * 消息编号
	 * @return
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * 消息编号
	 * @param guid
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 发送人
	 * @return
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * 发送人
	 * @param sender
	 */
	public void setSender(String sender) {
		this.sender = sender;
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
	 * 内容
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 内容
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 发送时间
	 * @return
	 */
	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * 发送时间
	 * @param sendTime
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	/**
	 * 标题
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 标题
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	/**
	 * 角色
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * 角色
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * 角色名称
	 * @return the roleMc
	 */
	public String getRoleMc() {
		return roleMc;
	}

	/**
	 * 角色名称
	 * @param roleMc the roleMc to set
	 */
	public void setRoleMc(String roleMc) {
		this.roleMc = roleMc;
	}

	/**
	 * 接收者类型
	 * @return the receiverType
	 */
	public String getReceiverType() {
		return receiverType;
	}

	/**
	 * 接收者类型
	 * @param receiverType the receiverType to set
	 */
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	
	public String getReceiverTypeMc() {
		// 个人
		if ("0".equals(this.receiverType)) {
			return "个人";
		// 角色
		} else if ("1".equals(this.receiverType)) {
			return "角色";
		} else {
			return "";
		}
	}
	
}
