package com.zfsoft.workflow.model.result;

import java.io.Serializable;

import com.zfsoft.workflow.model.BaseObject;

/**
 * 工作审核接口返回基础对象
 * 
 * @author yingjie.fan
 * @version 1.0
 */
public class BaseResult extends BaseObject implements Serializable {

	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = -3053003984199613539L;

	/* @property:工作ID */
	private String workId;
	/* @property:工作状态 */
	private String workStatus;
	/* @property:角色ID */
	private String roleId;
	/* @property:审核描述 */
	private String auditingDesc;

	/* Default constructor - creates a new instance with no values set. */
	public BaseResult() {
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getWorkId() {
		return workId;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public String getWorkStatus() {
		return workStatus;
	}
	
	/**
	 * @param roleId : set the property roleId.
	 */
	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	/**
	 * @return roleId : return the property roleId.
	 */
	
	public String getRoleId() {
		return roleId;
	}

	
	/**
	 * @param auditingDesc : set the property auditingDesc.
	 */
	
	public void setAuditingDesc(String auditingDesc) {
		this.auditingDesc = auditingDesc;
	}

	
	/**
	 * @return auditingDesc : return the property auditingDesc.
	 */
	
	public String getAuditingDesc() {
		return auditingDesc;
	}
}
