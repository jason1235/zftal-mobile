package com.zfsoft.mobile.servlet.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class MhRecommend {

	private String id;//id值 
	private String title;
	private List<String> logoPathList = new ArrayList<String>();
	private String url;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<String> getLogoPathList() {
		return logoPathList;
	}
	public void setLogoPathList(List<String> logoPathList) {
		this.logoPathList = logoPathList;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	
}
