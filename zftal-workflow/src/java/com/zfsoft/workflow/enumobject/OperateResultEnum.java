package com.zfsoft.workflow.enumobject;

/**
 * 
 * 类描述：工作审核节点审核状态枚举类
 * 
 * @version: 1.0
 * @author: yingjie.fan
 * @version: 2013-3-12 下午01:43:43
 */
public enum OperateResultEnum {
	SUCCESS("成功", "success"), // 成功
	FAIL("失败", "fail");// 失败

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private OperateResultEnum(String text, String key) {
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
