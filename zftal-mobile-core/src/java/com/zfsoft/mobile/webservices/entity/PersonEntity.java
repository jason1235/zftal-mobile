package com.zfsoft.mobile.webservices.entity;

public class PersonEntity {
	
	//人员id
	private String ryid;
	//人员名册
	private String ryname;
	//人员头像
	private String ryico;
	//人员头像id
	private String ryicoid;
	//人员拼音标识
	private String pybs;
	//联系电话
	private String lxdh;
	
	public String getRyid() {
		return ryid;
	}
	public void setRyid(String ryid) {
		this.ryid = ryid;
	}
	public String getRyname() {
		return ryname;
	}
	public void setRyname(String ryname) {
		this.ryname = ryname;
	}
	public String getRyico() {
		return ryico;
	}
	public void setRyico(String ryico) {
		this.ryico = ryico;
	}
	public void setRyicoid(String ryicoid) {
		this.ryicoid = ryicoid;
	}
	public String getRyicoid() {
		return ryicoid;
	}
	public void setPybs(String pybs) {
		this.pybs = pybs;
	}
	public String getPybs() {
		return pybs;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getLxdh() {
		return lxdh;
	}
	
	
}
