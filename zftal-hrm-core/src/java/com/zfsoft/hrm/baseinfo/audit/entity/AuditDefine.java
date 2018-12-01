package com.zfsoft.hrm.baseinfo.audit.entity;

import com.zfsoft.hrm.baseinfo.role.entity.Role;
import com.zfsoft.hrm.baseinfo.role.util.RoleUtil;

/** 
 * 审核定义
 * @author jinjj
 * @date 2012-9-28 上午09:50:42 
 *  
 */
public class AuditDefine {

	private String guid;
	
	private String classId;
	
	private String roleId;
	
	private String scope;
	
	private int order;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	public String getRoleName(){
		Role role = RoleUtil.getRole(roleId);
		String val = "无法解析";
		if(role!= null){
			val = role.getName();
		}
		return val;
	}
}
