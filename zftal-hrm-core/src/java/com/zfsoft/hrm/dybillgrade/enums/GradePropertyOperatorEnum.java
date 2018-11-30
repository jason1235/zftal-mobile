package com.zfsoft.hrm.dybillgrade.enums;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-10
 * @version V1.0.0
 */
public enum GradePropertyOperatorEnum {
	EQ("=","EQ"),
	NOT_EQ("!=","NOT_EQ"),
	XY("<","XY"),
	XY_EQ("<=","XY_EQ"),
	DY(">","DY"),
	DY_EQ(">=","DY_EQ"),
	LIKE("包含","LIKE"),
	NOT_LIKE("不包含","NOT_LIKE");
	
	private final String key;
	private final String text;
	private GradePropertyOperatorEnum(String text, String key) {
		this.text = text;
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public String getText() {
		return text;
	}
	
	public static GradePropertyOperatorEnum getGradePropertyOperatorEnum(String key){
		try {
			return valueOf(key);
		} catch (Exception e) {
			return EQ;
		}
	}
}
