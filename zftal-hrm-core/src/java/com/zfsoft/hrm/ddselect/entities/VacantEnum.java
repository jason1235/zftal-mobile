package com.zfsoft.hrm.ddselect.entities;

/**
 * 
 * @author yxlong
 * 2013-8-2
 */
public enum VacantEnum {
	Y("是", "Y"), // 是
	N("否", "N");// 否

/**
 * 定义枚举类型自己的属性
 */
private final String text;
private final String key;

private VacantEnum(String text, String key) {
	this.text = text;
	this.key = key;
}

/**
 * 展示文本
 * 
 * @return
 */
public String getText() {
	return text;
}

/**
 * 代码编号
 * 
 * @return
 */
public String getKey() {
	return key;
}
}
