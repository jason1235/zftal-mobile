/**
 * 
 */
package com.zfsoft.mobile.services.entity;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author zhangxu
 * @description 
 * @date 2017-12-21 下午04:35:16
 */
public class ExpressCommentEntityForApp {
	
	private String commentId;//评论id
	private String commentContent;//评论内容
	private String expressId;// 主表id
	private Date createTime;//创建时间
	private String goodFlag;//是否点赞，0没有点赞，1已经点赞
	private String goodCount;//点赞次数	
	private String username;//评论人帐号
	private String xm;//评论人姓名
	
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	private String currentUserGoodFlag;//当前用户是否点赞该评论
	
	public String getCurrentUserGoodFlag() {
		return currentUserGoodFlag;
	}
	public void setCurrentUserGoodFlag(String currentUserGoodFlag) {
		this.currentUserGoodFlag = currentUserGoodFlag;
	}
	public String getGoodCount() {
		return goodCount;
	}
	public void setGoodCount(String goodCount) {
		this.goodCount = goodCount;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}
	public String getExpressId() {
		return expressId;
	}
	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getGoodFlag() {
		return goodFlag;
	}
	public void setGoodFlag(String goodFlag) {
		this.goodFlag = goodFlag;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getCommentId() {
		return commentId;
	}
	
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
