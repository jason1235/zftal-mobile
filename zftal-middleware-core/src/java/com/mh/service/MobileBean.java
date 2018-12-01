package com.mh.service;

/**
 * @author Administrator
 * 移动接口bean
 */
public class MobileBean {
	
	private boolean cacheck;  //验证结果：（成功或者失败）
	//private String cacheck;  //验证结果
	private String jsName;  //权限标识。（如：老师，学生等）
	//用户属性。（姓名，部门，当前学年，当前学期）
	private String yhm;
	private String xm;
	private String bm;
	private String dqxn;
	private String dqxq;
	private String ticket; //ticket，可以通过门户验证，用来直接下载附件
	
	private String code;
	private String message;
	
	private String zjhm;
	
	public boolean isCacheck() {
		return cacheck;
	}
	public void setCacheck(boolean cacheck) {
		this.cacheck = cacheck;
	}
	/*
	public String getCacheck() {
		return cacheck;
	}
	public void setCacheck(String cacheck) {
		this.cacheck = cacheck;
	}
	*/
	public String getJsName() {
		return jsName;
	}
	public void setJsName(String jsName) {
		this.jsName = jsName;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getBm() {
		return bm;
	}
	public void setBm(String bm) {
		this.bm = bm;
	}
	public String getDqxn() {
		return dqxn;
	}
	public void setDqxn(String dqxn) {
		this.dqxn = dqxn;
	}
	public String getDqxq() {
		return dqxq;
	}
	public void setDqxq(String dqxq) {
		this.dqxq = dqxq;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}
	public String getYhm() {
		return yhm;
	}
	public void setYhm(String yhm) {
		this.yhm = yhm;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}

