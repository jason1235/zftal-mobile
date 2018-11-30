package com.zfsoft.mobile.webservices.entity;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.zfsoft.util.base.StringUtil;

public class InformationListEntity {

	//个人信息类别id
	private String informationid;
	//个人信息类别名称
	private String informationname;
	//个人信息类别LOG图
	private String informationico;
	
	private List<List<Map<String,String>>> informationList;
	
	
	
	public String getInformationid() {
		return informationid;
	}
	public void setInformationid(String informationid) {
		this.informationid = StringUtil.isEmpty(informationid) ? "" : informationid;
	}
	public String getInformationname() {
		return informationname;
	}
	public void setInformationname(String informationname) {
		this.informationname = StringUtil.isEmpty(informationname) ? "" : informationname;
	}
	public String getInformationico() {
		return informationico;
	}
	public void setInformationico(String informationico) {
		this.informationico = StringUtil.isEmpty(informationico) ? "" : informationico;
	}
	public void setInformationList(List<List<Map<String,String>>> informationList) {
		this.informationList = informationList;
	}
	public List<List<Map<String,String>>> getInformationList() {
		return informationList;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
