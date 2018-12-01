package com.zfsoft.mobile.helpguide.entity;

import java.util.Date;

public class HelpGuideEntity {

	private String id;
	private String title;
	private String content;
	private int orderNumber;
	private Date createTime;
	private String isPlayed;
	private String isHot;
	
	/**
	 *返回
	 *主键id 
	 */
	public String getId() {
		return id;
	}
	/**
	 *设置 id 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 *返回主题 
	 */
	public String getTitle() {
		return title;
	}
	/**
	 *设置主题
	 *@param title 
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 *返回内容 
	 */
	public String getContent() {
		return content;
	}
	/**
	 *设置内容
	 *@param content 
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 *返回排序 
	 */
	public int getOrderNumber() {
		return orderNumber;
	}
	/**
	 *设置排序
	 *@param  orderNumber
	 */
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 *返回创建时间 
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 *设置创建时间 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 *返回状态，0为停用，1为启用 
	 */
	public String getIsPlayed() {
		return isPlayed;
	}
	/**
	 * 设置状态，0为停用，1为启用 
	 * @param isPlayed
	 */
	public void setIsPlayed(String isPlayed) {
		this.isPlayed = isPlayed;
	}
	
	/**
	 *返回热门，0为取消热门设置，1为设置热门
	 */
	public String getIsHot() {
		return isHot;
	}
	/**
	 * 设置热门，0为取消热门设置，1为设置热门
	 * @param isHot
	 */
	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}
	
	
	
}
