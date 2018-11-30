package com.zfsoft.dao.entities;

import com.zfsoft.dao.query.BaseQuery;

public class ApiIndexModel extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6420242624712024030L;
	private String gnmkdm;
	private String gnmkmc;
	private String fjgndm;
	private String content;
	private String xssx;
	private String sfqy;
	public String getGnmkdm() {
		return gnmkdm;
	}
	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}
	public String getGnmkmc() {
		return gnmkmc;
	}
	public void setGnmkmc(String gnmkmc) {
		this.gnmkmc = gnmkmc;
	}
	public String getFjgndm() {
		return fjgndm;
	}
	public void setFjgndm(String fjgndm) {
		this.fjgndm = fjgndm;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getXssx() {
		return xssx;
	}
	public void setXssx(String xssx) {
		this.xssx = xssx;
	}
	public String getSfqy() {
		return sfqy;
	}
	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}
	
}
