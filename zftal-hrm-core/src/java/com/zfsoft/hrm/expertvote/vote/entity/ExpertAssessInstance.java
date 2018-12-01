package com.zfsoft.hrm.expertvote.vote.entity;

import java.util.Date;

/**
 * 专家评审实例
 * @author ChenMinming
 * @date 2014-3-19
 * @version V1.0.0
 */
public class ExpertAssessInstance {
	//变更业务信息类id
	private String businessClassId;
	//主键id
	private String id;
	//评审对象
	private String workId;
	//评审专家
	private String expertId;
	//评审专家组
	private String groupId;
	//任务id
	private String taskId;
	//批次id
	private String batchId;
	//评审状态 0.未评 1.已评
	private String status;
	//评审结论 0.不通过 1.通过
	private String result;
	//评审描述（如评价等）
	private String description;
	//评审时间
	private Date assessTime;
	
	//职工号
	private String gh;
	//职工姓名
	private String xm;
	//职工部门
	private String dwm;
	 
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getExpertId() {
		return expertId;
	}
	public void setExpertId(String expertId) {
		this.expertId = expertId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Date getAssessTime() {
		return assessTime;
	}
	public void setAssessTime(Date assessTime) {
		this.assessTime = assessTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getDwm() {
		return dwm;
	}
	public void setDwm(String dwm) {
		this.dwm = dwm;
	}
	public String getBusinessClassId() {
		return businessClassId;
	}
	public void setBusinessClassId(String businessClassId) {
		this.businessClassId = businessClassId;
	}
	

}
