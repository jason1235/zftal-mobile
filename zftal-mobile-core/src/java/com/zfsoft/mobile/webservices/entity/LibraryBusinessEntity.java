package com.zfsoft.mobile.webservices.entity;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

public class LibraryBusinessEntity {

	private String dzzh;	
	private String txm;	
	private String ztm;	
	private String jcsj;	
	private String xhsj;	
	private String xjcs;	
	private String ltlx;	
	private String wxlx;	
	private String sqh;	
	private String cbs;	
	private String cbrq;	
	private String yjs;
	private String sfgh;	
	private String ghsj;	
	private String zz;	
	private String gcd;	
	private String lsh;
	public String getDzzh() {
		return dzzh;
	}
	public void setDzzh(String dzzh) {
		this.dzzh = dzzh;
	}
	public String getTxm() {
		return txm;
	}
	public void setTxm(String txm) {
		this.txm = txm;
	}
	public String getZtm() {
		return ztm;
	}
	public void setZtm(String ztm) {
		this.ztm = ztm;
	}
	public String getJcsj() {
		return jcsj;
	}
	public void setJcsj(String jcsj) {
		this.jcsj = jcsj;
	}
	public String getXhsj() {
		return xhsj;
	}
	public void setXhsj(String xhsj) {
		this.xhsj = xhsj;
	}
	public String getXjcs() {
		return xjcs;
	}
	public void setXjcs(String xjcs) {
		this.xjcs = xjcs;
	}
	public String getLtlx() {
		return ltlx;
	}
	public void setLtlx(String ltlx) {
		this.ltlx = ltlx;
	}
	public String getWxlx() {
		return wxlx;
	}
	public void setWxlx(String wxlx) {
		this.wxlx = wxlx;
	}
	public String getSqh() {
		return sqh;
	}
	public void setSqh(String sqh) {
		this.sqh = sqh;
	}
	public String getCbs() {
		return cbs;
	}
	public void setCbs(String cbs) {
		this.cbs = cbs;
	}
	public String getCbrq() {
		return cbrq;
	}
	public void setCbrq(String cbrq) {
		this.cbrq = cbrq;
	}
	public String getYjs() {
		return yjs;
	}
	public void setYjs(String yjs) {
		this.yjs = yjs;
	}
	public String getSfgh() {
		return sfgh;
	}
	public void setSfgh(String sfgh) {
		this.sfgh = sfgh;
	}
	public String getGhsj() {
		return ghsj;
	}
	public void setGhsj(String ghsj) {
		this.ghsj = ghsj;
	}
	public String getZz() {
		return zz;
	}
	public void setZz(String zz) {
		this.zz = zz;
	}
	public String getGcd() {
		return gcd;
	}
	public void setGcd(String gcd) {
		this.gcd = gcd;
	}
	public String getLsh() {
		return lsh;
	}
	public void setLsh(String lsh) {
		this.lsh = lsh;
	}

	public static void main(String[] args) {
		List<LibraryBusinessEntity> list = new ArrayList<LibraryBusinessEntity>();
		LibraryBusinessEntity one = new LibraryBusinessEntity();
		one.setDzzh("789456123");
		one.setZtm("Java开发");
		one.setJcsj("2016-09-28");
		one.setXhsj("2016-09-28");
		one.setSfgh("0");
		one.setLsh("123456");
		
		
		list.add(one);
		//list.add(two);
		JSONArray jsonObject = JSONArray.fromObject(list);
		System.out.println(jsonObject);
	}
}
