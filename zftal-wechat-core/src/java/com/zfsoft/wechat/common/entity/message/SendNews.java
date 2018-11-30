package com.zfsoft.wechat.common.entity.message;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.util.base.StringUtil;
import com.zfsoft.wechat.common.enums.NewsTypeEnum;

/**
 * 
 * @author ChenMinming
 * @date 2015-7-27
 * @version V1.0.0
 */
public class SendNews {
	private String messageId;
	private NewsTypeEnum type;
	private String content;
	private String mediaId;
	private List<String> openIds;
	/**
	 * 返回
	 */
	public NewsTypeEnum getType() {
		return type;
	}
	/**
	 * 设置
	 * @param type 
	 */
	public void setType(NewsTypeEnum type) {
		this.type = type;
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
	public String getMediaId() {
		return mediaId;
	}
	/**
	 * 设置
	 * @param mediaId 
	 */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	/**
	 * 返回
	 */
	public List<String> getOpenIds() {
		return openIds;
	}
	/**
	 * 设置
	 * @param openIds 
	 */
	public void setOpenIds(List<String> openIds) {
		this.openIds = openIds;
	}
	
	public void addOpenId(String id){
		if(StringUtil.isEmpty(id)) return;
		if(openIds==null){
			openIds=new ArrayList<String>();
		}
		openIds.add(id);
	}
	/**
	 * 返回
	 */
	public String getMessageId() {
		return messageId;
	}
	/**
	 * 设置
	 * @param messageId 
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
