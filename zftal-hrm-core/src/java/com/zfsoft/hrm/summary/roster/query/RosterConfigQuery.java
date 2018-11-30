package com.zfsoft.hrm.summary.roster.query;

import com.zfsoft.dao.query.BaseQuery;

/** 
 * 花名册配置查询实体
 * @author jinjj
 * @date 2012-8-31 上午09:09:40 
 *  
 */
public class RosterConfigQuery extends BaseQuery {

	private static final long serialVersionUID = -1212976548811731329L;

	private String name;
	
	private String queryType;
	
	private String classid;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}
	
}
