package com.zfsoft.mobile.reportFix.entity;

import com.zfsoft.dao.query.BaseQuery;

//报修类型model
public class FixTypeQuery extends BaseQuery{

	private String id;
	private String name;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
