package com.zfsoft.hrm.staffturn.leaveschool.query;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * 离校步骤查询实体
 * @author jinjj
 * @date 2012-8-1 上午01:28:03 
 *  
 */
public class LeaveStepQuery extends BaseQuery {

	private static final long serialVersionUID = 2302109743231828759L;

	private String deptId;
	
	private String handler;
	
	private String guid;
	
	/**
	 * 部门ID
	 * @return
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * 部门ID
	 * @param deptId
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getHandler() {
		return handler;
	}

	public void setHandler(String handler) {
		this.handler = handler;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
}
