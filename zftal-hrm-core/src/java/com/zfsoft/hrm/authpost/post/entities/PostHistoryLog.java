package com.zfsoft.hrm.authpost.post.entities;

import java.util.Date;

/** 
 * 历史岗位日志
 * @author jinjj
 * @date 2012-7-25 下午01:55:43 
 *  
 */
public class PostHistoryLog {

	private String guid;
	
	private String operator;
	
	private Date snapTime;
	
	private Date operateTime;

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getSnapTime() {
		return snapTime;
	}

	public void setSnapTime(Date snapTime) {
		this.snapTime = snapTime;
	}

	public Date getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

}
