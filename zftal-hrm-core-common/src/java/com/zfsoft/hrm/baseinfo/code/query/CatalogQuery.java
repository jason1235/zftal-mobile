/**   
 * @Title: CatalogQuery.java 
 * @Package com.zfsoft.hrm.baseinfo.code.query 
 * @author jinjj   
 * @date 2012-5-22 下午01:24:34 
 * @version V1.0   
 */
package com.zfsoft.hrm.baseinfo.code.query;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * @ClassName: CatalogQuery 
 * @Description: 编目查询条件
 * @author jinjj
 * @date 2012-5-22 下午01:24:34 
 *  
 */
public class CatalogQuery extends BaseQuery{

	private static final long serialVersionUID = 621230032312614154L;
	
	private final static int pageSize = 20;

	private String guid;
	
	private String name;
	
	private Integer type;
	
	private Integer source;
	
	/**
	 * 默认构造方法，设置了分页大小
	 */
	public CatalogQuery(){
		setPerPageSize(pageSize);
	}

	/**
	 * Get 编目ID
	 * @return 
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Set 编目ID
	 * @param guid 
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}

	/**
	 * Get 编目名称
	 * @return 
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set 编目名称
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get编目类型
	 * @return 
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * Set编目类型
	 * @param type 
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * Get数据来源
	 * @return 
	 */
	public Integer getSource() {
		return source;
	}

	/**
	 * Set数据来源
	 * @param source 
	 */
	public void setSource(Integer source) {
		this.source = source;
	}
	
	
}
