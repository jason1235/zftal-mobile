package com.zfsoft.hrm.expertvote.vote.query;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-27
 * @version V1.0.0
 */
public class DeclareInstanceQuery extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 201403271421946L;
	//工号
	private String gh;
	//姓名
	private String name;
	//部门
	private String department;
	//晋升职称
	private String dutyType;
	//申报类型（晋升中、高级）
	private String type;
	//任务状态 （ BEFORE：未进行  WAIT：待执行 PASS：已执行）
	private String taskStatus;
	private String taskId;
	
	//业务信息类id
	private String businessClassId;
	/**
	 * 返回
	 */
	public String getGh() {
		return gh;
	}
	/**
	 * 设置
	 * @param gh 
	 */
	public void setGh(String gh) {
		this.gh = gh;
	}
	/**
	 * 返回
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 返回
	 */
	public String getDepartment() {
		return department;
	}
	/**
	 * 设置
	 * @param department 
	 */
	public void setDepartment(String department) {
		this.department = department;
	}
	/**
	 * 返回
	 */
	public String getDutyType() {
		return dutyType;
	}
	/**
	 * 设置
	 * @param dutyType 
	 */
	public void setDutyType(String dutyType) {
		this.dutyType = dutyType;
	}
	/**
	 * 返回
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置
	 * @param type 
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 返回
	 */
	public String getTaskStatus() {
		return taskStatus;
	}
	/**
	 * 设置
	 * @param taskStatus 
	 */
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	/**
	 * 返回
	 */
	public String getTaskId() {
		return taskId;
	}
	/**
	 * 设置
	 * @param taskId 
	 */
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	/**
	 * 返回
	 */
	public String getBusinessClassId() {
		return businessClassId;
	}
	/**
	 * 设置
	 * @param businessCLassId 
	 */
	public void setBusinessClassId(String businessClassId) {
		this.businessClassId = businessClassId;
	}

}
