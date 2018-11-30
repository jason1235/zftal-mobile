package com.zfsoft.mobile.servlet.entity;

import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ListEntity<T> {

	public String ranking;//排序
	private boolean isOvered = true;//是否结束
	private List<T> itemList;//list列表

	public boolean isOvered() {
		return isOvered;
	}
	public void setOvered(boolean isOvered) {
		this.isOvered = isOvered;
	}
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}
	public List<T> getItemList() {
		return itemList;
	}
	public String getRanking() {
		return ranking;
	}
	public void setRanking(String ranking) {
		this.ranking = ranking;
	}
	
	
}
