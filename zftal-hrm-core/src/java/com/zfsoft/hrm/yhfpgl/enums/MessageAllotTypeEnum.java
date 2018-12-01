package com.zfsoft.hrm.yhfpgl.enums;

/**
 * 
 * @author ChenMinming
 * @date 2015-7-20
 * @version V1.0.0
 */
public enum MessageAllotTypeEnum {
	
	ROLE("ROLE","角色"),USER("USER","用户");

	private String key;
	
	private String name;

	private MessageAllotTypeEnum(String key, String name) {
		this.name = name;
		this.key = key;
	}
	public String getName() {
		return name;
	}
	public String getKey() {
		return key;
	}
}