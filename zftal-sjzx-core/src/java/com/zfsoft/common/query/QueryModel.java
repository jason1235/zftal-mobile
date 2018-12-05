package com.zfsoft.common.query;

import java.util.List;

public class QueryModel {
	//每页显示记录
	private int showCount = 20;
	//总页
	private int totalPage;
	//总页数
	private int totalResult;
	//当前页数
	private int currentPage;
	//当前记录起始索引
	private int currentResult;
	//true:分页的地方，传入的参数就是Page实体；false:分页的地方，传入的参数所代表的实体拥有Page属性
	private boolean entityOrField;
	//页面显示的底部翻页导航，详细见：getPageStr()
	//private String pageStr;		//�?��页面显示的底部翻页导航，详细见：getPageStr();
	private String sortName;
	private String sortOrder;

	private List items;

	public List getItems() {
		return items;
	}
	public void setItems(List items) {
		this.items = items;
	}

	public int getCurrentPage() {
		if(currentPage<=0)
			currentPage = 1;
		if(currentPage>getTotalPage())
			currentPage = getTotalPage();
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}


	public int getTotalPage() {
		if(totalResult%showCount==0) {
			totalPage = totalResult / showCount;
		}
		else {
			totalPage = totalResult / showCount + 1;
		}
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalResult() {
		return totalResult;
	}
	public void setTotalResult(int totalResult) {
		this.totalResult = totalResult;
	}
	public int getShowCount() {
		return showCount;
	}
	public void setShowCount(int showCount) {
		this.showCount = showCount;
	}
	public int getCurrentResult() {
		currentResult = (getCurrentPage()-1)*getShowCount();
		if(currentResult<0)
			currentResult = 0;
		return currentResult;
	}
	public void setCurrentResult(int currentResult) {
		this.currentResult = currentResult;
	}
	public boolean isEntityOrField() {
		return entityOrField;
	}
	public void setEntityOrField(boolean entityOrField) {
		this.entityOrField = entityOrField;
	}
	public String getSortName() {
		return sortName;
	}
	public void setSortName(String sortName) {
		this.sortName = sortName;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

}
