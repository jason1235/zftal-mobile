package com.zfsoft.mobile.exampaper.entity;

/**
 * 问卷问题实体类
 * @author liucb
 */
public class ExampaperQes {

	private String questionId; //问题id
	private String title; //问题名称
	private Integer type; //问题类型
	private String items; //问题选项
	private Integer sort; //问题顺序
	private String paperMainId; //问卷id
	private Integer maxItem; //最多选几项
	
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getItems() {
		return items;
	}
	public void setItems(String items) {
		this.items = items;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getPaperMainId() {
		return paperMainId;
	}
	public void setPaperMainId(String paperMainId) {
		this.paperMainId = paperMainId;
	}
	public Integer getMaxItem() {
		return maxItem;
	}
	public void setMaxItem(Integer maxItem) {
		this.maxItem = maxItem;
	}
	
}
