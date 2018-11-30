package com.zfsoft.hrm.baseinfo.table.entities;

import java.io.Serializable;

/**
 * 表格字段实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-6
 * @version V1.0.0
 * 
 * 实体属性修改
 * @author jjj
 * @date 2012-06-06
 */
public class Column implements Serializable {

	private static final long serialVersionUID = -8937512795791990022L;

	private String tableName;			//表名
	
	private String columnName;			//名字
	
	private String newName;				//新的名字
	
	private String type;				//类型
	
	private boolean nullable = true;	//是否可为空
	
	private String defaultV;			//默认值
	
	private String comment;				//注释

	/**
	 * （空）构造函数
	 */
	public Column() {
		// do nothing
	}
	
	/**
	 * 构造函数
	 * @param columnName 名字
	 * @param comment 注释
	 * @param type 类型
	 * @param tablename 表名
	 */
	public Column(String columnName, String comment, String type, String tablename) {
		this.columnName = columnName;
		this.comment = comment;
		this.type = type;
		this.tableName = tablename;
	}

	/**
	 * 返回名字
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * 设置名字
	 * @param name 名字
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
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
	 * 返回是否可为空
	 */
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * 设置是否可为空
	 * @param nullable 是否可为空
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	/**
	 * 返回默认值
	 */
	public String getDefaultV() {
		return defaultV;
	}

	/**
	 * 设置默认值
	 * @param defaultV 默认值
	 */
	public void setDefaultV(String defaultV) {
		this.defaultV = defaultV;
	}

	/**
	 * 返回注释
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 设置注释
	 * @param comment 注释
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 返回表名
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 设置表名
	 * @param tableName 表名
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 返回新的名字
	 */
	public String getNewName() {
		return newName;
	}

	/**
	 * 设置新的名字
	 * @param newName 新的名字
	 */
	public void setNewName(String newName) {
		this.newName = newName;
	}
	
}
