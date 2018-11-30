package com.zfsoft.dao.entities;
/**
 * 用户_子系统_上次登录角色
 * 
 * @author gonghui
 * 2014-5-15
 */
public class UserSubsystemLastRole {
private String userId;
private String sysCode;
private String roleId;
public String getUserId() {
	return userId;
}
public void setUserId(String userId) {
	this.userId = userId;
}
public String getSysCode() {
	return sysCode;
}
public void setSysCode(String sysCode) {
	this.sysCode = sysCode;
}
public String getRoleId() {
	return roleId;
}
public void setRoleId(String roleId) {
	this.roleId = roleId;
}

}
