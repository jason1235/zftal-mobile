package com.zfsoft.mobile.peEvaluation.query;

import com.zfsoft.dao.query.BaseQuery;

public class ClassInfoQuery extends BaseQuery{
	
	private String id;                //班级id 
	private String name;              //班级名称
	private String classTime;         //上课时间
	private String teacherName;       //教师姓名
	private String studentAmt;        //学生姓名
	private String instituteId;       //学院id
	private String instituteName;     //学院名称
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClassTime() {
		return classTime;
	}
	public void setClassTime(String classTime) {
		this.classTime = classTime;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getStudentAmt() {
		return studentAmt;
	}
	public void setStudentAmt(String studentAmt) {
		this.studentAmt = studentAmt;
	}
	public String getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
}
