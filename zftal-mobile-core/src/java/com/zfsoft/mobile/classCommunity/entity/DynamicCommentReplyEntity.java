package com.zfsoft.mobile.classCommunity.entity;

/**
 * 评论回复
 * @author H110MF
 *
 */
public class DynamicCommentReplyEntity {
    private String id;   //唯一主键
    private String publisherId;//回复人id
    private String publisherName;//回复人姓名
    private String content; //回复内容
    private String createTime; //恢复时间
    private String commentId;  //评论id
    private String toPersonId; //回复对象id
    private String toPersonName;//回复对象姓名
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getToPersonId() {
		return toPersonId;
	}
	public void setToPersonId(String toPersonId) {
		this.toPersonId = toPersonId;
	}
	public String getToPersonName() {
		return toPersonName;
	}
	public void setToPersonName(String toPersonName) {
		this.toPersonName = toPersonName;
	}
}
