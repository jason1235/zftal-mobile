package com.zfsoft.mobile.reportFix.entity;

public enum StatusType {

	/*
	 * 报修状态枚举
	 */
	
	daibaoxiu("待保修","0"),
	yibaoxiu("已保修","2"),
	yipingjia("已评价","3")
	;
	
	// 成员变量
	private String name;
	private String index;
	
	// 构造方法
	private StatusType(String name, String index) {
		this.name = name;
		this.index = index;
	}
	
	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}
	
}
