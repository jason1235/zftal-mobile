package com.zfsoft.sjzx.jhpz.yyfwq.model;

import com.zfsoft.framework.page.BaseDataObject;

/**
 * 业务服务器实体类
 * 
 * @author esadmin
 * 
 */
public class Yyfwq extends BaseDataObject {

	private static final long serialVersionUID = 1L;
	private Integer yyfwqlsh;
	private String jkdk;// 监控端口
	private String jtdk;// 监听端口
	private String yyfwqmc;// 应用服务器名称
	private String ipdz;// ip地址
	private String dkh;// 端口号
	private String wsm;// 握手码
	private String cjsj;// 创建时间
	private String xgsj;// 修改时间
	private String cjr;// 创建人
	private String xgr;// 修改人
	public String dlm;// 登录名
	private String zt;// 状态
	private String isjk;// 是否监控

	public String getJtdk() {
		return jtdk;
	}

	public void setJtdk(String jtdk) {
		this.jtdk = jtdk;
	}

	public String getJkdk() {
		return jkdk;
	}

	public void setJkdk(String jkdk) {
		this.jkdk = jkdk;
	}

	public void setYyfwqlsh(Integer yyfwqlsh) {
		this.yyfwqlsh = yyfwqlsh;
	}

	public Integer getYyfwqlsh() {
		return this.yyfwqlsh;
	}

	public void setYyfwqmc(String yyfwqmc) {
		this.yyfwqmc = yyfwqmc;
	}

	public String getYyfwqmc() {
		return this.yyfwqmc;
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

	public void setWsm(String wsm) {
		this.wsm = wsm;
	}

	public String getWsm() {
		return this.wsm;
	}

	public void setCjsj(String cjsj) {
		this.cjsj = cjsj;
	}

	public String getCjsj() {
		return this.cjsj;
	}

	public void setXgsj(String xgsj) {
		this.xgsj = xgsj;
	}

	public String getXgsj() {
		return this.xgsj;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	public String getCjr() {
		return this.cjr;
	}

	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	public String getXgr() {
		return this.xgr;
	}

	public String getDlm() {
		return dlm;
	}

	public void setDlm(String dlm) {
		this.dlm = dlm;
	}

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}

	public String getIsjk() {
		return isjk;
	}

	public void setIsjk(String isjk) {
		this.isjk = isjk;
	}
	
}