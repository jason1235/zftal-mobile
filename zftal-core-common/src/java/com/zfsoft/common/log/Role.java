package com.zfsoft.common.log;

import java.io.Serializable;


/**
 * 角色
 * 
 * @author gonghui
 * 2013-8-28
 */
public class Role implements Serializable{
	
	private static final long serialVersionUID = -2044533545247893221L;

	private String jsdm;
	
	private String jsmc;
	
	private String sfejsq;
	
	/**子系统*/
	private String sysCode;
	
	public String getJsdm() {
		return jsdm;
	}
	public void setJsdm(String jsdm) {
		this.jsdm = jsdm;
	}
	public String getJsmc() {
		return jsmc;
	}
	public void setJsmc(String jsmc) {
		this.jsmc = jsmc;
	}
	
	public String getSfejsq() {
		return sfejsq;
	}
	public void setSfejsq(String sfejsq) {
		this.sfejsq = sfejsq;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	
}
