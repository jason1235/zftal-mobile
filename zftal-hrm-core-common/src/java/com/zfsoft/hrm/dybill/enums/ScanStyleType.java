package com.zfsoft.hrm.dybill.enums;

/**
 * 实体类型
 * @author Patrick Shen
 */
public enum ScanStyleType {
	
	TILE("平铺"),
	LIST("列表");

	private final String value;

	ScanStyleType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}
	
	public String getDesc(){
		return value;
	}

	public static ScanStyleType fromValue(String v) {
		for (ScanStyleType c : ScanStyleType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
