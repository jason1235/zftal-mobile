package com.zfsoft.workflow.enumobject;

/**
 * 
 * 类描述：状态枚举类
 * 
 * @version: 1.0
 * @author: yingjie.fan
 * @version: 2013-3-12 下午01:43:43
 */
public enum StatusEnum {
	VALID_STATUS("有效状态", "1"), // 有效状态
	INVALID_STATUS("无效状态", "0");// 无效状态

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private StatusEnum(String text, String key) {
		this.text = text;
		this.key = key;
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
