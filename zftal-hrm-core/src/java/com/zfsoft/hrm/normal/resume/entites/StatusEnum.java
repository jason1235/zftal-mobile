package com.zfsoft.hrm.normal.resume.entites;

public enum StatusEnum {

	COMPLETE("已处理", "COMPLETE"), // 已处理
	UNCOMPLETE("未处理", "UNCOMPLETE"); // 未处理

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private StatusEnum(String text, String key) {
		this.text = text;
		this.key = key;
	}

	public boolean isComplete() {
		return this == COMPLETE;
	}

	/**
	 * 展示文本
	 * 
	 * @return
	 */
	public String getText() {
		return text;
	}

	/**
	 * 代码编号
	 * 
	 * @return
	 */
	public String getKey() {
		return key;
	}

}
