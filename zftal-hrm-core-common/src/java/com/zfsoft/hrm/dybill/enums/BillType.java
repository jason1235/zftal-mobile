package com.zfsoft.hrm.dybill.enums;

/**
 * 实体类型
 * @author Patrick Shen
 */
public enum BillType {
	
	COMMIT("上报"),
	APPROVE("审批");

	private final String value;

	BillType(String v) {
		value = v;
	}

	public String value() {
		return value;
	}
	
	public String getDesc(){
		return value;
	}

	public static BillType fromValue(String v) {
		for (BillType c : BillType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
