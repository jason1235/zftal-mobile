package com.zfsoft.mobile.services.entity;

import com.zfsoft.dao.query.BaseQuery;

public class FwdyjsModel extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7383980037897839814L;

	private String classFwbm;
	
	private String classJsid;
	
	private String classFwmc;

	public String getClassFwbm() {
		return classFwbm;
	}

	public void setClassFwbm(String classFwbm) {
		this.classFwbm = classFwbm;
	}

	public String getClassJsid() {
		return classJsid;
	}

	public void setClassJsid(String classJsid) {
		this.classJsid = classJsid;
	}

	public void setClassFwmc(String classFwmc) {
		this.classFwmc = classFwmc;
	}

	public String getClassFwmc() {
		return classFwmc;
	}
	

}
