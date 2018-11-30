package com.zfsoft.mobile.servlet.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.google.gson.Gson;

public class AppServiceInfo {

	private String userId;
    private List<String> serviceList;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<String> serviceList) {
        this.serviceList = serviceList;
    }

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
    
    public static void main(String[] args) {
    	AppServiceInfo info = new AppServiceInfo();
    	info.setUserId("1211");
    	List<String> serviceList = new ArrayList<String>();
    	serviceList.add("302");
    	serviceList.add("306");
    	info.setServiceList(serviceList);
    	Gson gson = new Gson();
    	System.out.println(gson.toJson(info));
	}

}
