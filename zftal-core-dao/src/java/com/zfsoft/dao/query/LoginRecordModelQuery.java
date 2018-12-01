package com.zfsoft.dao.query;

public class LoginRecordModelQuery extends BaseQuery{
    private String username;      //用户名
    private String lastLoginTime; //最近登录时间
    private String phoneType;     //手机类型 1安卓 2苹果
    
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getPhoneType() {
		return phoneType;
	}
	public void setPhoneType(String phoneType) {
		this.phoneType = phoneType;
	}
}
