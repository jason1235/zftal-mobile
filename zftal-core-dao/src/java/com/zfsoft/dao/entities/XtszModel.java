package com.zfsoft.dao.entities;


/**
 * 系统维护
 * 
 * @author Administrator
 * 
 */
public class XtszModel{

	// 处理学校名称
	private String xxdm;
	// 处理学校名称
	private String xxmc;
	// 处理当前学年
	private String dqxn;

	// 处理当前学期
	private String dqxq;
	// 处理当前年度
	private String dqnd;
	public String getXxmc() {
		return xxmc;
	}
	public void setXxmc(String xxmc) {
		this.xxmc = xxmc;
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
	public String getDqnd() {
		return dqnd;
	}
	public void setDqnd(String dqnd) {
		this.dqnd = dqnd;
	}
	public String getXxdm() {
		return xxdm;
	}
	public void setXxdm(String xxdm) {
		this.xxdm = xxdm;
	}


}
