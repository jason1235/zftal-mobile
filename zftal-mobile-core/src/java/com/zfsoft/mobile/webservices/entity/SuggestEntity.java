package com.zfsoft.mobile.webservices.entity;

import org.apache.avalon.framework.activity.Suspendable;

import com.google.gson.Gson;

public class SuggestEntity {

	private String schoolName;//学校
	private String suggestContent;//建议内容
	private String version;//笨笨
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	public String getSuggestContent() {
		return suggestContent;
	}
	public void setSuggestContent(String suggestContent) {
		this.suggestContent = suggestContent;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	public static void main(String[] args) {
		SuggestEntity suggest = new SuggestEntity();
		suggest.setSchoolName("西北工业大");
		suggest.setSuggestContent("app闪退");
		suggest.setVersion("6.0.1");
		Gson gson = new Gson();
		System.out.println(gson.toJson(suggest));
	}
	
	
}
