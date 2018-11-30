package com.zfsoft.mobile.classCommunity.entity;

/**
 * 签到记录实体
 * @author H110MF
 *
 */

public class ClassSignInRecordEntity {
    private String id;            //唯一主键
    private String studentNumber; //学号
    private String name;          //姓名
    private String signInTime;    //签到时间
    private String signInAddr;    //签到地点
    private String loglat;        //经纬度
    private String distance;      //与课程地点距离
    private String classSignInfoId;//签到课程id
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudentNumber() {
		return studentNumber;
	}
	public void setStudentNumber(String studentNumber) {
		this.studentNumber = studentNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSignInTime() {
		return signInTime;
	}
	public void setSignInTime(String signInTime) {
		this.signInTime = signInTime;
	}
	public String getSignInAddr() {
		return signInAddr;
	}
	public void setSignInAddr(String signInAddr) {
		this.signInAddr = signInAddr;
	}
	public String getLoglat() {
		return loglat;
	}
	public void setLoglat(String loglat) {
		this.loglat = loglat;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getClassSignInfoId() {
		return classSignInfoId;
	}
	public void setClassSignInfoId(String classSignInfoId) {
		this.classSignInfoId = classSignInfoId;
	}
}
