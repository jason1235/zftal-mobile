package com.zfsoft.mobile.group.query;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-21
 * @version V1.0.0
 */
public class PushGroupQuery extends BaseQuery{
	private static final long serialVersionUID = -8428693880009421865L;
	private String name;
	private String type;
	private String status;
	private String detail;
	private String pid;
	private Integer level;
	private String creator;
	private String academy;
	private String classgrade;
	private String grade;
	private String sex;
	
	public String getAcademy() {
		return academy;
	}
	public void setAcademy(String academy) {
		this.academy = academy;
	}
	public String getClassgrade() {
		return classgrade;
	}
	public void setClassgrade(String classgrade) {
		this.classgrade = classgrade;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
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
	public Integer getLevel() {
		return level;
	}
	/**
	 * 设置
	 * @param level 
	 */
	public void setLevel(Integer level) {
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
}
