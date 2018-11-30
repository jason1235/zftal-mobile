package com.zfsoft.mobile.group.entity;

import java.util.Date;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-21
 * @version V1.0.0
 */
public class PushGroup {
	private String id;
	private String name;
	private String type;
	private String status;
	private String detail;
	private String pid;
	private int level=1;
	private String creator;
	private Date createDate;
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
	public String getPid() {
		return pid;
	}
	/**
	 * 设置
	 * @param pid 
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	/**
	 * 返回
	 */
	public int getLevel() {
		return level;
	}
	/**
	 * 设置
	 * @param level 
	 */
	public void setLevel(int level) {
		this.level = level;
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
	 * 返回
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 设置
	 * @param createDate 
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 返回
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置
	 * @param status 
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 返回
	 */
	public String getDetail() {
		return detail;
	}
	/**
	 * 设置
	 * @param detail 
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
