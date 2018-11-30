package com.zfsoft.mobile.services.dao.query;

import com.zfsoft.dao.query.BaseQuery;

public class ServiceManagerQuery extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5198525665172821501L;

	private String classId;
	
	private String classFwbm;
	
	private String classFwmc;
	
	private String classSsywxt;
	
	private String classSscp;
	
	private String classFwlx;
	
	private String classFbzt;
	
	private String classFwly;
	
	private int classRdpx;
	
	private String classDeleted;
	
	private String iscommon;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassFwbm() {
		return classFwbm;
	}

	public void setClassFwbm(String classFwbm) {
		this.classFwbm = classFwbm;
	}

	public String getClassFwmc() {
		return classFwmc;
	}

	public void setClassFwmc(String classFwmc) {
		this.classFwmc = classFwmc;
	}

	public String getClassSsywxt() {
		return classSsywxt;
	}

	public void setClassSsywxt(String classSsywxt) {
		this.classSsywxt = classSsywxt;
	}

	public String getClassSscp() {
		return classSscp;
	}

	public void setClassSscp(String classSscp) {
		this.classSscp = classSscp;
	}

	public String getClassFwlx() {
		return classFwlx;
	}

	public void setClassFwlx(String classFwlx) {
		this.classFwlx = classFwlx;
	}

	public String getClassFbzt() {
		return classFbzt;
	}

	public void setClassFbzt(String classFbzt) {
		this.classFbzt = classFbzt;
	}

	public String getClassFwly() {
		return classFwly;
	}

	public void setClassFwly(String classFwly) {
		this.classFwly = classFwly;
	}

	public void setClassRdpx(int classRdpx) {
		this.classRdpx = classRdpx;
	}

	public int getClassRdpx() {
		return classRdpx;
	}

	public void setClassDeleted(String classDeleted) {
		this.classDeleted = classDeleted;
	}

	public String getClassDeleted() {
		return classDeleted;
	}

	public void setIscommon(String iscommon) {
		this.iscommon = iscommon;
	}

	public String getIscommon() {
		return iscommon;
	}

}
