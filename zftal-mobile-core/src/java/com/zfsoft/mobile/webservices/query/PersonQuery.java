package com.zfsoft.mobile.webservices.query;

import com.zfsoft.dao.query.BaseQuery;

public class PersonQuery extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3694701677251431830L;
	//用户名
	private String userName;
	//部门id
	private String dpId;
	//搜索名
	private String searchName;
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDpId() {
		return dpId;
	}
	public void setDpId(String dpId) {
		this.dpId = dpId;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getSearchName() {
		return searchName;
	}
	
	
}
