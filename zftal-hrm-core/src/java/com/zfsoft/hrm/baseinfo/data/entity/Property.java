package com.zfsoft.hrm.baseinfo.data.entity;

import com.zfsoft.common.query.QueryModel;

public class Property extends QueryModel {
	private String id;
	private String mbid;	 //模版id
	private String xxlid;    //信息类id
	private String sxmc;     //属性名称
	private String zdmc;	 //字段名称
	private String zdlx;     //字段类型
	private Integer zdcd;    //字段长度
	public Integer getZdcd() {
		return zdcd;
	}
	public void setZdcd(Integer zdcd) {
		this.zdcd = zdcd;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMbid() {
		return mbid;
	}
	public void setMbid(String mbid) {
		this.mbid = mbid;
	}
	public String getXxlid() {
		return xxlid;
	}
	public void setXxlid(String xxlid) {
		this.xxlid = xxlid;
	}
	public String getSxmc() {
		return sxmc;
	}
	public void setSxmc(String sxmc) {
		this.sxmc = sxmc;
	}
	public String getZdmc() {
		return zdmc;
	}
	public void setZdmc(String zdmc) {
		this.zdmc = zdmc;
	}
	public String getZdlx() {
		return zdlx;
	}
	public void setZdlx(String zdlx) {
		this.zdlx = zdlx;
	}

}
