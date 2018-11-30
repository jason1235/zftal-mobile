package com.zfsoft.hrm.contract.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 提醒合同
 * @author: xiaoyongjun
 * @since: 2014-3-4 下午03:57:13
 */
public class RemindContract implements Serializable {
	
	/* serialVersionUID: serialVersionUID */
	
	private static final long serialVersionUID = -8343351937093188910L;
	private String htzlmc;
	private int dqtxts;
	private int xqtxts;
	private int syqtxts;
	private String glry;
	private String htzlbm;
	private String gh;
	private String htbh;
	private Date htzzrq;
	private Date syqjzrq;
	
	public String getHtzlmc() {
		return htzlmc;
	}
	public void setHtzlmc(String htzlmc) {
		this.htzlmc = htzlmc;
	}
	public int getDqtxts() {
		return dqtxts;
	}
	public void setDqtxts(int dqtxts) {
		this.dqtxts = dqtxts;
	}
	public int getXqtxts() {
		return xqtxts;
	}
	public void setXqtxts(int xqtxts) {
		this.xqtxts = xqtxts;
	}
	public int getSyqtxts() {
		return syqtxts;
	}
	public void setSyqtxts(int syqtxts) {
		this.syqtxts = syqtxts;
	}
	public String getGlry() {
		return glry;
	}
	public void setGlry(String glry) {
		this.glry = glry;
	}
	public String getHtzlbm() {
		return htzlbm;
	}
	public void setHtzlbm(String htzlbm) {
		this.htzlbm = htzlbm;
	}
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public String getHtbh() {
		return htbh;
	}
	public void setHtbh(String htbh) {
		this.htbh = htbh;
	}
	public Date getHtzzrq() {
		return htzzrq;
	}
	public void setHtzzrq(Date htzzrq) {
		this.htzzrq = htzzrq;
	}
	public Date getSyqjzrq() {
		return syqjzrq;
	}
	public void setSyqjzrq(Date syqjzrq) {
		this.syqjzrq = syqjzrq;
	}
}
