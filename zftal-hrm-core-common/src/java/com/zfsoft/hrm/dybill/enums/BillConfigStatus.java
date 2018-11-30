package com.zfsoft.hrm.dybill.enums;

/**
 * 表单配置状态
 * @author Patrick Shen
 */
public enum BillConfigStatus {
	INITIALIZE("初始化"),
	USING("使用中"),
	UNUSE("未使用");

	private final String value;

	BillConfigStatus(String v) {
		value = v;
	}

	public String value() {
		return value;
	}
	
	public String getDesc(){
		return value;
	}

	public static BillConfigStatus fromValue(String v) {
		for (BillConfigStatus c : BillConfigStatus.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
