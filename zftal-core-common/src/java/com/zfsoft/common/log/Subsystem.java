package com.zfsoft.common.log;

import java.io.Serializable;
/**
 * 子系统实体
 * 
 * @author gonghui
 * 2014-5-15
 */
public class Subsystem implements Serializable{

	private static final long serialVersionUID = 1L;
	
	/**子系统编码*/
	private String sysCode;
	/**子系统名称*/
	private String sysName;
	/**子系统描述*/
	private String sysDesc;
	/**是否启用*/
	private String enabled;
	/**显示序号*/
	private Integer seq;
	/**子系统入口图片路径*/
	private String entry_icon;
	/**子系统首页图片路径*/
	private String index_icon;
	/**子系统首页路径*/
	private String index_page;
	
	
	private String orderStr;
	
	public String getSysCode() {
		return sysCode;
	}
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	public String getSysName() {
		return sysName;
	}
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}
	public String getSysDesc() {
		return sysDesc;
	}
	public void setSysDesc(String sysDesc) {
		this.sysDesc = sysDesc;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getOrderStr() {
		return orderStr;
	}
	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}
	public String getEntry_icon() {
		return entry_icon;
	}
	public void setEntry_icon(String entry_icon) {
		this.entry_icon = entry_icon;
	}
	public String getIndex_icon() {
		return index_icon;
	}
	public void setIndex_icon(String index_icon) {
		this.index_icon = index_icon;
	}
	public String getIndex_page() {
		return index_page;
	}
	public void setIndex_page(String index_page) {
		this.index_page = index_page;
	}
	
}
