package com.zfsoft.dao.entities;

import com.zfsoft.dao.query.BaseQuery;

public class YhglModelNew extends BaseQuery{
	private String zgh;//职工号
	private String lxdh;//联系电话
	private String xm;//姓名
	private String sfqy;//是否启用
	private String jsdm;//角色代码
	private String jsmc;//角色名称
	private String deviceId;//设备编号
	private String classGrade;//账号所属班级id
	private String grade;//账号所属学院id
	
	public String getZgh() {
		return zgh;
	}
	public void setZgh(String zgh) {
		this.zgh = zgh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getSfqy() {
		return sfqy;
	}
	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}
	public String getJsdm() {
		return jsdm;
	}
	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}
	public String getJsmc() {
		return jsmc;
	}
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getLxdh() {
		return lxdh;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getClassGrade() {
		return classGrade;
	}
	public void setClassGrade(String classGrade) {
		this.classGrade = classGrade;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
