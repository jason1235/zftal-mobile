package com.zfsoft.hrm.externaltea.base.entity;

import java.util.Date;

import com.zfsoft.orcus.lang.TimeUtil;


/** 
 * @author xiaoyj
 * @date 2013-5-16 13:06:20 
 *  
 */
public class WpjsDeclare {

	private String id;
	
	private String ygh;
	
	private String xm;
	
	private String xb;
	
	private Date csny;
	
	private String sfzh;
	
	private String xl;
	
	private String xw;
	
	private String zyjszw;
	
	private String bmdm;
	
	private String rjbj;
	
	private String rjkc;
	
	private String sfggk;
	
	private String prqx;
	
	private String rylb;
	
	private String bz;
	
	private String cjr;
	
	private Date cjsj;
	
	private String xgr;
	
	private Date xgsj;
	
	private String zt;
	
	private String perPageSize;
	
	public String getSfggk() {
		return sfggk;
	}

	public void setSfggk(String sfggk) {
		this.sfggk = sfggk;
	}

	public String getPerPageSize() {
		return perPageSize;
	}

	public void setPerPageSize(String perPageSize) {
		this.perPageSize = perPageSize;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getYgh() {
		return ygh;
	}

	public void setYgh(String ygh) {
		this.ygh = ygh;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getXb() {
		return xb;
	}

	public void setXb(String xb) {
		this.xb = xb;
	}

	public Date getCsny() {
		return csny;
	}

	public void setCsny(Date csny) {
		this.csny = csny;
	}

	public String getCsnyText(){
		return TimeUtil.format(csny, "yyyy-MM-dd");
	}
	
	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getXl() {
		return xl;
	}

	public void setXl(String xl) {
		this.xl = xl;
	}

	public String getXw() {
		return xw;
	}

	public void setXw(String xw) {
		this.xw = xw;
	}

	public String getZyjszw() {
		return zyjszw;
	}

	public void setZyjszw(String zyjszw) {
		this.zyjszw = zyjszw;
	}

	public String getBmdm() {
		return bmdm;
	}

	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}

	public String getRjbj() {
		return rjbj;
	}

	public void setRjbj(String rjbj) {
		this.rjbj = rjbj;
	}

	public String getRjkc() {
		return rjkc;
	}

	public void setRjkc(String rjkc) {
		this.rjkc = rjkc;
	}

	public String getPrqx() {
		return prqx;
	}

	public void setPrqx(String prqx) {
		this.prqx = prqx;
	}

	public String getRylb() {
		return rylb;
	}

	public void setRylb(String rylb) {
		this.rylb = rylb;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getCjr() {
		return cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	public Date getCjsj() {
		return cjsj;
	}
	
	public String getCjsjString() {
		return TimeUtil.format(csny, "yyyy-MM-dd");
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public String getXgr() {
		return xgr;
	}	

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	public Date getXgsj() {
		return xgsj;
	}
	
	public String getXgsjString() {
		return TimeUtil.format(csny, "yyyy-MM-dd");
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}
}
