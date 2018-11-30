/**
 * 
 */
package com.zfsoft.mobile.news.entity;

import java.util.Date;

import com.zfsoft.dao.query.BaseQuery;

/**
 * @author zhangxu
 * @description 学校风景类别
 * @date 2017-5-10 上午10:01:36
 */
public class SchoolSceneryCatalog extends BaseQuery{

	private String sceneryCatalogId;//类别id
	private String sceneryCatalogName;//类别名称
	private int sortNumber;//排序码
	private Date createTime; // 创建时间
	private String isActive;//是否启用
	public String getSceneryCatalogId() {
		return sceneryCatalogId;
	}
	public void setSceneryCatalogId(String sceneryCatalogId) {
		this.sceneryCatalogId = sceneryCatalogId;
	}
	public String getSceneryCatalogName() {
		return sceneryCatalogName;
	}
	public void setSceneryCatalogName(String sceneryCatalogName) {
		this.sceneryCatalogName = sceneryCatalogName;
	}
	public int getSortNumber() {
		return sortNumber;
	}
	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public String getIsActive() {
		return isActive;
	}
	
	
}
