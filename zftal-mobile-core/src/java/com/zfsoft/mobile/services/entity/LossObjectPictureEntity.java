/**
 * 
 */
package com.zfsoft.mobile.services.entity;

import java.util.Date;

/**
 * @author zhangxu
 * @description 
 * @date 2017-6-22 上午08:52:36
 */
public class LossObjectPictureEntity {

	private String lossObjectId;
	private String title;
	private byte[] pictureContent;
	private String picturePath;
	private Date createTime;
	public String getLossObjectId() {
		return lossObjectId;
	}
	public void setLossObjectId(String lossObjectId) {
		this.lossObjectId = lossObjectId;
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
