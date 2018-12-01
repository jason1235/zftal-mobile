package com.zfsoft.globalweb.common.entities;

import java.io.Serializable;

/**
 * 
 * @author
 * 2014-2-20
 */
public class Common implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;
	private String resourceId;
	private String resourceName;
	private String roseId;
	private int sort;
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the resourceId
	 */
	public String getResourceId() {
		return resourceId;
	}
	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}
	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	/**
	 * @return the sort
	 */
	public int getSort() {
		return sort;
	}
	/**
	 * @param sort the sort to set
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}
	/**
	 * @return the roseId
	 */
	public String getRoseId() {
		return roseId;
	}
	/**
	 * @param roseId the roseId to set
	 */
	public void setRoseId(String roseId) {
		this.roseId = roseId;
	}
}
