package com.zfsoft.hrm.dagl.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

public class MaterialsAddItem extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3244023515053451890L;

	private String classId;
	
	private String classGh;
	
	private String classXm;
	
	private String classClid;
	
	private Date   classJysj;
	
	private Date   classGhsj;
	
	private String classLh;
	
	private String classXh;
	
	private String classClmc;
	
	private String classDescribe;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getClassGh() {
		return classGh;
	}

	public void setClassGh(String classGh) {
		this.classGh = classGh;
	}

	public String getClassXm() {
		return classXm;
	}

	public void setClassXm(String classXm) {
		this.classXm = classXm;
	}

	public String getClassClid() {
		return classClid;
	}

	public void setClassClid(String classClid) {
		this.classClid = classClid;
	}

	public Date getClassJysj() {
		return classJysj;
	}

	public void setClassJysj(Date classJysj) {
		this.classJysj = classJysj;
	}

	public Date getClassGhsj() {
		return classGhsj;
	}

	public void setClassGhsj(Date classGhsj) {
		this.classGhsj = classGhsj;
	}

	public String getClassLh() {
		return classLh;
	}

	public void setClassLh(String classLh) {
		this.classLh = classLh;
	}

	public String getClassXh() {
		return classXh;
	}

	public void setClassXh(String classXh) {
		this.classXh = classXh;
	}

	public String getClassClmc() {
		return classClmc;
	}

	public void setClassClmc(String classClmc) {
		this.classClmc = classClmc;
	}

	public void setClassDescribe(String classDescribe) {
		this.classDescribe = classDescribe;
	}

	public String getClassDescribe() {
		return classDescribe;
	}
	
}
