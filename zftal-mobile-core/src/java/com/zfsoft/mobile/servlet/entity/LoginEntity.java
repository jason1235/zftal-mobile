package com.zfsoft.mobile.servlet.entity;

import com.zfsoft.util.base.StringUtil;

public class LoginEntity {

	private String name;//姓名
	private String role;//角色
	private String department;//部门
	private String userId;//用户名
	private String appname;//app名
	private String schoolName;//学校名称
	//public String zydm;
	//public String zymc;
	private String className;//班级
	private String gradeName;//年纪
	private String nowSchoolYearTerm;//当前学年学期
	private String app_token;//秘钥
	private String headPicturePath;//我的头像路径
	
	
	public String getName() {
		return StringUtil.isEmpty(name) ? "" : name;
	}


	public void setName(String name) {
		
		this.name = StringUtil.isEmpty(name) ? "" :name;
	}


	public String getRole() {
		return StringUtil.isEmpty(role) ? "" : role;
	}


	public void setRole(String role) {
		this.role = StringUtil.isEmpty(role) ? "" : role;
	}


	public String getDepartment() {
		return StringUtil.isEmpty(department) ? "" : department;
	}


	public void setDepartment(String department) {
		this.department = StringUtil.isEmpty(department) ? "" :department;
	}


	public String getUserId() {
		return StringUtil.isEmpty(userId) ? "" : userId;
	}


	public void setUserId(String userId) {
		this.userId = StringUtil.isEmpty(userId) ? "" : userId;
	}


	public String getAppname() {
		return StringUtil.isEmpty(appname) ? "" : appname;
	}


	public void setAppname(String appname) {
		this.appname = StringUtil.isEmpty(appname) ? "" : appname;
	}


	public String getSchoolName() {
		return StringUtil.isEmpty(schoolName) ? "" : schoolName;
	}


	public void setSchoolName(String schoolName) {
		this.schoolName = StringUtil.isEmpty(schoolName) ? "" : schoolName;
	}


	public String getClassName() {
		return StringUtil.isEmpty(className) ? "" : className;
	}


	public void setClassName(String className) {
		this.className = StringUtil.isEmpty(className) ? "" : className;
	}


	public String getGradeName() {
		return StringUtil.isEmpty(gradeName) ? "" : gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = StringUtil.isEmpty(gradeName) ? "" : gradeName;
	}

	public String getNowSchoolYearTerm() {
		return StringUtil.isEmpty(nowSchoolYearTerm) ? "" : nowSchoolYearTerm;
	}


	public void setNowSchoolYearTerm(String nowSchoolYearTerm) {
		this.nowSchoolYearTerm = StringUtil.isEmpty(nowSchoolYearTerm) ? "" : nowSchoolYearTerm;
	}


	public String getApp_token() {
		return StringUtil.isEmpty(app_token) ? "" : app_token;
	}


	public void setApp_token(String app_token) {
		this.app_token = StringUtil.isEmpty(app_token) ? "" : app_token;
	}


	@Override
	public String toString() {
		return "name"+name+",role:"+role+",department:"+department+",userId:"+userId+",appname:"+appname+",schoolName:"+schoolName+
		",className:"+className+",gradeName:"+gradeName+",nowSchoolYearTerm:"+nowSchoolYearTerm+",app_token:"+app_token;
	}


	public void setHeadPicturePath(String headPicturePath) {
		this.headPicturePath = headPicturePath;
	}


	public String getHeadPicturePath() {
		return headPicturePath;
	}
	
	
	
}
