/**
 * 
 */
package com.zfsoft.mobile.services.entity;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/**
 * @author zhangxu
 * @description 
 * @date 2017-12-21 下午02:23:07
 */
public class ExpressCommentEntity extends BaseQuery{
	
	private String commentId;//评论id
	private String commentContent;//评论内容
	private String expressId;// 主表id
	private Date createTime;//创建时间
	private String goodFlag;//是否点赞，0没有点赞，1已经点赞
	private String username;//用户帐号
	private String xm;//用户姓名
	private String anonymous;//评论是否匿名 1是0否
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
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
	public String getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}
}
