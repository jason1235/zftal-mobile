package com.zfsoft.hrm.contract.entity;

import java.util.Date;

import com.zfsoft.common.query.QueryModel;
/**
 * 合同种类管理
 * @author: xiaoyongjun
 * @since: 2014-2-26 下午06:16:35
 */
public class CategoryConfig extends QueryModel {
	private String htzldm;    //合同种类代码
	private String htzlmc;    //合同种类名称
	private int dqtxts;	//到期提醒天数
	private int xqtxts;	//续签提醒天数
	private int syqtxts;	//试用期提醒天数
	private String glry;		//管理人员
	private String sfqy;		//是否启用
	private String sfzdtx;	//是否自动提醒
	private String bz;		//备注
	private String cjr;		//创建人
	private Date cjsj;		//创建时间
	private String xgr;		//修改人
	private Date xgsj;		//修改时间
	 
	public String getHtzldm() {
		return htzldm;
	}
	public void setHtzldm(String htzldm) {
		this.htzldm = htzldm;
	}
	public String getHtzlmc() {
		return htzlmc;
	}
	public void setHtzlmc(String htzlmc) {
		this.htzlmc = htzlmc;
	}
	
	public String getGlry() {
		return glry;
	}
	public void setGlry(String glry) {
		this.glry = glry;
	}
	public String getSfqy() {
		return sfqy;
	}
	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}
	public String getSfzdtx() {
		return sfzdtx;
	}
	public void setSfzdtx(String sfzdtx) {
		this.sfzdtx = sfzdtx;
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
	public Date getCjsj() {
		return cjsj;
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
	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}
}
