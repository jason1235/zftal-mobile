package com.zfsoft.hrm.expertvote.vote.query;

import com.zfsoft.common.query.QueryModel;

public class GroupMemberQuery extends QueryModel {
	private String id;    //专家用户对应的ID号
	private String gh;   //工号
	private String xm;	 //姓名
	private String dwm;  //单位码
	private String type;	 //专业
	
	private String zjz_id; //专家组id
	
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getDwm() {
		return dwm;
	}
	public void setDwm(String dwm) {
		this.dwm = dwm;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getZjz_id() {
		return zjz_id;
	}
	public void setZjz_id(String zjz_id) {
		this.zjz_id = zjz_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
