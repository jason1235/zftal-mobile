package com.zfsoft.hrm.expertvote.vote.entity;

import java.util.Date;

/**
 * 申报信息实例 
 * @author ChenMinming
 * @date 2014-3-27
 * @version V1.0.0
 */
public class DeclareInstance {
	//id
	private String id;
	//工号
	private String gh;
	//姓名
	private String name;
	//部门
	private String department;
	//业务信息类id
	private String businessClassId;
	//对应的申报表单类型id
	private String configId;
	//对应的申报表单实例ID
	private String instanceId;
	//对应的审核表单类型id
	private String auditConfigId;
	//对应的审核表单实例ID
	private String auditInstanceId;
	
	private Date declareTime;
	
	private Date auditTime;
	
	//评审专家数
	private int expertNum=0;
	//同意人数
	private int allowNum=0;
	/**
	 * 返回
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}
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
	public String getConfigId() {
		return configId;
	}
	/**
	 * 设置
	 * @param configId 
	 */
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	/**
	 * 返回
	 */
	public String getInstanceId() {
		return instanceId;
	}
	/**
	 * 设置
	 * @param instanceId 
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	/**
	 * 返回
	 */
	public String getAuditConfigId() {
		return auditConfigId;
	}
	/**
	 * 设置
	 * @param auditConfigId 
	 */
	public void setAuditConfigId(String auditConfigId) {
		this.auditConfigId = auditConfigId;
	}
	/**
	 * 返回
	 */
	public String getAuditInstanceId() {
		return auditInstanceId;
	}
	/**
	 * 设置
	 * @param auditInstanceId 
	 */
	public void setAuditInstanceId(String auditInstanceId) {
		this.auditInstanceId = auditInstanceId;
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
	/**
	 * 返回
	 */
	public Date getDeclareTime() {
		return declareTime;
	}
	/**
	 * 设置
	 * @param declareTime 
	 */
	public void setDeclareTime(Date declareTime) {
		this.declareTime = declareTime;
	}
	/**
	 * 返回
	 */
	public Date getAuditTime() {
		return auditTime;
	}
	/**
	 * 设置
	 * @param auditTime 
	 */
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	/**
	 * 返回
	 */
	public int getExpertNum() {
		return expertNum;
	}
	/**
	 * 设置
	 * @param expertNum 
	 */
	public void setExpertNum(int expertNum) {
		this.expertNum = expertNum;
	}
	/**
	 * 返回
	 */
	public int getAllowNum() {
		return allowNum;
	}
	/**
	 * 设置
	 * @param allowNum 
	 */
	public void setAllowNum(int allowNum) {
		this.allowNum = allowNum;
	}
	
	

}
