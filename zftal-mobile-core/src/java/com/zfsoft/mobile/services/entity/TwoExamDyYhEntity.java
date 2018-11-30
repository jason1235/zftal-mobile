package com.zfsoft.mobile.services.entity;

import com.zfsoft.dao.query.BaseQuery;

public class TwoExamDyYhEntity extends BaseQuery{

	/**
	 * 
	 */
	private static final long serialVersionUID = -174832450541044672L;
	
	private String yhid;//用户id
	private String questionid;//题型id
	private String itemvalue;//选项值
	public String getYhid() {
		return yhid;
	}
	public void setYhid(String yhid) {
		this.yhid = yhid;
	}
	public String getQuestionid() {
		return questionid;
	}
	public void setQuestionid(String questionid) {
		this.questionid = questionid;
	}
	public String getItemvalue() {
		return itemvalue;
	}
	public void setItemvalue(String itemvalue) {
		this.itemvalue = itemvalue;
	}
	

}
