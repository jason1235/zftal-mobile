package com.zfsoft.sjzx.jhpz.sjkpz.model;

import com.zfsoft.framework.page.BaseDataObject;

public class Sjkpz extends BaseDataObject {

	/**
	 * 序列化配置
	 */
	private static final long serialVersionUID = 1L;

	private String pzxh; // 配置序号
	private String pzljmc; // 配置连接名称
	private String ipdz;// IP地址
	private String dkh;// 端口号
	private String sjkmc;// 数据库名称
	private String yhm;// 用户名
	private String mm;// 密码
	private String yyzt;
	private int ljlx; // 数据连接类型
	private int sjklx;// 数据库类型
	private String racx; // RAC配置
	private String racdz; // RAC地址
	private String isjk; // 是否监控

	private String zt;
	private String zydz;
	private String sjklxmc;
	private String ljlxmc;

	private String cjsj;
	private String cjr;
	private String xgsj;
	private String xgr;

	public void setPzxh(String pzxh) {
		this.pzxh = pzxh;
	}

	public String getPzxh() {
		return this.pzxh;
	}

	public void setPzljmc(String pzljmc) {
		this.pzljmc = pzljmc;
	}

	public String getPzljmc() {
		return this.pzljmc;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getZt() {
		return this.zt;
	}

	public void setZydz(String zydz) {
		this.zydz = zydz;
	}

	public String getZydz() {
		return this.zydz;
	}

	public void setIpdz(String ipdz) {
		this.ipdz = ipdz;
	}

	public String getIpdz() {
		return this.ipdz;
	}

	public void setDkh(String dkh) {
		this.dkh = dkh;
	}

	public String getDkh() {
		return this.dkh;
	}

	public void setSjkmc(String sjkmc) {
		this.sjkmc = sjkmc;
	}

	public String getSjkmc() {
		return this.sjkmc;
	}

	public void setYhm(String yhm) {
		this.yhm = yhm;
	}

	public String getYhm() {
		return this.yhm;
	}

	public void setMm(String mm) {
		this.mm = mm;
	}

	public String getMm() {
		return this.mm;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public String getCjsj() {
		return this.cjsj;
	}

	private int px;

	public void setPx(int px) {
		this.px = px;
	}

	public int getPx() {
		return this.px;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	public String getXgsj() {
		return this.xgsj;
	}

	public void setSjklx(int sjklx) {
		this.sjklx = sjklx;
	}

	public int getSjklx() {
		return this.sjklx;
	}

	public void setLjlx(int ljlx) {
		this.ljlx = ljlx;
	}

	public int getLjlx() {
		return this.ljlx;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	public String getXgr() {
		return this.xgr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	public String getCjr() {
		return this.cjr;
	}

	/**
	 * @return the racdz
	 */
	public String getRacdz() {
		return racdz;
	}

	/**
	 * @param racdz
	 *            the racdz to set
	 */
	public void setRacdz(String racdz) {
		this.racdz = racdz;
	}

	/**
	 * @return the rac
	 */
	public String getRacx() {
		return racx;
	}

	/**
	 * @param rac
	 *            the rac to set
	 */
	public void setRacx(String racx) {
		this.racx = racx;
	}

	/**
	 * @return the yyzt
	 */
	public String getYyzt() {
		return yyzt;
	}

	/**
	 * @param yyzt
	 *            the yyzt to set
	 */
	public void setYyzt(String yyzt) {
		this.yyzt = yyzt;
	}

	/**
	 * @return the sjklxmc
	 */
	public String getSjklxmc() {
		return sjklxmc;
	}

	/**
	 * @param sjklxmc
	 *            the sjklxmc to set
	 */
	public void setSjklxmc(String sjklxmc) {
		this.sjklxmc = sjklxmc;
	}

	/**
	 * @return the ljlxmc
	 */
	public String getLjlxmc() {
		return ljlxmc;
	}

	/**
	 * @param ljlxmc
	 *            the ljlxmc to set
	 */
	public void setLjlxmc(String ljlxmc) {
		this.ljlxmc = ljlxmc;
	}

	public String getIsjk() {
		return isjk;
	}

	public void setIsjk(String isjk) {
		this.isjk = isjk;
	}

}