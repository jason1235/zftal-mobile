package com.zfsoft.dao.entities;

import com.zfsoft.dao.query.BaseQuery;




public class User extends BaseQuery{
	private static final long serialVersionUID = 168148605371820079L;
    private String name;//姓名
    private String userId;//用户id
    private String sfqy;//是否启用
    private String password;//密码
    private String tellphone;//手机号码
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSfqy() {
		return sfqy;
	}
	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTellphone() {
		return tellphone;
	}
	public void setTellphone(String tellphone) {
		this.tellphone = tellphone;
	}
    
    
}
