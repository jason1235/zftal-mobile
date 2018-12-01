package com.zfsoft.hrm.notice.query;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * @author jinjj
 * @date 2012-9-26 上午10:24:14 
 *  
 */
public class NoticeQuery extends BaseQuery {

	private static final long serialVersionUID = 9125650554614218028L;

	private Integer status;
	
	private Integer top;
	
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
