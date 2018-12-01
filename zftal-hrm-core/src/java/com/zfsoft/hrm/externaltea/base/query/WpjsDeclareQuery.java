package com.zfsoft.hrm.externaltea.base.query;


import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * @author xiaoyj
 * @date 2013-5-16 下午02:07:33 
 *  
 */

public class WpjsDeclareQuery extends BaseQuery {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5883735055635347305L;

	private String bmdm;
	
	private String bz;

	private String cjr;

	private Date cjsj;

	private Date cjsjEnd;

	private Date cjsjStart;

	private Date csny;

	private String id;

	private String prqx;

	private String rjbj;

	private String rjkc;

	private String rylb;

	private boolean sfggk;

	private String sfzh;

	private String xb;

	private String xgr;

	private Date xgsj;

	private String xl;

	private String xm;

	private String xw;
	
	private String ygh;

	private String zt;

	private String zyjszw;

	public String getBmdm() {
		return bmdm;
	}


	public String getBz() {
		return bz;
	}

	public String getCjr() {
		return cjr;
	}

	public Date getCjsj() {
		return cjsj;
	}

	public Date getCjsjEnd() {
		return cjsjEnd;
	}

	public String getCjsjEndString() {
		return TimeUtil.format(cjsjEnd, "yyyy-MM-dd");
	}

	public Date getCjsjStart() {
		return cjsjStart;
	}

	public String getCjsjStartString() {
		return TimeUtil.format(cjsjStart, "yyyy-MM-dd");
	}

	public Date getCsny() {
		return csny;
	}

	public String getId() {
		return id;
	}

	public String getPrqx() {
		return prqx;
	}

	public String getRjbj() {
		return rjbj;
	}

	public String getRjkc() {
		return rjkc;
	}

	public String getRylb() {
		return rylb;
	}

	public String getSfzh() {
		return sfzh;
	}

	public String getXb() {
		return xb;
	}

	public String getXgr() {
		return xgr;
	}

	public Date getXgsj() {
		return xgsj;
	}

	public String getXl() {
		return xl;
	}
	
	public String getXm() {
		return xm;
	}
	
	public String getXw() {
		return xw;
	}
	
	public String getYgh() {
		return ygh;
	}
	
	public String getZt() {
		return zt;
	}
	
	public String getZyjszw() {
		return zyjszw;
	}
	
	public boolean isSfggk() {
		return sfggk;
	}
	
	public void setBmdm(String bmdm) {
		this.bmdm = bmdm;
	}
	
	public void setBz(String bz) {
		this.bz = bz;
	}
	
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}
	
	public void setCjsjEnd(Date cjsjEnd) {
		this.cjsjEnd = cjsjEnd;
	}
	
	public void setCjsjStart(Date cjsjStart) {
		this.cjsjStart = cjsjStart;
	}
	
	public void setCsny(Date csny) {
		this.csny = csny;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setPrqx(String prqx) {
		this.prqx = prqx;
	}
	
	public void setRjbj(String rjbj) {
		this.rjbj = rjbj;
	}
	
	public void setRjkc(String rjkc) {
		this.rjkc = rjkc;
	}
	
	public void setRylb(String rylb) {
		this.rylb = rylb;
	}
	
	public void setSfggk(boolean sfggk) {
		this.sfggk = sfggk;
	}
	
	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}
	
	public void setXb(String xb) {
		this.xb = xb;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}

	public void setXl(String xl) {
		this.xl = xl;
	}
	
	public void setXm(String xm) {
		this.xm = xm;
	}

	public void setXw(String xw) {
		this.xw = xw;
	}

	public void setYgh(String ygh) {
		this.ygh = ygh;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
	
	public void setZyjszw(String zyjszw) {
		this.zyjszw = zyjszw;
	}		
}
