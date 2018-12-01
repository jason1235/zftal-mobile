package com.zfsoft.hrm.exportword.entity;

import java.util.Date;

/**
 * 
 * @author ChenMinming
 * @date 2014-8-25
 * @version V1.0.0
 */
public class ExportConfig {

	private String id;
	private String name;
	private String type;
	private String open;
	private Date lastModify;
	private String creator;
	private String origin;
	/**
	 * 返回
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置
	 * @param id 
	 */
	public void setId(String id) {
		this.id = id;
	}
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
	public Date getLastModify() {
		return lastModify;
	}
	/**
	 * 设置
	 * @param lastModify 
	 */
	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
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
	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	
}
