package com.zfsoft.hrm.baseinfo.infoclass.enums;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;

/**
 * 验证类型
 * @author Patrick Shen
 */
public enum VerifyType {
	NOMAL("正常字符",Type.COMMON,"",""),
	NOMAL_WORD("字段串",Type.COMMON,"field_123","^\\w+$"),
	//ID_CARD("身份证","^([1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3})|([1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{4})$/"),
	TELEPHONE_NUMBER("电话号码",Type.COMMON,"13333333333或1111-11111111","(^(\\d{3,4}-)?\\d{7,8})$|(1[0-9]{10})$"),
	MOBILE_TELEPHONE_NUMBER("手机号码",Type.COMMON,"13333333333或+86-11111111111","^(((\\+86)|(86))?(1)\\d{10})|(1[0-9]{10})$ "),
	EMAIL("邮箱",Type.COMMON,"ssss_s@126.com","^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$"),
	URL("网址",Type.COMMON,"www.demo.com","^(([a-zA-z]+://)*(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))(\\.(\\w+(-\\w+)*))*(\\?\\S*)?)$"),
	POSTCODE("邮政编码",Type.COMMON,"111111","^[1-9]{1}\\d{5}$"),
	ID_CARD("身份证号码",Type.COMMON,"330331198001011234","(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)"),
	POSITIVE_INTEGER("正整数",Type.NUMBER,"10","^[0-9]*[1-9][0-9]*$"),
	NOTNEGATIVE_INTEGER("非负整数",Type.NUMBER,"0","^([1-9][0-9]*)|([0-9])$"),
	POSITIVE_NUMBER("正数值",Type.NUMBER,"10.01","^(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*))$"),
	NEGATIVE_INTEGER("负整数",Type.NUMBER,"-10","^-[0-9]*[1-9][0-9]*$"),
	NEGATIVE_NUMBER("负数值",Type.NUMBER,"-10.01","^(-(([0-9]+\\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\\.[0-9]+)|([0-9]*[1-9][0-9]*)))$"),
	NOTPOSITIVE_NUMBER("非负数值",Type.NUMBER,"10.01","^(([0-9]+\\.[0-9]*[0-9][0-9]*)|([0-9]*[0-9][0-9]*\\.[0-9]+)|([0-9]*[0-9][0-9]*))$"),
	NUMBER("数值",Type.NUMBER,"0.01","^(-?(([0-9]+\\.[0-9]*[0-9][0-9]*)|([0-9]*[0-9][0-9]*\\.[0-9]+)|([0-9]*[0-9][0-9]*)))$"),;
	
	
	/**
	 * 中文描述
	 */
	private final String value;
	/**
	 * 正则表达式
	 */
	private final String formula;
	/**
	 * 验证字段类型
	 */
	private final String type;
	/**
	 * 样例
	 */
	private final String demo;

	VerifyType(String value,String type,String demo, String formula) {
		this.value = value;
		this.formula=formula;
		this.type=type;
		this.demo=demo;
	}

	public String value() {
		return value;
	}
	
	public String getDesc(){
		return value;
	}
	
	public String getType(){
		return type;
	}
	
	public String getDemo(){
		return demo;
	}
	
	public String getFormula(){
		return formula;
	}

	public static VerifyType fromValue(String v) {
		for (VerifyType c : VerifyType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
