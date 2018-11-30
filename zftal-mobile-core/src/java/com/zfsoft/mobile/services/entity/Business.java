package com.zfsoft.mobile.services.entity;

import java.util.Date;

public class Business {

	private String classId;
	
	private String classXtbm;
	
	private String classXtmc;
	
	private String classXtms;
	
	private String classXtdz;
	
	private Date classCjsj;
	
	private String classCjzid;
	
	private Date classGxsj;
	
	private String classGxzid;
	
	private String classSyzt;
	
	private String classDeleted;
	
	private String procode;
	
	private int    classFwsl;
	
	private String otherFlag;//第三方业务系统标识,不属于正方系统
	
	private int pxm;
	
	private String signalXtbm;
	
	

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassXtbm() {
		return classXtbm;
	}

	public void setClassXtbm(String classXtbm) {
		this.classXtbm = classXtbm;
	}

	public String getClassXtmc() {
		return classXtmc;
	}

	public void setClassXtmc(String classXtmc) {
		this.classXtmc = classXtmc;
	}

	public String getClassXtms() {
		return classXtms;
	}

	public void setClassXtms(String classXtms) {
		this.classXtms = classXtms;
	}

	public String getClassXtdz() {
		return classXtdz;
	}

	public void setClassXtdz(String classXtdz) {
		this.classXtdz = classXtdz;
	}

	public Date getClassCjsj() {
		return classCjsj;
	}

	public void setClassCjsj(Date classCjsj) {
		this.classCjsj = classCjsj;
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

	public String getClassSyzt() {
		return classSyzt;
	}

	public void setClassSyzt(String classSyzt) {
		this.classSyzt = classSyzt;
	}

	public String getClassDeleted() {
		return classDeleted;
	}

	public void setClassDeleted(String classDeleted) {
		this.classDeleted = classDeleted;
	}

	public void setClassFwsl(int classFwsl) {
		this.classFwsl = classFwsl;
	}

	public int getClassFwsl() {
		return classFwsl;
	}

	public void setClassGxsj(Date classGxsj) {
		this.classGxsj = classGxsj;
	}

	public Date getClassGxsj() {
		return classGxsj;
	}

	/**
	 * @return the procode
	 */
	public String getProcode() {
		return procode;
	}

	/**
	 * @param procode the procode to set
	 */
	public void setProcode(String procode) {
		this.procode = procode;
	}

	public void setOtherFlag(String otherFlag) {
		this.otherFlag = otherFlag;
	}

	public String getOtherFlag() {
		return otherFlag;
	}

	public void setPxm(int pxm) {
		this.pxm = pxm;
	}

	public int getPxm() {
		return pxm;
	}

	public void setSignalXtbm(String signalXtbm) {
		this.signalXtbm = signalXtbm;
	}

	public String getSignalXtbm() {
		return signalXtbm;
	}


}
