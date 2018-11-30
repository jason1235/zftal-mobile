package com.zfsoft.mobile.interest.entity;

import java.util.List;

/*
 * 圈子评论实体
 */
public class InterestComment {

	private String id;//
	private String interPostId;//帖子id
	private String userId;//评论人id
	private String commentId;//二级评论时一级评论的id
	private String commentUserId;//二级评论时被评论人的id
	private String createTime;//评论时间
	private String content;//评论内容
	
	private String userName;
	private String headPic;
	private String commentName;//被评论人名称
	
	private List<InterestComment> comments;//二级列表
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInterPostId() {
		return interPostId;
	}
	public void setInterPostId(String interPostId) {
		this.interPostId = interPostId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getCommentUserId() {
		return commentUserId;
	}
	public void setCommentUserId(String commentUserId) {
		this.commentUserId = commentUserId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHeadPic() {
		return headPic;
	}
	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}
	public List<InterestComment> getComments() {
		return comments;
	}
	public void setComments(List<InterestComment> comments) {
		this.comments = comments;
	}
	public String getCommentName() {
		return commentName;
	}
	public void setCommentName(String commentName) {
		this.commentName = commentName;
	}
	
	
	
}
