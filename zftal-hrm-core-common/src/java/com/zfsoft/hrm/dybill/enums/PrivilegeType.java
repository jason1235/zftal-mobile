package com.zfsoft.hrm.dybill.enums;

/**
 * 表单权限
 * @author Patrick Shen
 */
public enum PrivilegeType {
	SEARCH("查询",1),
	SEARCH_EDIT("查询-编辑",2),
	SEARCH_ADD_DELETE("查询-增加-删除",3),
	SEARCH_ADD_DELETE_EDIT("查询-增加-删除-编辑",4);

	private final String value;
	
	private final int index;

	PrivilegeType(String value,int index) {
		this.value = value;
		this.index=index;
	}

	public String value() {
		return value;
	}
	
	public String getDesc(){
		return value;
	}
	
	public Integer getIndex(){
		return index;
	}

	public static PrivilegeType fromValue(String v) {
		for (PrivilegeType c : PrivilegeType.values()) {
			if (c.value.equals(v)) {
				return c;
			}
		}
		throw new IllegalArgumentException(v);
	}
}
