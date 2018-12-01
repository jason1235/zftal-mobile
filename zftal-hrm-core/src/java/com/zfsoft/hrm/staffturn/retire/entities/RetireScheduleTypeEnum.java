package com.zfsoft.hrm.staffturn.retire.entities;
/**
 * 预退休检测的周期
 * 
 * @author gonghui
 * 2013-10-11
 */
public enum RetireScheduleTypeEnum {

	MONTH("每个月"),SEASON("每季度"),HALFYEAR("每半年"),YEAR("每年");
	
	private String name;

	private RetireScheduleTypeEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
