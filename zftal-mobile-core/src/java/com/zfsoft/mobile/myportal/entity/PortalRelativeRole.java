package com.zfsoft.mobile.myportal.entity;

import com.zfsoft.common.query.ModelBase;
import com.zfsoft.dao.query.BaseQuery;

public class PortalRelativeRole extends BaseQuery {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6868604566817800940L;

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

	public String getClassFwmc() {
		return classFwmc;
	}

	public void setClassFwmc(String classFwmc) {
		this.classFwmc = classFwmc;
	}
	
	
}
