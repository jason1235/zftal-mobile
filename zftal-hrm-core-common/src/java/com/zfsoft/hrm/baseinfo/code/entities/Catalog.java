package com.zfsoft.hrm.baseinfo.code.entities;

import java.io.Serializable;


/** 
 * @ClassName: CatalogModel 
 * @Description: 代码编目实体
 * @author jinjj
 * @date 2012-5-18 上午11:05:40 
 *  
 */
public class Catalog implements Serializable {

	private static final long serialVersionUID = -8131726627146555608L;

	private String guid;				//编目编号 bmid
	
	private String name;				//编目名称 bmmc
	
	private Integer includeParentNode;	//是否包括父节点 sfbkfjd
	
	private String delimiter;			//分级显示间隔符 fjxsjgf
	
	private String classCode;			//分级码 fjm
	
	private String chooseExpr;			//可选中表达式(分级码是否可选中的表达式) kxzbds
	
	private String remark;				//备注 bz
	
	private String source;				//代码数据来源 dmsjly
	
	private String type;				//代码类型 dmlx

	/**
	 * 返回编目编号
	 * @return 
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * 设置编目编号
	 * @param guid 编目编号
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * 返回编目名称
	 * @return 
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置编目名称
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回是否包括父节点
	 * @return 
	 */
	public Integer getIncludeParentNode() {
		return includeParentNode;
	}

	/**
	 * 设置是否包括父节点
	 * @param includeParentNode 
	 */
	public void setIncludeParentNode(Integer includeParentNode) {
		this.includeParentNode = includeParentNode;
	}

	/**
	 * 返回分级显示间隔符
	 * @return 
	 */
	public String getDelimiter() {
		return delimiter;
	}

	/**
	 * 设置分级显示间隔符
	 * @param delimiter 
	 */
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	/**
	 * 返回分级码
	 * @return 
	 */
	public String getClassCode() {
		return classCode;
	}

	/**
	 * 设置分级码
	 * @param classCode 
	 */
	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getChooseExpr() {
		return chooseExpr;
	}

	/**
	 * 设置
	 * @param chooseExpr 
	 */
	public void setChooseExpr(String chooseExpr) {
		this.chooseExpr = chooseExpr;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置
	 * @param remark 
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getSource() {
		return source;
	}

	/**
	 * 设置
	 * @param source 
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置
	 * @param type 
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	
}
