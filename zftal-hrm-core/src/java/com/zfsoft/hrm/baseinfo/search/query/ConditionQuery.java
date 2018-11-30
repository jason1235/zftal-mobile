package com.zfsoft.hrm.baseinfo.search.query;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-14
 * @version V1.0.0
 */
public class ConditionQuery extends BaseQuery {
	
	private static final long serialVersionUID = 893142801578972875L;

	private String parentId;	//条件系列ID
	
	private String guid;		//条件ID
	
	private String title;		//条件标题
	
	private String type;		//条件类型 ,区别教师和学生

	/**
	 * 返回条件系列ID
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 设置条件系列ID
	 * @param parentId 条件系列ID
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	/**
	 * 返回条件标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置条件标题
	 * @param title 条件标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 返回
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * 设置
	 * @param guid 
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
