package com.zfsoft.hrm.exportword.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-8-27
 * @version V1.0.0
 */
public class ExportConfigQuery extends BaseQuery{
	/**
	 * 
	 */
	private static final long serialVersionUID = -279947407870327558L;
	private String name;
	private String type;
	private String open;
	private String creator;
	/**
	 * 返回
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 返回
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
	/**
	 * 返回
	 */
	public String getOpen() {
		return open;
	}
	/**
	 * 设置
	 * @param open 
	 */
	public void setOpen(String open) {
		this.open = open;
	}
	/**
	 * 返回
	 */
	public String getCreator() {
		return creator;
	}
	/**
	 * 设置
	 * @param creator 
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	
}
