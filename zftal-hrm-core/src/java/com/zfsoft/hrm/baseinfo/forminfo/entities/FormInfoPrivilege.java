package com.zfsoft.hrm.baseinfo.forminfo.entities;

public class FormInfoPrivilege {

	private String roleId;//角色编号
	
	private String classId;//信息类编号
	
	private String opType;//操作类型

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}
	
	
}
