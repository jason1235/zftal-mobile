package com.zfsoft.hrm.yhfpgl.entity;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.yhfpgl.enums.MessageAllotTypeEnum;

/**
 * 
 * @author ChenMinming
 * @date 2015-7-20
 * @version V1.0.0
 */
public class MessageAllot extends BaseQuery{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3595350968801891003L;
	/**
	 * 
	 */
	private String mainId;//关联信息ID
	private String type;
	private String allotValue;
	private MessageAllotTypeEnum messageAllotType;
	private String allotValueText;
	/**
	 * 返回
	 */
	public String getMainId() {
		return mainId;
	}
	/**
	 * 设置
	 * @param mainId 
	 */
	public void setMainId(String mainId) {
		this.mainId = mainId;
	}
	/**
	 * 返回
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置
	 * @param type 
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 返回
	 */
	public String getAllotValue() {
		return allotValue;
	}
	/**
	 * 设置
	 * @param allotValue 
	 */
	public void setAllotValue(String allotValue) {
		this.allotValue = allotValue;
	}
	/**
	 * 返回
	 */
	public MessageAllotTypeEnum getMessageAllotType() {
		return messageAllotType;
	}
	/**
	 * 设置
	 * @param messageAllotType 
	 */
	public void setMessageAllotType(MessageAllotTypeEnum messageAllotType) {
		this.messageAllotType = messageAllotType;
	}
	/**
	 * 返回
	 */
	public String getAllotValueText() {
		return allotValueText;
	}
	/**
	 * 设置
	 * @param allotValueText 
	 */
	public void setAllotValueText(String allotValueText) {
		this.allotValueText = allotValueText;
	}
	
}
