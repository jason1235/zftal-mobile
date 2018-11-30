package com.zfsoft.hrm.baseinfo.data.entity;

import com.zfsoft.common.query.QueryModel;

public class Template extends QueryModel {
	private String id;
	private String mbmc;
	private String bz;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMbmc() {
		return mbmc;
	}
	public void setMbmc(String mbmc) {
		this.mbmc = mbmc;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}

}
