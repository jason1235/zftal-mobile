/**   
 * @Title: ItemQuery.java 
 * @Package com.zfsoft.hrm.baseinfo.code.query 
 * @author jinjj   
 * @date 2012-5-22 下午01:24:34 
 * @version V1.0   
 */
package com.zfsoft.hrm.baseinfo.code.query;

/** 
 * @ClassName: ItemQuery 
 * @Description: 编目查询条件
 * @author jinjj
 * @date 2012-5-22 下午01:24:34 
 *  
 */
public class ItemQuery {

	private String guid;
	
	private String name;
	
	private String catalogId;
	
	private String parentId;

	/**
	 * Get 条目ID
	 * @return 
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Set 条目ID
	 * @param guid 
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * Get 条目名称
	 * @return 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set 条目名称
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get所属编目编号
	 * @return 
	 */
	public String getCatalogId() {
		return catalogId;
	}

	/**
	 * Set所属编目编号
	 * @param catalogId 
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}

	/**
	 * Get
	 * @return 
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * Set
	 * @param parentId 
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	
}
