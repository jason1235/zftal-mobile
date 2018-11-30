package com.zfsoft.mobile.notice.query;

import com.zfsoft.dao.query.BaseQuery;

public class NotiKindQuery extends BaseQuery {
	private String id; // 种类ID
	private String name; // 种类名称
	private String status; // 使用状态
	private String createTime; // 创建时间
	private String creator; // 创建者ID
	private String updateTime; // 更新时间
	private String updater; // 更新者ID
	private String businessSystem; //所属业务系统
	private String grabOrNot;//是否抓取
	private String deleted;
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public void setBusinessSystem(String businessSystem) {
		this.businessSystem = businessSystem;
	}
	public String getBusinessSystem() {
		return businessSystem;
	}
	public void setGrabOrNot(String grabOrNot) {
		this.grabOrNot = grabOrNot;
	}
	public String getGrabOrNot() {
		return grabOrNot;
	}
}
