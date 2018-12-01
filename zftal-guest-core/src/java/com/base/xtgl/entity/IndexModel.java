package com.base.xtgl.entity;

import com.zfsoft.dao.query.BaseQuery;



public class IndexModel extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6420242624712024030L;
	private String gnmkdm;//功能模块代码
	private String gnmkmc;//功能模块名称
	private String fjgndm;//腹肌功能代码
	private String xssx;//显示顺序
	private String sfqy;//是否启用
	private String dyym;//对应页面
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
	public void setDyym(String dyym) {
		this.dyym = dyym;
	}
	public String getDyym() {
		return dyym;
	}
	
}
