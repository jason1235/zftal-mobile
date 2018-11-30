package com.zfsoft.mobile.services.entity;

import java.util.Date;

public class OauthYhDyXt {

	private String yhid;//用户id
	private String procode;//系统procode
	private long endtime;//有效截止时间
	public String getYhid() {
		return yhid;
	}
	public void setYhid(String yhid) {
		this.yhid = yhid;
	}
	public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
	public void setEndtime(long endtime) {
		this.endtime = endtime;
	}
	public long getEndtime() {
		return endtime;
	}
	
	
}
