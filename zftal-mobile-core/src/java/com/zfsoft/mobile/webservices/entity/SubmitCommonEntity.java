package com.zfsoft.mobile.webservices.entity;

import java.util.List;


public class SubmitCommonEntity {

	private String username;
	
	private List<String> serviceCodeList;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getServiceCodeList() {
		return serviceCodeList;
	}

	public void setServiceCodeList(List<String> serviceCodeList) {
		this.serviceCodeList = serviceCodeList;
	}
	
	
}
