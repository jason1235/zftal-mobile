package com.zfsoft.dao.entities;

import java.util.Date;

public class userStatictisEntity {
	
	private String installs;//安装量
	
	private String datetime;//日期
	
	private String startdate;//开始日期
	
	private String enddate;//介绍日期

	public void setInstalls(String installs) {
		this.installs = installs;
	}

	public String getInstalls() {
		return installs;
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

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getDatetime() {
		return datetime;
	}

	

}
