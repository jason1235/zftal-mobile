package com.zfsoft.mobile.services.dao.query;

import com.zfsoft.common.query.ModelBase;
import com.zfsoft.dao.query.BaseQuery;

public class FwdyjsQuery  extends ModelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8206536701945367693L;
	
	private String classJsid;
	
	private String classFwbm;
	
	private String classFwmc;

	public String getClassJsid() {
		return classJsid;
	}

	public void setClassJsid(String classJsid) {
		this.classJsid = classJsid;
	}

	public String getClassFwbm() {
		return classFwbm;
	}

	public void setClassFwbm(String classFwbm) {
		this.classFwbm = classFwbm;
	}

	public void setClassFwmc(String classFwmc) {
		this.classFwmc = classFwmc;
	}

	public String getClassFwmc() {
		return classFwmc;
	}
	


	
}
