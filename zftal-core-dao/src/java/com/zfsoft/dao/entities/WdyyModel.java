package com.zfsoft.dao.entities;

import java.util.List;

/**
 * 
* 
* 类名称：AncdModel 
* 类描述：按钮菜单Model
* 创建人：yijd
* 创建时间：2012-5-7 上午17:22:13 
* 修改备注： 
* @version 
*
 */
public class WdyyModel {
	private String gnmkdm;//功能模块代码,三级功能模块代码
	private String yhdm;//用户代码,职工号或者学工
	private String xssx;//显示顺序
	private String fjgndm;//父级功能代码,当前菜单的一级功能模块代码
	private String gnmkmc;//功能模块名称
	private String dyym;//对应页面
	
	private List<String> jsdms;
	private String sysCode;
	
	public String getGnmkdm() {
		return gnmkdm;
	}
	public void setGnmkdm(String gnmkdm) {
		this.gnmkdm = gnmkdm;
	}
	public String getYhdm() {
		return yhdm;
	}
	public void setYhdm(String yhdm) {
		this.yhdm = yhdm;
	}
	public String getXssx() {
		return xssx;
	}
	public void setXssx(String xssx) {
		this.xssx = xssx;
	}
	public String getFjgndm() {
		return fjgndm;
	}
	public void setFjgndm(String fjgndm) {
		this.fjgndm = fjgndm;
	}
	public String getGnmkmc() {
		return gnmkmc;
	}
	public void setGnmkmc(String gnmkmc) {
		this.gnmkmc = gnmkmc;
	}
	public String getDyym() {
		return dyym;
	}
	public void setDyym(String dyym) {
		this.dyym = dyym;
	}
	public List<String> getJsdms() {
		return jsdms;
	}
	public void setJsdms(List<String> jsdms) {
		this.jsdms = jsdms;
	}
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}

}
