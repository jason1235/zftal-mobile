package com.zfsoft.hrm.baseinfo.search.query;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 常用查询查询条件
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-15
 * @version V1.0.0
 */
public class CommonSearchQuery extends BaseQuery {

	private static final long serialVersionUID = -2052953443504677401L;

	private String guid;	//全局ID
	
	private String title;	//标题
	
	private String type;	//类型

	/**
	 * 返回全局ID
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * 设置全局ID
	 * @param guid 全局ID
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 返回标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题
	 * @param title 标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
