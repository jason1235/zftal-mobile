package com.zfsoft.hrm.ddselect.entities;

import java.io.Serializable;

/**
 * 
 * 类描述：表中的索引
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-8-14 下午01:03:58
 */
public class Indexs implements Serializable {
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = -723991216043935432L;

	private String indexName;
	
	private String indexType;
	
	private String columnName;

	/**
	 * @return indexName : return the property indexName.
	 */
	
	public String getIndexName() {
		return indexName;
	}

	/**
	 * @param indexName : set the property indexName.
	 */
	
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}

	/**
	 * @return indexType : return the property indexType.
	 */
	
	public String getIndexType() {
		return indexType;
	}

	/**
	 * @param indexType : set the property indexType.
	 */
	
	public void setIndexType(String indexType) {
		this.indexType = indexType;
	}

	/**
	 * @return columnName : return the property columnName.
	 */
	
	public String getColumnName() {
		return columnName;
	}

	/**
	 * @param columnName : set the property columnName.
	 */
	
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

}
