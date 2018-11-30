package com.zfsoft.mobile.classCommunity.query;

import com.zfsoft.dao.query.BaseQuery;

public class ClassEntityQuery extends BaseQuery{
	private String id;           //唯一主键
	private String name;         //班级名称
	private String instituteId;  //学院id
	private String instituteName; //学院名称
	
	private String username;  //用户账号
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInstituteId() {
		return instituteId;
	}
	public void setInstituteId(String instituteId) {
		this.instituteId = instituteId;
	}
	public String getInstituteName() {
		return instituteName;
	}
	public void setInstituteName(String instituteName) {
		this.instituteName = instituteName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
