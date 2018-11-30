package com.zfsoft.sjzx.ysj.util;

import java.util.List;

public class Page {
	private String sql;
	private int rowCount;
	private int pageCount;
	private int pageTo;
	private int pagePre;
	private int pageNext;
	private int perPageSize;	
	private String primaryKey;
	private String orderBy;	
	private List<?> list;
	private String[] fields;
	 
	public String[] getFields() {
		return fields;
	}
	public void setFields(String[] fields) {
		this.fields = fields;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public int getRowCount() {
		return rowCount;
	}
	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageTo() {
		return pageTo;
	}
	public void setPageTo(int pageTo) {
		this.pageTo = pageTo;
	}
	public int getPagePre() {
		pagePre = pageTo - 1;
		if(pagePre < 1)
			pagePre = 1;
		return pagePre;
	}
	public void setPagePre(int pagePre) {
		this.pagePre = pagePre;
	}
	public int getPageNext() {
		pageNext = pageTo + 1;
		if(pageNext > pageCount)
			pageNext = pageCount;
		return pageNext;
	}
	public void setPageNext(int pageNext) {
		this.pageNext = pageNext;
	}
	public int getPerPageSize() {
		return perPageSize;
	}
	public void setPerPageSize(int perPageSize) {
		this.perPageSize = perPageSize;
	}
	public String getPrimaryKey() {
		return primaryKey;
	}
	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public List<?> getList() {
		return list;
	}
	public void setList(List<?> list) {
		this.list = list;
	}
	 
	
}
