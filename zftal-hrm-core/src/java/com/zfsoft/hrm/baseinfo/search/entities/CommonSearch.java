package com.zfsoft.hrm.baseinfo.search.entities;

import java.util.List;

/**
 * 常用查询实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-13
 * @version V1.0.0
 */
public class CommonSearch {
	
	private String guid;					//全局ID			
	
	private String title;					//标题
	
	private List<Condition> conditions;		//条件系列
	
	private Object data;					//数据
	
	private String type;                    //类型(区分 教师和学生)

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

	/**
	 * 返回条件系列
	 */
	public List<Condition> getConditions() {
		return conditions;
	}

	/**
	 * 设置条件系列
	 * @param conditions 条件系列
	 */
	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	/**
	 * 返回数据
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 设置数据
	 * @param data 数据 
	 */
	public void setData(Object data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	

}
