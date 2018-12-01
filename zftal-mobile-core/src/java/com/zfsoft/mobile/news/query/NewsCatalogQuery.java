package com.zfsoft.mobile.news.query;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 资讯类别查询实体
 * @author wy
 *
 */
public class NewsCatalogQuery extends BaseQuery {
	
	private static final long serialVersionUID = 1L;
	
	private String catalogId = ""; // 类别ID
	private String catalogCode= ""; // 类别编码
	private String catalogName= ""; // 类别名称
	private String showType= ""; // 展示形式
	private String source; // 类别来源
	private String status= ""; // 使用状态
	private String logoUrl= ""; // logo图片地址
	private String logoId= ""; // logo图片ID
	private String createTime= ""; // 创建时间
	private String creator= ""; // 创建者ID
	private String updateTime= ""; // 修改时间
	private String updater= ""; // 更新者ID
	private int pxm = 0; // 排序码 
	private String deleted = "0";
	private String listType; // 列表形式
	
	
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	public String getCatalogId() {
		return catalogId;
	}
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	public String getCatalogCode() {
		return catalogCode;
	}
	public void setCatalogCode(String catalogCode) {
		this.catalogCode = catalogCode;
	}
	public String getCatalogName() {
		return catalogName;
	}
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	public String getShowType() {
		return showType;
	}
	public void setShowType(String showType) {
		this.showType = showType;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getLogoId() {
		return logoId;
	}
	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getPxm() {
		return pxm;
	}
	public void setPxm(int pxm) {
		this.pxm = pxm;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getUpdater() {
		return updater;
	}
	public void setUpdater(String updater) {
		this.updater = updater;
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}
	
	
}
