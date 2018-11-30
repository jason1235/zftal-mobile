package com.zfsoft.dataprivilege.dto;


/**
 * 抽象过滤类
 * @author Patrick Shen
 */
public abstract class AbstractFilter {
	
	protected String filterId;
	protected String userId;
	protected String roleId;
	
	/**
	 * 返回
	 * @return 
	 */
	public String getFilterId() {
		return filterId;
	}
	/**
	 * 设置
	 * @param filterId 
	 */
	public void setFilterId(String filterId) {
		this.filterId = filterId;
	}
	/**
	 * 返回
	 * @return 
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
	 * @return 
	 */
	public String getRoleId() {
		return roleId;
	}
	/**
	 * 设置
	 * @param roleId 
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
