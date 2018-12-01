package com.zfsoft.hrm.baseinfo.infoclass.query;

import java.io.Serializable;

/**
 * 信息类目录查询条件实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-18
 * @version V1.0.0
 */
public class CatalogQuery implements Serializable {

	private static final long serialVersionUID = -4993812764689257491L;

	private String name;	//目录名称
	
	private String type;	//目录分类    student 学生分类   teacher 老师分类 
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * （空）构造函数
	 */
	public CatalogQuery() {
		// do nothing 
	}

	/**
	 * 构造函数
	 * @param name 目录名称
	 * @param type 目录分类
	 */
	public CatalogQuery(String name,String type) {
		this.type = type;
		this.name = name;
	}
	
	/**
	 * 返回目录名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置目录名称
	 * @param name 目录名称 
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
