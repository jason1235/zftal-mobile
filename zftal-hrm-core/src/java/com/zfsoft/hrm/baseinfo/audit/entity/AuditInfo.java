package com.zfsoft.hrm.baseinfo.audit.entity;

import java.util.Date;

/** 
 * 审核信息
 * @author jinjj
 * @date 2012-9-28 上午10:56:38 
 *  
 */
public class AuditInfo {

	private String guid;
	
	private String classId;
	
	private String roleId;
	
	private String operator;
	
	private Date createTime;
	
	private int status;
	
	private String info;

	/**
	 * 日志ID
	 * @return
	 */
	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 信息类ID
	 * @return
	 */
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	/**
	 * 角色ID
	 * @return
	 */
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/**
	 * 审核人
	 * @return
	 */
	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	/**
	 * 审核时间
	 * @return
	 */
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 审核状态
	 * @return
	 */
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 审核意见
	 * @return
	 */
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	
}
