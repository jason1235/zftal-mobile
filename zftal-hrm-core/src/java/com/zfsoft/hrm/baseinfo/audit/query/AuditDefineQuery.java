package com.zfsoft.hrm.baseinfo.audit.query;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * @author jinjj
 * @date 2012-9-28 上午11:57:25 
 *  
 */
public class AuditDefineQuery extends BaseQuery {

	private static final long serialVersionUID = 6196136293584828468L;
	private String classId;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
}
