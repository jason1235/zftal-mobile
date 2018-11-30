package com.zfsoft.mobile.webservices.entity;

import com.zfsoft.dao.query.BaseQuery;

public class WeiBoEntity extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8055000469090043459L;
	private String wbbh;//微博编号
	private String wbmc;//微博名称
	private String client_id;//微博id
	private String client_sercret;//微博sercret
	private String accesstoken;//微博accesstoken
	private String istimeout;//accesstoken是否过期
	private String remaintime;//accesstoken剩余时长（单位：）
	private String sfqy;//是否启用
	public String getIstimeout() {
		return istimeout;
	}
	public void setIstimeout(String istimeout) {
		this.istimeout = istimeout;
	}
	public String getRemaintime() {
		return remaintime;
	}
	public void setRemaintime(String remaintime) {
		this.remaintime = remaintime;
	}
	public String getSfqy() {
		return sfqy;
	}
	public void setSfqy(String sfqy) {
		this.sfqy = sfqy;
	}
	public String getWbbh() {
		return wbbh;
	}
	public void setWbbh(String wbbh) {
		this.wbbh = wbbh;
	}
	public String getWbmc() {
		return wbmc;
	}
	public void setWbmc(String wbmc) {
		this.wbmc = wbmc;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getClient_sercret() {
		return client_sercret;
	}
	public void setClient_sercret(String client_sercret) {
		this.client_sercret = client_sercret;
	}
	public String getAccesstoken() {
		return accesstoken;
	}
	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	
	
}
