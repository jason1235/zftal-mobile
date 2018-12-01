package com.zfsoft.hrm.baseinfo.table.entities;

import java.io.Serializable;
import java.util.List;

/**
 * 数据库表格实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-6
 * @version V1.0.0
 */
public class Table implements Serializable {

	private static final long serialVersionUID = -2126630865447957765L;

	private String tableName;		//表名
	
	private String comment;			//注释
	
	private List<Column> columns;	//字段列表
	
	/**
	 * （空）构造函数
	 */
	public Table() {
		this(null, "");
	}
	
	/**
	 * 构造函数
	 * @param tableName 表名
	 * @param comment 注释
	 */
	public Table( String tableName, String comment ) {
		this.tableName = tableName;
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
	 * @param name 表名
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 返回注释
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * 设置注释
	 * @param comments 注释
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * 返回字段列表
	 */
	public List<Column> getColumns() {
		return columns;
	}

	/**
	 * 设置字段列表
	 * @param columns 字段列表 
	 */
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
}
