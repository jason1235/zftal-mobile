package com.zfsoft.mobile.helpguide.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

public class HelpGuideQuery extends BaseQuery{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String title;
	private String content;
	private int orderNumber=0;
	private Date createTime;
	private String isPlayed;
	private String isHot;
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
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getIsPlayed() {
		return isPlayed;
	}
	public void setIsPlayed(String isPlayed) {
		this.isPlayed = isPlayed;
	}
	public String getIsHot() {
		return isHot;
	}
	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	
	
}
