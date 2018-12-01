package com.zfsoft.mobile.servlet.entity;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class NewsEntity {

	private String id;//咨询id
	private String timeCreate;//创建时间
	private String title;//咨询标题
	private List<String> logoPathList = new ArrayList<String>();//logo地址集合
	private String introduce;//咨询简介
	private String catalogCode;//所属咨询类别编码
	private String webUrl;//web链接地址
	private String author;//来源或作者
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTimeCreate() {
		return timeCreate;
	}
	public void setTimeCreate(String timeCreate) {
		this.timeCreate = timeCreate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<String> getLogoPathList() {
		return logoPathList;
	}
	public void setLogoPathList(List<String> logoPathList) {
		this.logoPathList = logoPathList;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getCatalogCode() {
		return catalogCode;
	}
	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	
}
