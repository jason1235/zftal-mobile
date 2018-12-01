package com.zfsoft.mobile.pushmsg.enums;

/**
 * 
 * @author ChenMinming
 * @date 2015-4-24
 * @version V1.0.0
 */
public enum PushObjectTypeEnum {
	ALL("ALL","广播（所有人）"),
	ALL_USER("ALL_USER","系统中所有用户"),
	ALL_NOT_USER("ALL_NOT_USER","所有不在系统中的用户"),
	TAG("TAG","分组"),
	ALIAS("ALIAS","特定人"),
	REGISTRATION_ID("REGISTRATION_ID","REGISTRATION ID");
	private String key;
	private String text;
	private PushObjectTypeEnum(String key,String text){
		this.key=key;
		this.text=text;
	}
	/**
	 * 返回
	 */
	public String getKey() {
		return key;
	}
	/**
	 * 返回
	 */
	public String getText() {
		return text;
	}
}
