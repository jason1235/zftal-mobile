package com.zfsoft.mobile.webservices.entity;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

public class CardBusinessEntity extends BaseQuery{

/**
	 * 
	 */
	private static final long serialVersionUID = -1238018906750868154L;

public String classLsh;
	
	private String classOutIn;
	
	private Date classJysj;
	
	private float classJye;
	
	private String classRyh;
	
	private String classSbbh;
	
	private String classDate;
	
	private String classSh;
	
	private String classYe;
	
	private String classZh;
	
	public String getClassYe() {
		return classYe;
	}

	public void setClassYe(String classYe) {
		this.classYe = classYe;
	}

	public String getClassSh() {
		return classSh;
	}

	public void setClassSh(String classSh) {
		this.classSh = classSh;
	}


	public String getClassDate() {
		return classDate;
	}

	public void setClassDate(String classDate) {
		this.classDate = classDate;
	}

	public String getClassSbbh() {
		return classSbbh;
	}

	public void setClassSbbh(String classSbbh) {
		this.classSbbh = classSbbh;
	}

	public String getClassRyh() {
		return classRyh;
	}

	public void setClassRyh(String classRyh) {
		this.classRyh = classRyh;
	}

	public String getClassLsh() {
		return classLsh;
	}

	public void setClassLsh(String classLsh) {
		this.classLsh = classLsh;
	}

	public String getClassOutIn() {
		return classOutIn;
	}

	public void setClassOutIn(String classOutIn) {
		this.classOutIn = classOutIn;
	}

	public Date getClassJysj() {
		return classJysj;
	}

	public void setClassJysj(Date classJysj) {
		this.classJysj = classJysj;
	}

	public float getClassJye() {
		return classJye;
	}

	public void setClassJye(float classJye) {
		if(classJye < 0){
			classOutIn = "out";
			this.classJye = - classJye;
		}
		else{
			classOutIn = "in";
			this.classJye = classJye;
		}
	}

	public void setClassZh(String classZh) {
		this.classZh = classZh;
	}

	public String getClassZh() {
		return classZh;
	}


}
