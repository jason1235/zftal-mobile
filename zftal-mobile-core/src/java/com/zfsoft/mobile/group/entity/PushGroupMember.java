package com.zfsoft.mobile.group.entity;

import com.zfsoft.common.query.ModelBase;
import com.zfsoft.dao.query.BaseQuery;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-22
 * @version V1.0.0
 */
public class PushGroupMember  extends BaseQuery {
	private String id;
	private String userId;
	private String userName;
	private String groupId;
	private String groupName;
	private Boolean directly;
	private String academy;
	private String classgrade;
	private String grade;
	private String sex;
	private String jsdm;
	
	
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
	public String getUserId() {
		return userId;
	}
	/**
	 * 设置
	 * @param userId 
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * 返回
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * 设置
	 * @param userName 
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * 返回
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * 设置
	 * @param groupId 
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * 返回
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * 设置
	 * @param groupName 
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * 返回
	 */
	public Boolean getDirectly() {
		return directly;
	}
	/**
	 * 设置
	 * @param directly 
	 */
	public void setDirectly(Boolean directly) {
		this.directly = directly;
	}
	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}
	public String getJsdm() {
		return jsdm;
	}
	
	
}
