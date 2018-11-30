package com.zfsoft.mobile.market.entity;

import com.zfsoft.dao.query.BaseQuery;

/*
 * 跳蚤市场分类
 */
public class MarketTypeQuery extends BaseQuery  {
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
