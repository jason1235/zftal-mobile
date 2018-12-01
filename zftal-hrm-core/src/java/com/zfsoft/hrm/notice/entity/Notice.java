package com.zfsoft.hrm.notice.entity;

import java.util.Date;


/** 
 * 通知
 * @author jinjj
 * @date 2012-9-26 上午09:07:47 
 *  
 */
public class Notice extends NoticeAttachement {

	private String guid;
	
	private String title;
	
	private Date createTime;
	
	private String author;
	
	private String content;
	
	private String remark;
	
	private String attachmentId;
	
	private int status;
	
	private int top;

	/**
	 * 主键
	 * @return
	 */
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 标题
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 创建时间
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 作者
	 * @return
	 */
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 内容
	 * @return
	 */
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 附件ID
	 * @return
	 */
	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	/**
	 * 发布状态
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 置顶
	 * @return
	 */
	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}
	
}
