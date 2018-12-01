package com.zfsoft.mobile.reportFix.entity;

import java.util.Date;

public class ReportFixPicInfoEntity {
	
	private String fixId; //相关报修主键
    private String name; //图片名称
    private String path; //图片路径
    private Date createTime; //创建时间
    private String type;
    
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
