package com.zfsoft.hrm.baseinfo.infoclass.query;

import java.util.ArrayList;
import java.util.List;

/**
 * 信息类查询条件实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-21
 * @version V1.0.0
 */
public class InfoClassQuery {

	private String classId;			//信息类全局ID
	
	private String catalogId;		//目录ID
	
	private String catalogType;		//目录类型
	
	private String name;			//名称（中文）
	
	private String identityName;	//标识名（英文，用于建表）
	
	private List<String> types = new ArrayList<String>();	//类型 
	
	/**
	 * 返回目录ID
	 */
	public String getCatalogId() {
		return catalogId;
	}

	/**
	 * 设置目录ID
	 * @param catalogId 目录ID
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	/**
	 * 返回名称（中文）
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称（中文）
	 * @param name 名称（中文）
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回标识名（英文，用于建表）
	 */
	public String getIdentityName() {
		return identityName;
	}

	/**
	 * 设置标识名（英文，用于建表）
	 * @param identityName 标识名（英文，用于建表）
	 */
	public void setIdentityName(String identityName) {
		this.identityName = identityName;
	}

	/**
	 * 返回类型类别
	 */
	public List<String> getTypes() {
		return types;
	}

	/**
	 * 设置类型列表
	 * @param types 类型列表 
	 */
	public void setTypes(List<String> types) {
		this.types = types;
	}

	/**
	 * 返回信息类全局ID
	 */
	public String getClassId() {
		return classId;
	}

	/**
	 * 设置信息类全局ID
	 * @param classId 信息类全局ID
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}

	/**
	 * 返回目录类型
	 */
	public String getCatalogType() {
		return catalogType;
	}

	/**
	 * 设置目录类型
	 * @param catalogType 目录类型
	 */
	public void setCatalogType(String catalogType) {
		this.catalogType = catalogType;
	}
}
