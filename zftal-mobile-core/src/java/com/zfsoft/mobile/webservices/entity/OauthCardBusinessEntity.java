package com.zfsoft.mobile.webservices.entity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;


public class OauthCardBusinessEntity {

	private String lsh;
	
	private String jysj;
	
	private String jye;
	
	private String ryh;
	
	private String sh;
	
	private String ye;
	
	private String zh;

	
	public String getLsh() {
		return lsh;
	}


	public void setLsh(String lsh) {
		this.lsh = lsh;
	}


	public String getJysj() {
		return jysj;
	}


	public void setJysj(String jysj) {
		this.jysj = jysj;
	}


	public String getJye() {
		return jye;
	}


	public void setJye(String jye) {
		this.jye = jye;
	}


	public String getRyh() {
		return ryh;
	}


	public void setRyh(String ryh) {
		this.ryh = ryh;
	}


	public String getSh() {
		return sh;
	}


	public void setSh(String sh) {
		this.sh = sh;
	}


	public String getYe() {
		return ye;
	}


	public void setYe(String ye) {
		this.ye = ye;
	}


	public String getZh() {
		return zh;
	}


	public void setZh(String zh) {
		this.zh = zh;
	}


	public static void main(String[] args) {
		List<OauthCardBusinessEntity> list = new ArrayList<OauthCardBusinessEntity>();
		OauthCardBusinessEntity one = new OauthCardBusinessEntity();
		one.setZh("1211");
		one.setLsh("123456");
		one.setJye("12.56");
		one.setJysj("2016/05/25");
		one.setRyh("1211");
		one.setSh("肯德基");
		one.setYe("56.45");
		OauthCardBusinessEntity two = new OauthCardBusinessEntity();
		two.setZh("1211");
		two.setLsh("456789");
		two.setJye("56.4");
		two.setJysj("2016/07/28");
		two.setRyh("1211");
		two.setSh("麦当劳");
		two.setYe("56.24");
		
		list.add(one);
		list.add(two);
		JSONArray jsonObject = JSONArray.fromObject(list);
		try {
			System.out.println(URLEncoder.encode(jsonObject.toString(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
