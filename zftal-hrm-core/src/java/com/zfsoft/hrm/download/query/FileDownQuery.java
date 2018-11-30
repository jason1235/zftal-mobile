package com.zfsoft.hrm.download.query;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * @author jinjj
 * @date 2013-5-21 上午10:39:18 
 *  
 */
public class FileDownQuery extends BaseQuery {

	private static final long serialVersionUID = 7696808801236945353L;

	private Integer status;
	
	private Integer top;
	
	private String name;
	
	private String type;
	
	private String userName;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
	
	
}
