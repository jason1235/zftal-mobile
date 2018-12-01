package com.zfsoft.hrm.dybill.enums;

public enum DataPushEventType {
	PUSH_TO_INFOCLASS("推向信息类"),
	PUSH_TO_LOCAL("推向本地库");

	private final String value;

	DataPushEventType(String v) {
		value = v;
	}

	public String getValue() {
		return value;
	}
	
	public static DataPushEventType fromValue(String v) {
		for (DataPushEventType c : DataPushEventType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
