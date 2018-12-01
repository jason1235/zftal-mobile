package com.zfsoft.hrm.dybill.enums;

/**
 * 表单实例状态
 * @author Patrick Shen
 */
public enum BillInstanceStatus {
	INITIALIZE("初始化"),
	COMMITED("已提交");

	private final String value;

	BillInstanceStatus(String v) {
		value = v;
	}

	public String value() {
		return value;
	}
	
	public String getDesc(){
		return value;
	}

	public static BillInstanceStatus fromValue(String v) {
		for (BillInstanceStatus c : BillInstanceStatus.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
