package com.zfsoft.mobile.interest.entity;

import com.zfsoft.dao.query.BaseQuery;


/*
 * 帖子实体
 */
public class InterestPostQuery extends BaseQuery{

	private String id;//
	private String interestId;//所属圈子id
	private String content;//帖子内容
	private String img;//帖子文件
	private String address;//帖子位置信息
	private Integer giveNum;//帖子点赞数
	private Integer collNum;//帖子收藏数
	private Integer isTop;//是否置顶 0不置顶 1.置顶
	private String  reportId;//举报原因 
	private String createUserId;
	private String createTime;
	
	private String userId;//用户id用来查询是否点赞

	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getInterestId() {
		return interestId;
	}
	public void setInterestId(String interestId) {
		this.interestId = interestId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getGiveNum() {
		return giveNum;
	}
	public void setGiveNum(Integer giveNum) {
		this.giveNum = giveNum;
	}
	public Integer getCollNum() {
		return collNum;
	}
	public void setCollNum(Integer collNum) {
		this.collNum = collNum;
	}
	public Integer getIsTop() {
		return isTop;
	}
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
