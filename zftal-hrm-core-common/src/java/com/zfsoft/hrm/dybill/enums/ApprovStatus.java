package com.zfsoft.hrm.dybill.enums;
/**
 *  条目审批状态
 *  @author Patrick Shen
 */
public enum ApprovStatus {
	UNDEAL("未处理"),
	PASS("通过"),
	REFUSE("不通过");

	private final String value;
	
	ApprovStatus(String v) {
		value = v;
	}

	public String value() {
		return value;
	}
	
	public String getDesc(){
		return value;
	}

	public static ApprovStatus fromValue(String v) {
		for (ApprovStatus c : ApprovStatus.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
