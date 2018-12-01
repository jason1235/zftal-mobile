package com.zfsoft.mobile.webservices.query;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

public class CardBusinessQuery extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3955432731367091425L;

	
	private  String ocid;//一卡通卡号
	private  String startdate;//查询时间起始日期
	private  String enddate;//查询时间结束日期
	private  String detailtype;//查询类型（1代表消费，0代表充值）
	private  String userid;
	
	public String getOcid() {
		return ocid;
	}
	public void setOcid(String ocid) {
		this.ocid = ocid;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setDetailtype(String detailtype) {
		this.detailtype = detailtype;
	}
	public String getDetailtype() {
		return detailtype;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUserid() {
		return userid;
	}
	
	
}
