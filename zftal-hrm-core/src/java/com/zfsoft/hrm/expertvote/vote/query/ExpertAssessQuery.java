package com.zfsoft.hrm.expertvote.vote.query;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-24
 * @version V1.0.0
 */
public class ExpertAssessQuery extends BaseQuery{

	private static final long serialVersionUID = 201403241418946L;
	
	//专业学科
	private String subject;
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
	//年度
	private String year;
	//专家工号
	private String gh;

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
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
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
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
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

}
