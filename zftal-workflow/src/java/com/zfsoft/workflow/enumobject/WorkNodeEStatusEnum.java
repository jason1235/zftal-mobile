package com.zfsoft.workflow.enumobject;

/**
 * 
 * 类描述：工作审核节点执行状态枚举类
 * 
 * @version: 1.0
 * @author: yingjie.fan
 * @version: 2013-3-12 下午01:43:43
 */
public enum WorkNodeEStatusEnum {
	CLOSE("关闭", "CLOSE"), // 关闭
	WAIT_EXECUTE("待执行", "WAIT_EXECUTE"), // 待执行
	ALREADY_EXECUTE("已执行", "ALREADY_EXECUTE");// 已执行

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private WorkNodeEStatusEnum(String text, String key) {
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
