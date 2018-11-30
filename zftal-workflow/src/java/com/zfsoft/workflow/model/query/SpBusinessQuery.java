package com.zfsoft.workflow.model.query;

import com.zfsoft.dao.query.BaseQuery;

public class SpBusinessQuery extends BaseQuery {
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = 4477987679458241201L;
	private String b_name; //业务流程名
	private String b_desc; //流程名称
	private String link;   //业务模块
	
	public String getB_name() {
		return b_name;
	}
	public void setB_name(String b_name) {
		this.b_name = b_name;
	}
	public String getB_desc() {
		return b_desc;
	}
	public void setB_desc(String b_desc) {
		this.b_desc = b_desc;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
}
