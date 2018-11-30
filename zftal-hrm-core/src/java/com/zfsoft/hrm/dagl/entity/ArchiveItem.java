package com.zfsoft.hrm.dagl.entity;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-10-21
 * @version V1.0.0
 */
public class ArchiveItem extends BaseQuery{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2547076099054585500L;
	//归属档案id
	private String archiveId;
	//材料id
	private String itemId;
	//材料编号
	private String bh;
	//材料名称
	private String name;
	//材料描述
	private String desc;
	//材料入档时间
	private Date createTime;
	//记录修改时间
	private Date lastModify;
	//类号
	private String lh;
	//序号
	private String xh;
	//份数
	private int fs;
	//页码
	private int ym;
	//职工号
	private String gh;
	//姓名
	private String xm;
	public String getGh() {
		return gh;
	}
	public void setGh(String gh) {
		this.gh = gh;
	}
	public String getXm() {
		return xm;
	}
	public void setXm(String xm) {
		this.xm = xm;
	}
	public String getLh() {
		return lh;
	}
	public void setLh(String lh) {
		this.lh = lh;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
	}
	public int getFs() {
		return fs;
	}
	public void setFs(int fs) {
		this.fs = fs;
	}
	public int getYm() {
		return ym;
	}
	public void setYm(int ym) {
		this.ym = ym;
	}
	/**
	 * 返回
	 */
	public String getArchiveId() {
		return archiveId;
	}
	/**
	 * 设置
	 * @param archiveId 
	 */
	public void setArchiveId(String archiveId) {
		this.archiveId = archiveId;
	}
	/**
	 * 返回
	 */
	public String getItemId() {
		return itemId;
	}
	/**
	 * 设置
	 * @param itemId 
	 */
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	/**
	 * 返回
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 返回
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置
	 * @param createTime 
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 返回
	 */
	public Date getLastModify() {
		return lastModify;
	}
	/**
	 * 设置
	 * @param lastModify 
	 */
	public void setLastModify(Date lastModify) {
		this.lastModify = lastModify;
	}
	/**
	 * 返回
	 */
	public String getBh() {
		return bh;
	}
	/**
	 * 设置
	 * @param bh 
	 */
	public void setBh(String bh) {
		this.bh = bh;
	}
	/**
	 * 返回
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * 设置
	 * @param desc 
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
