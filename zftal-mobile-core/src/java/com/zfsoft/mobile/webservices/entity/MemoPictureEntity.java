package com.zfsoft.mobile.webservices.entity;

import java.util.Date;

public class MemoPictureEntity {
	private String memoFileName;
	private String title;
	private byte[] pictureContent;
	private String picturePath;
	private Date createTime;
	public String getMemoFileName() {
		return memoFileName;
	}
	public void setMemoFileName(String memoFileName) {
		this.memoFileName = memoFileName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public byte[] getPictureContent() {
		return pictureContent;
	}
	public void setPictureContent(byte[] pictureContent) {
		this.pictureContent = pictureContent;
	}
	public String getPicturePath() {
		return picturePath;
	}
	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
