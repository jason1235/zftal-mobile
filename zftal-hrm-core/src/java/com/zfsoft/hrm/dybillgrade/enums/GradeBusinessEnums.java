package com.zfsoft.hrm.dybillgrade.enums;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-4
 * @version V1.0.0
 */
public enum GradeBusinessEnums {
	ZWPR("职务聘任","ZWPR"),
	NDKH("年度考核","NDKH"),
	BSHCZKH("博士后出站考核","BSHCZKH");
	private final String key;
	private final String text;
	private GradeBusinessEnums(String text, String key) {
		this.text = text;
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public String getText() {
		return text;
	}
	

}
