package com.zfsoft.workflow.enumobject;

/**
 * 
 * 类描述：日志类型枚举类
 * 
 * @version: 1.0
 * @author: yingjie.fan
 * @version: 2013-3-12 下午01:43:43
 */
public enum LogTypeEnum {
	NODE_LOG("节点日志", "NODE_LOG"), // 节点日志
	TASK_LOG("任务日志", "TASK_LOG");// 任务日志

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private LogTypeEnum(String text, String key) {
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
