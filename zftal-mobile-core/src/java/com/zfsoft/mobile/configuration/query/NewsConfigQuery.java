package com.zfsoft.mobile.configuration.query;

import com.zfsoft.dao.query.BaseQuery;

public class NewsConfigQuery extends BaseQuery {
	private String key; // sk
	private String value; // sv
	private String comment; // sc
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
