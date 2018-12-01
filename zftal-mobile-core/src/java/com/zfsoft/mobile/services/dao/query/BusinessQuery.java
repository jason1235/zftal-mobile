package com.zfsoft.mobile.services.dao.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

public class BusinessQuery extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6894684410267416003L;

	private String classId;
	
	private String classXtbm;
	
	private String classXtmc;
	
	private String classSyzt;
	
	private String classXtms;
	
	private String classXtdz;
	
	private Date classGxsj;
	
	private String classGxzid;
	
	private String procode;


	public Date getClassGxsj() {
		return classGxsj;
	}

	public void setClassGxsj(Date date) {
		this.classGxsj = date;
	}

	public String getClassGxzid() {
		return classGxzid;
	}

	public void setClassGxzid(String classGxzid) {
		this.classGxzid = classGxzid;
	}

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

	public String getClassSyzt() {
		return classSyzt;
	}

	public void setClassSyzt(String classSyzt) {
		this.classSyzt = classSyzt;
	}

	public void setClassXtms(String classXtms) {
		this.classXtms = classXtms;
	}

	public String getClassXtms() {
		return classXtms;
	}

	public void setClassXtdz(String classXtdz) {
		this.classXtdz = classXtdz;
	}

	public String getClassXtdz() {
		return classXtdz;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}

	public String getProcode() {
		return procode;
	}
}
