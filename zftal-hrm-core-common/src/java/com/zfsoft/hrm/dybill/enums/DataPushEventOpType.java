package com.zfsoft.hrm.dybill.enums;

public enum DataPushEventOpType {
	UPDATE("更新"),
	INSERT("增加");

	private final String value;

	DataPushEventOpType(String v) {
		value = v;
	}

	public String getValue() {
		return value;
	}
	
	public static DataPushEventOpType fromValue(String v) {
		for (DataPushEventOpType c : DataPushEventOpType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
