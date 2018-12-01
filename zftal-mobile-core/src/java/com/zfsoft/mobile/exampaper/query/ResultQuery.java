package com.zfsoft.mobile.exampaper.query;

import com.zfsoft.dao.query.BaseQuery;


/**
 * 问卷答案实体
 * @author liucb
 */
public class ResultQuery extends BaseQuery{

	private static final long serialVersionUID = 1L;
	private String yhid;
	private String questionId;
	private String itemValue;
	
	public String getYhid() {
		return yhid;
	}
	public void setYhid(String yhid) {
		this.yhid = yhid;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getItemValue() {
		return itemValue;
	}
	public void setItemValue(String itemValue) {
		this.itemValue = itemValue;
	}
}
