package com.zfsoft.mobile.services.entity;

import com.zfsoft.dao.query.BaseQuery;

public class TwoExamDyJsEntity extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2240635144326272639L;

	private String papermainid;//问卷主表id
	
	private String qzid;//群组id
	
	private String qzmc;//群组名称

	public String getPapermainid() {
		return papermainid;
	}

	public void setPapermainid(String papermainid) {
		this.papermainid = papermainid;
	}

	public String getQzid() {
		return qzid;
	}

	public void setQzid(String qzid) {
		this.qzid = qzid;
	}

	public String getQzmc() {
		return qzmc;
	}

	public void setQzmc(String qzmc) {
		this.qzmc = qzmc;
	}
	
	
}
