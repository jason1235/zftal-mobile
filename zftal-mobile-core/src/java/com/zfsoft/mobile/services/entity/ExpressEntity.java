/**
 * 
 */
package com.zfsoft.mobile.services.entity;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.query.BaseQuery;

/**
 * @author zhangxu
 * @description 
 * @date 2017-12-21 下午02:14:39
 */
public class ExpressEntity extends BaseQuery{

	private String expressId;//主表id
	private String title;//标题
	private String textContent;//文本内容
	private String theType;//0是帅哥，1是美女
	private String userName;//发布人姓名
	private Date createTime;//创建时间
	private String xm;
	private int  countComment;//评论计数
	private String anonymous;//是否匿名0不是 1是
	
	private List<ExpressPicEntity> picList;//图片列表
	
	public String getExpressId() {
		return expressId;
	}
	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTextContent() {
		return textContent;
	}
	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}
	public String getTheType() {
		return theType;
	}
	public void setTheType(String theType) {
		this.theType = theType;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setCountComment(int countComment) {
		this.countComment = countComment;
	}
	public int getCountComment() {
		return countComment;
	}
	public void setPicList(List<ExpressPicEntity> picList) {
		this.picList = picList;
	}
	public List<ExpressPicEntity> getPicList() {
		return picList;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getXm() {
		return xm;
	}
	public String getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(String anonymous) {
		this.anonymous = anonymous;
	}
	
	
}
