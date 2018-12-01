package com.base.xtgl.entity;

import com.zfsoft.dao.query.BaseQuery;


public class MenuPermissionEntity extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4516536302936084593L;
	private String userId;//用户id
	private String gnmkdm;//功能模块代码
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getGnmkdm() {
		return gnmkdm;
	}
	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}
	
	
}
