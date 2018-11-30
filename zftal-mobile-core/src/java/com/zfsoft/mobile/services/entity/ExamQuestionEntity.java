package com.zfsoft.mobile.services.entity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.query.BaseQuery;

public class ExamQuestionEntity  extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1518004054576078884L;

	private String questionid;//题id
	
	private String title;//题目内容
	
	private String type;//--0单选,1多选,2,填空,3打分
	
	private String items;//--当type=2时不读此字段.type={0,1,3}时读此字段为选项,比如aaaa,bbbb,cccc这样存放三个选项
	
	private String papermainid;//所属问卷主表id
	
	private String sort;//排序码
	
	private String maxItem;//最多可选择项的数量
	
	private List<String> item = new ArrayList<String>();
	
	private List<Map<String, String>> itemValue = new ArrayList<Map<String, String>>();
	
	private int totalpeople;

	public String getQuestionid() {
		return questionid;
	}

	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		String[] itemString = items.split("\r\n");
		item = Arrays.asList(itemString);
		this.items = items;
	}

	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setPapermainid(String papermainid) {
		this.papermainid = papermainid;
	}

	public String getPapermainid() {
		return papermainid;
	}

	public void setItem(List<String> item) {
		this.item = item;
	}

	public List<String> getItem() {
		return item;
	}

	public void setItemValue(List<Map<String, String>> itemValue) {
		this.itemValue = itemValue;
	}

	public List<Map<String, String>> getItemValue() {
		return itemValue;
	}

	public void setTotalpeople(int totalpeople) {
		this.totalpeople = totalpeople;
	}

	public int getTotalpeople() {
		return totalpeople;
	}

	public void setMaxItem(String maxItem) {
		this.maxItem = maxItem;
	}

	public String getMaxItem() {
		return maxItem;
	}

	
	
}
