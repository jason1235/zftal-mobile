package com.zfsoft.hrm.dybill.enums;

/**
 * 表单模式
 * @author Patrick Shen
 */
public enum ModeType {
	NORMAL("普通模式"),
	SEARCH("查询模式"),
	ALL_SEARCH("查询所有模式");

	private final String value;

	ModeType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}
	
	public String getDesc(){
		return value;
	}

	public static ModeType fromValue(String v) {
		for (ModeType c : ModeType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
