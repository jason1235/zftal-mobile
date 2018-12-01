/**
 * 
 */
package com.zfsoft.mobile.services.entity;

/**
 * @author zhangxu
 * @description 
 * @date 2017-12-21 下午02:19:49
 */
public class ExpressPicEntity {

	private byte[] picContent;//图片内容
	private String picName;//图片名称
	private String picPath;//文件路径
	private int orderNumber;//排序码
	private String expressId;//主表id
	
	
	public byte[] getPicContent() {
		return picContent;
	}
	public void setPicContent(byte[] picContent) {
		this.picContent = picContent;
	}
	public String getPicName() {
		return picName;
	}
	public void setPicName(String picName) {
		this.picName = picName;
	}
	public String getPicPath() {
		return picPath;
	}
	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
	
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getExpressId() {
		return expressId;
	}
	public void setExpressId(String expressId) {
		this.expressId = expressId;
	}
	
	
}
