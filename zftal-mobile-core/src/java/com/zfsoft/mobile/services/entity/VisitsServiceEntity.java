package com.zfsoft.mobile.services.entity;

import com.zfsoft.dao.query.BaseQuery;

public class VisitsServiceEntity extends BaseQuery{

	private String fwbm;//服务编码
	private String fwmc;//服务名称
	private String visits;//累计访问量
	private float avenge;//日均访问量
	private String startDate;//开始日期
	private String endDate;//结束日期
	private String xtmc;//系统名称
	private String schoolCode;//学校编码
	public String getFwbm() {
		return fwbm;
	}
	public void setFwbm(String fwbm) {
		this.fwbm = fwbm;
	}
	public String getFwmc() {
		return fwmc;
	}
	public void setFwmc(String fwmc) {
		this.fwmc = fwmc;
	}
	public String getVisits() {
		return visits;
	}
	public void setVisits(String visits) {
		this.visits = visits;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setAvenge(float avenge) {
		this.avenge = avenge;
	}
	public float getAvenge() {
		return avenge;
	}
	public void setXtmc(String xtmc) {
		this.xtmc = xtmc;
	}
	public String getXtmc() {
		return xtmc;
	}
	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}
	public String getSchoolCode() {
		return schoolCode;
	}
	
	
	
	
}
