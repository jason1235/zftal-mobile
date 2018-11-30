package com.zfsoft.mobile.myportal.entity;

public class MyPortal {
	private String id = ""; //id
	private String name = ""; //mc  名称
	private String code = ""; //fwbm 服务编码
	private String ms = ""; // ms 描述
	private String type; // fwlx 服务类型
	private String addr = ""; // yydz 应用地址
	private String status; // zt 状态
	private String bz; //bz 备注
	private String deleted; //deleted 删除状态
	private String tbid; // tbid 图标ID
	private String tburl; // tburl 图标
	private String creator;
	private String createTime;
	private String updater;
	private String updateTime;
	private String pxm;
	private String tsgn; //是否特色功能
	private String isfx; //是否属于我的发现服务 0否，1是
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMs() {
		return ms;
	}
	public void setMs(String ms) {
		this.ms = ms;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getTbid() {
		return tbid;
	}
	public void setTbid(String tbid) {
		this.tbid = tbid;
	}
	public String getTburl() {
		return tburl;
	}
	public void setTburl(String tburl) {
		this.tburl = tburl;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getPxm() {
		return pxm;
	}
	public void setPxm(String pxm) {
		this.pxm = pxm;
	}
	public String getTsgn() {
		return tsgn;
	}
	public void setTsgn(String tsgn) {
		this.tsgn = tsgn;
	}
	public String getIsfx() {
		return isfx;
	}
	public void setIsfx(String isfx) {
		this.isfx = isfx;
	}
}
