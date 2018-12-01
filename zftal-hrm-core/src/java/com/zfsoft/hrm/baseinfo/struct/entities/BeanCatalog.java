package com.zfsoft.hrm.baseinfo.struct.entities;

import java.io.Serializable;

/**
 * 信息类目录实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-3
 * @version V1.0.0
 */
public class BeanCatalog implements Serializable {

	private static final long serialVersionUID = 1724586556615071387L;
	
	/** 默认信息类 */
	public final static String DEFAULT = BeanCatalog.INFO;
	
	/** 信息类  */
	public final static String INFO = "info";
	
	/** 审核信息类 */
	public final static String AUDIT = "audit";
	
	private String guid;	//全局ID
	
	private String type;	//类型
	
	private String name;	//名称
	
	private int index;		//序号

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
	 * 返回类型
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置类型
	 * @param type 类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 返回名称
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回序号
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置序号
	 * @param index 序号
	 */
	public void setIndex(int index) {
		this.index = index;
	}

}
