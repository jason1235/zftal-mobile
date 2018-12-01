package com.zfsoft.mobile.market.entity;

import java.util.List;

/*
 * 跳蚤市场评论 
 */
public class MarketComment {
	private String id;
	private String marketId;
	private String content;
	private String createUserId;
	private String commentId;
	private String createTime;
	private String userName;
	private String headPic;
	
	private List<MarketComment> comments;//二级评论列表
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMarketId() {
		return marketId;
	}
	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCommentId() {
		return commentId;
	}
	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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
	public List<MarketComment> getComments() {
		return comments;
	}
	public void setComments(List<MarketComment> comments) {
		this.comments = comments;
	}
	
	
	
}
