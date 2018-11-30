package com.zfsoft.mobile.interest.entity;

/*
 * 兴趣圈实体
 */
public class InterestEntity {

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
	private Integer blackNum;//拉黑人数
	private String adminisId;//管理员id
	private String postName;//帖子名称
	private Integer isJoin;//是否已加入圈子 ,0为未加入
	
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
	public String getAdminisId() {
		return adminisId;
	}
	public void setAdminisId(String adminisId) {
		this.adminisId = adminisId;
	}
	public Integer getBlackNum() {
		return blackNum;
	}
	public void setBlackNum(Integer blackNum) {
		this.blackNum = blackNum;
	}
	public Integer getIsJoin() {
		return isJoin;
	}
	public void setIsJoin(Integer isJoin) {
		this.isJoin = isJoin;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	
	
}
