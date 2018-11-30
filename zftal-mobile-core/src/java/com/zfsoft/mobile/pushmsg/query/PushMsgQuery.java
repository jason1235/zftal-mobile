package com.zfsoft.mobile.pushmsg.query;

import com.zfsoft.dao.query.BaseQuery;
public class PushMsgQuery extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4829062208743418826L;

	private final static int pageSize = 20;
	
	private String tsdx;
	private String tsry;
	private String tsfs;
	private String appType;
	
	public PushMsgQuery(){
		super.setPerPageSize(pageSize);
	}

	/**
	 * 返回
	 */
	public String getTsdx() {
		return tsdx;
	}

	/**
	 * 设置
	 * @param tsdx 
	 */
	public void setTsdx(String tsdx) {
		this.tsdx = tsdx;
	}

	/**
	 * 返回
	 */
	public String getTsry() {
		return tsry;
	}

	/**
	 * 设置
	 * @param tsry 
	 */
	public void setTsry(String tsry) {
		this.tsry = tsry;
	}

	/**
	 * 返回
	 */
	public String getTsfs() {
		return tsfs;
	}

	/**
	 * 设置
	 * @param tsfs 
	 */
	public void setTsfs(String tsfs) {
		this.tsfs = tsfs;
	}

	/**
	 * 返回
	 */
	public String getAppType() {
		return appType;
	}

	/**
	 * 设置
	 * @param appType 
	 */
	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	
}
