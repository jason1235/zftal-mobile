package com.zfsoft.hrm.ddselect.entities;

import java.io.Serializable;

/**
 * 
 * 类描述：表中的主键或外键
 *
 * @version: 1.0
 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
 * @since: 2013-8-14 下午01:03:58
 */
public class Keys implements Serializable {
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = -97223544924345166L;

	private String keyName;
	
	private String keyType;
	
	private String columnName;
	
	private String comments;
	
	private String rconstraintName;

	/**
	 * @return keyName : return the property keyName.
	 */
	
	public String getKeyName() {
		return keyName;
	}

	/**
	 * @param keyName : set the property keyName.
	 */
	
	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	/**
	 * @return keyType : return the property keyType.
	 */
	
	public String getKeyType() {
		return keyType;
	}

	/**
	 * @param keyType : set the property keyType.
	 */
	
	public void setKeyType(String keyType) {
		this.keyType = keyType;
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

	/**
	 * @return comments : return the property comments.
	 */
	
	public String getComments() {
		return comments;
	}

	/**
	 * @param comments : set the property comments.
	 */
	
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * @return rconstraintName : return the property rconstraintName.
	 */
	
	public String getRconstraintName() {
		return rconstraintName;
	}

	/**
	 * @param rconstraintName : set the property rconstraintName.
	 */
	
	public void setRconstraintName(String rconstraintName) {
		this.rconstraintName = rconstraintName;
	}

}
