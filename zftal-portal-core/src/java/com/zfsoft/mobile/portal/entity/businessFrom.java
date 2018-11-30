package com.zfsoft.mobile.portal.entity;

import java.util.Date;

public class businessFrom {
	
	public String classLsh;
	
	public String classOutIn;
	
	public Date classJysj;
	
	public float classJye;
	
	public String classRyh;
	
	public String classSbbh;
	
	public String classDate;
	
	public String classSh;
	
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


}
