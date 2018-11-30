package com.zfsoft.hrm.dybill.enums;

/**
 * 实体类型
 * @author Patrick Shen
 */
public enum EntityType {
	
	SELFADD("自增"),
	INFOCLASS("信息类");

	private final String value;

	EntityType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}
	
	public String getDesc(){
		return value;
	}

	public static EntityType fromValue(String v) {
		for (EntityType c : EntityType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
