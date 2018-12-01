/**
 * 
 */
package com.zfsoft.dao.entities;

import com.zfsoft.dao.query.BaseQuery;

/**
 * @author zhangxu
 * @description 
 * @date 2017-7-18 下午01:37:01
 */
public class IndexModelNew extends BaseQuery{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5859533510068196083L;
	private String gnmkdm;
	private String gnmkmc;
	private String fjgndm;
	private String dyym;
	private String xssx;
	private String isAuto="0";
	private String yhm;
	private String fsZgh;//附属职工号
	private String xm;
	
	//用于点击更多时，用于首页面的参数传递
	private String gnmkdm_ej;//功能模块代码二级
	private String gnmkdm_sj;//果农模块代码三级
	private String jsid;//

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

	public String getDyym() {
		return dyym;
	}

	public void setDyym(String dyym) {
		this.dyym = dyym;
	}

	public String getXssx() {
		return xssx;
	}

	public void setXssx(String xssx) {
		this.xssx = xssx;
	}

	public String getYhm() {
		return yhm;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getFsZgh() {
		return fsZgh;
	}

	public void setFsZgh(String fsZgh) {
		this.fsZgh = fsZgh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getGnmkdm_ej() {
		return gnmkdm_ej;
	}

	public void setGnmkdm_ej(String gnmkdmEj) {
		gnmkdm_ej = gnmkdmEj;
	}

	public String getGnmkdm_sj() {
		return gnmkdm_sj;
	}

	public void setGnmkdm_sj(String gnmkdmSj) {
		gnmkdm_sj = gnmkdmSj;
	}

	public String getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(String isAuto) {
		this.isAuto = isAuto;
	}

	public void setJsid(String jsid) {
		this.jsid = jsid;
	}

	public String getJsid() {
		return jsid;
	}
	
}
