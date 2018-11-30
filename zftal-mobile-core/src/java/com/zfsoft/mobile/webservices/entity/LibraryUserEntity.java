package com.zfsoft.mobile.webservices.entity;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

public class LibraryUserEntity {

	private String dzzh;
	private String ryh;
	private String dzxm;
	private String xb;
	private String bzrq;
	private String yjcs;
	private String ljcs;
	private String qkje;
	public String getDzzh() {
		return dzzh;
	}
	public void setDzzh(String dzzh) {
		this.dzzh = dzzh;
	}
	public String getRyh() {
		return ryh;
	}
	public void setRyh(String ryh) {
		this.ryh = ryh;
	}
	public String getDzxm() {
		return dzxm;
	}
	public void setDzxm(String dzxm) {
		this.dzxm = dzxm;
	}
	public String getXb() {
		return xb;
	}
	public void setXb(String xb) {
		this.xb = xb;
	}
	public String getBzrq() {
		return bzrq;
	}
	public void setBzrq(String bzrq) {
		this.bzrq = bzrq;
	}
	public String getYjcs() {
		return yjcs;
	}
	public void setYjcs(String yjcs) {
		this.yjcs = yjcs;
	}
	public String getLjcs() {
		return ljcs;
	}
	public void setLjcs(String ljcs) {
		this.ljcs = ljcs;
	}
	public String getQkje() {
		return qkje;
	}
	public void setQkje(String qkje) {
		this.qkje = qkje;
	}
	
	public static void main(String[] args) {
		List<LibraryUserEntity> list = new ArrayList<LibraryUserEntity>();
		LibraryUserEntity one = new LibraryUserEntity();
		one.setDzzh("789456123");
		one.setRyh("1053");
		one.setBzrq("2015-08-27");
		one.setYjcs("7");
		one.setLjcs("5");
		one.setQkje("0");
		one.setDzxm("阳章");
		
		
		list.add(one);
		//list.add(two);
		JSONArray jsonObject = JSONArray.fromObject(list);
		System.out.println(jsonObject);
	}
}
