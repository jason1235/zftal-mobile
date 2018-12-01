package com.zfsoft.hrm.baseinfo.infoclass.query;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * 信息属性展现配置QUERY
 * @author jinjj
 * @date 2012-11-13 上午09:34:31 
 *  
 */
public class InfoPropertyViewQuery extends BaseQuery {

	private static final long serialVersionUID = 8453252303716058015L;

	private String classId;
	
	private String username = "";

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
