package com.zfsoft.mobile.interest.entity;

import java.util.List;

import com.zfsoft.dao.query.BaseQuery;

/*
 * 兴趣圈实体
 */
public class InterestQuery extends BaseQuery {

	private String id;
	private String name;//圈子名称
	private String img;//圈子图片
	private String createUserId;//创建人id
	private Integer follow;//关注人数
	private String status;//圈子状态
	private String content;//圈子描述
	private String type;//圈子分类
	private Integer postNum;//帖子数
	private String idCard;//学生证
	private String title;//成员头衔
	private String createTime;//创建时间
	private String blackNum;//拉黑人数
	private String adminisId;//管理员id
	
	private String userId;//我的圈子用户id
	private String orderCode;//排序条件
	private String isJoin;//是否已经加入圈子标志参数 isJoin 0未加入 ，1或大于1已加入
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getFollow() {
		return follow;
	}
	public void setFollow(Integer follow) {
		this.follow = follow;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getPostNum() {
		return postNum;
	}
	public void setPostNum(Integer postNum) {
		this.postNum = postNum;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getBlackNum() {
		return blackNum;
	}
	public void setBlackNum(String blackNum) {
		this.blackNum = blackNum;
	}
	public String getAdminisId() {
		return adminisId;
	}
	public void setAdminisId(String adminisId) {
		this.adminisId = adminisId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getIsJoin() {
		return isJoin;
	}
	public void setIsJoin(String isJoin) {
		this.isJoin = isJoin;
	}
	
	
}
