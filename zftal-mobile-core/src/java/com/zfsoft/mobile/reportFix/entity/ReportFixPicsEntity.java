package com.zfsoft.mobile.reportFix.entity;

import java.util.Date;

public class ReportFixPicsEntity {
    private String fixId; //相关报修主键
    private String name; //图片名称
    private byte[] content; //图片文件 
    private String path; //图片路径
    private Date createTime; //创建时间
    private String type;
    private String picpath;
    private String evapath;
    
	public String getFixId() {
		return fixId;
	}
	public void setFixId(String fixId) {
		this.fixId = fixId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPicpath() {
		return picpath;
	}
	public void setPicpath(String picpath) {
		this.picpath = picpath;
	}
	public String getEvapath() {
		return evapath;
	}
	public void setEvapath(String evapath) {
		this.evapath = evapath;
	}
	
}
