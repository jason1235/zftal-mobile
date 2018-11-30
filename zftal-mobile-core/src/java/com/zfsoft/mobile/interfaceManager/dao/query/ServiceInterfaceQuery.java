package com.zfsoft.mobile.interfaceManager.dao.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

public class ServiceInterfaceQuery extends BaseQuery{

/**
	 * 
	 */
	private static final long serialVersionUID = -44077566531951814L;

	private String classFwjkid;
	
	private String classSsfw;
	
	private String classSsjk;
	
	private String classJkffms;
	
	private String classJkffm;
	
	private String classFwmc;
	
	private String classJklm;
	
	public String getClassFwjkid() {
		return classFwjkid;
	}

	public void setClassFwjkid(String classFwjkid) {
		this.classFwjkid = classFwjkid;
	}

	public String getClassSsfw() {
		return classSsfw;
	}

	public void setClassSsfw(String classSsfw) {
		this.classSsfw = classSsfw;
	}

	public String getClassSsjk() {
		return classSsjk;
	}

	public void setClassSsjk(String classSsjk) {
		this.classSsjk = classSsjk;
	}

	public String getClassJkffms() {
		return classJkffms;
	}

	public void setClassJkffms(String classJkffms) {
		this.classJkffms = classJkffms;
	}

	public String getClassJkffm() {
		return classJkffm;
	}

	public void setClassJkffm(String classJkffm) {
		this.classJkffm = classJkffm;
	}

	public Date getClassCjsj() {
		return classCjsj;
	}

	public void setClassCjsj(Date classCjsj) {
		this.classCjsj = classCjsj;
	}

	public Date getClassGxsj() {
		return classGxsj;
	}

	public void setClassGxsj(Date classGxsj) {
		this.classGxsj = classGxsj;
	}

	public String getClassCjzid() {
		return classCjzid;
	}

	public void setClassCjzid(String classCjzid) {
		this.classCjzid = classCjzid;
	}

	public String getClassGxzid() {
		return classGxzid;
	}

	public void setClassGxzid(String classGxzid) {
		this.classGxzid = classGxzid;
	}

	public String getClassDeleted() {
		return classDeleted;
	}

	public void setClassDeleted(String classDeleted) {
		this.classDeleted = classDeleted;
	}

	public void setClassFwmc(String classFwmc) {
		this.classFwmc = classFwmc;
	}

	public String getClassFwmc() {
		return classFwmc;
	}

	public void setClassJklm(String classJklm) {
		this.classJklm = classJklm;
	}

	public String getClassJklm() {
		return classJklm;
	}

	private Date classCjsj;
	
	private Date classGxsj;
	
	private String classCjzid;
	
	private String classGxzid;
	
	private String classDeleted;
	
}
