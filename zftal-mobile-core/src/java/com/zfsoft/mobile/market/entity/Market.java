package com.zfsoft.mobile.market.entity;

import java.util.List;

public class Market {

	private String id;
	private String title;//物品标题
	private String content;
	private String pic;
	private Float price;
	private String type;
	private String tel;
	private String createUserId;
	private String createTime;
	private String campus;
	private String status;
	private String campusName;
	private Float oldPrice;
	private Integer isColl = 0;
	private String headImg;
	private List<String> pics;
	private String qNum;
	
	private String username;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
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
	public String getCampus() {
		return campus;
	}
	public void setCampus(String campus) {
		this.campus = campus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCampusName() {
		return campusName;
	}
	public void setCampusName(String campusName) {
		this.campusName = campusName;
	}
	public Integer getIsColl() {
		return isColl;
	}
	public void setIsColl(Integer isColl) {
		this.isColl = isColl;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getOldPrice() {
		return oldPrice;
	}
	public void setOldPrice(Float oldPrice) {
		this.oldPrice = oldPrice;
	}
	public String getHeadImg() {
		return headImg;
	}
	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}
	public List<String> getPics() {
		return pics;
	}
	public void setPics(List<String> pics) {
		this.pics = pics;
	}
	public String getqNum() {
		return qNum;
	}
	public void setqNum(String qNum) {
		this.qNum = qNum;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
