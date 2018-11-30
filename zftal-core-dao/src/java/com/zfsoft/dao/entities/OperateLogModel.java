package com.zfsoft.dao.entities;

import com.zfsoft.common.query.ModelBase;


/**
 * 什么人什么时候作了什么操作
 * 
 * @author Administrator
 * 
 */
public class OperateLogModel extends ModelBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String czbh;// 操作编码
	private String czr;// 操作人
	private String czrq;// 操作日期
	private String czmk;// 操作模块
	private String ywmc;// 业务名称
	private String czlx;// 业务类型
	private String czms;// 操作描述
	private String ipdz;// IP地址
	private String zjm;// 主机名
	private String bmmc;
	public String getCzrq() {
		return czrq;
	}
	public void setCzrq(String czrq) {
		this.czrq = czrq;
	}
	public String getBmmc() {
		return bmmc;
	}
	public void setBmmc(String bmmc) {
		this.bmmc = bmmc;
	}
	public String getCzbh() {
		return czbh;
	}
	public void setCzbh(String czbh) {
		this.czbh = czbh;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getCzmk() {
		return czmk;
	}
	public void setCzmk(String czmk) {
		this.czmk = czmk;
	}
	public String getYwmc() {
		return ywmc;
	}
	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}
	public String getCzlx() {
		return czlx;
	}
	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}
	public String getCzms() {
		return czms;
	}
	public void setCzms(String czms) {
		this.czms = czms;
	}
	public String getIpdz() {
		return ipdz;
	}
	public void setIpdz(String ipdz) {
		this.ipdz = ipdz;
	}
	public String getZjm() {
		return zjm;
	}
	public void setZjm(String zjm) {
		this.zjm = zjm;
	}


}