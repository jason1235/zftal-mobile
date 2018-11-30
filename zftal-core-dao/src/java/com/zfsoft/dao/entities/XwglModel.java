package com.zfsoft.dao.entities;

import com.zfsoft.common.query.ModelBase;

public class XwglModel extends ModelBase{

	private static final long serialVersionUID = 1L;
	
	private String xwbh; // 标题
	private String xwbt; // 标题
	private String fbsj;// 发布时间
	private String fbr;// 发布人
	private String fbdx;// 发布对象
	private String fbnr;// 新闻内容
	private String xwnr;// 新闻内容
	private String sffb;// 是否发布
	private String sfzd;//新闻是否置顶
	private String fbrxm;
	
	public String getFbrxm() {
		return fbrxm;
	}
	public void setFbrxm(String fbrxm) {
		this.fbrxm = fbrxm;
	}
	public String getXwnr() {
		return xwnr;
	}
	public void setXwnr(String xwnr) {
		this.xwnr = xwnr;
	}
	public String getXwbh() {
		return xwbh;
	}
	public void setXwbh(String xwbh) {
		this.xwbh = xwbh;
	}
	public String getFbdx() {
		return fbdx;
	}
	public void setFbdx(String fbdx) {
		this.fbdx = fbdx;
	}
	public String getXwbt() {
		return xwbt;
	}
	public void setXwbt(String xwbt) {
		this.xwbt = xwbt;
	}
	public String getFbsj() {
		return fbsj;
	}
	public void setFbsj(String fbsj) {
		this.fbsj = fbsj;
	}
	public String getFbr() {
		return fbr;
	}
	public void setFbr(String fbr) {
		this.fbr = fbr;
	}
	public String getFbnr() {
		return fbnr;
	}
	public void setFbnr(String fbnr) {
		this.fbnr = fbnr;
	}
	public String getSffb() {
		return sffb;
	}
	public void setSffb(String sffb) {
		this.sffb = sffb;
	}
	public String getSfzd() {
		return sfzd;
	}
	public void setSfzd(String sfzd) {
		this.sfzd = sfzd;
	}
	
	


}
