package com.zfsoft.hrm.config;

import java.io.Serializable;

import com.zfsoft.hrm.core.util.SQLExplainUtil;

/**
 * 查询条件实体
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-8
 * @version V1.0.0
 */
public class SearchCondition implements Serializable {

	private static final long serialVersionUID = -4607848209564633166L;
	
	private String name;			//名字
	
	private String title;			//标题，页面显示的标题
	
	private String table;			//实体表
	
	private String column;			//字段
	
	private String op;				//操作
	
	private String defaultValue;	//默认值
	
	private boolean show;			//显示？
	
	/**
	 * 返回条件表达式
	 * 如：column ## '%' || #{name} '%' and sex == #{sex} 
	 * @return 如：gh == #{gh}
	 */
	public String getCondition() {
		
		return column + " " + op + " " + getValue();
	}
	
	
	private String getValue() {
		String out = "#{params."+ name + "}";
		
		if( SQLExplainUtil.LIKE.equals( op ) ) {
			out = "'%' || " + out + " || '%'";
		}
		
		return out;
	}
	
	/////////////////////////
	/////////////////////////

	/**
	 * 返回名字
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名字
	 * @param name 名字
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回标题，页面显示的标题
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置标题，页面显示的标题
	 * @param title 标题，页面显示的标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 返回实体表
	 */
	public String getTable() {
		return table;
	}

	/**
	 * 设置实体表
	 * @param table 实体表
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * 返回字段
	 */
	public String getColumn() {
		return column;
	}

	/**
	 * 设置字段
	 * @param column 字段
	 */
	public void setColumn(String column) {
		this.column = column;
	}

	/**
	 * 返回操作
	 */
	public String getOp() {
		return op;
	}

	/**
	 * 设置操作
	 * @param op 操作
	 */
	public void setOp(String op) {
		this.op = op;
	}

	/**
	 * 返回默认值
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * 设置默认值
	 * @param defaultValue 默认值
	 */
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * 返回是否显示
	 */
	public boolean isShow() {
		return show;
	}

	/**
	 * 设置是否显示
	 * @param show 是否显示
	 */
	public void setShow(boolean show) {
		this.show = show;
	}
}
