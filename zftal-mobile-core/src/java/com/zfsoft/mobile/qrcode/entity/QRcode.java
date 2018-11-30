package com.zfsoft.mobile.qrcode.entity;

import com.zfsoft.dao.query.BaseQuery;

public class QRcode  extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7501655530522016101L;
	
	private String 	codeid;//二维码id
	private String 	codename;//二维码名称
	private String 	codeimageid;//二维码图片id
	private String 	codeintroduction;//二维码简介
	private String  codelogoid;
	private String  codelogourl;
	public String getCodeid() {
		return codeid;
	}
	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}
	public String getCodename() {
		return codename;
	}
	public void setCodename(String codename) {
		this.codename = codename;
	}
	public String getCodeimageid() {
		return codeimageid;
	}
	public void setCodeimageid(String codeimageid) {
		this.codeimageid = codeimageid;
	}
	public String getCodeintroduction() {
		return codeintroduction;
	}
	public void setCodeintroduction(String codeintroduction) {
		this.codeintroduction = codeintroduction;
	}
	public String getCodelogoid() {
		return codelogoid;
	}
	public void setCodelogoid(String codelogoid) {
		this.codelogoid = codelogoid;
	}
	public String getCodelogourl() {
		return codelogourl;
	}
	public void setCodelogourl(String codelogourl) {
		this.codelogourl = codelogourl;
	}
	

}
