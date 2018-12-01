package com.zfsoft.workflow.enumobject;

/**
 * 
 * 类描述：节点类型枚举类
 * 
 * @version: 1.0
 * @author: yingjie.fan
 * @version: 2013-3-12 下午01:43:43
 */
public enum NodeTypeEnum {
	START_NODE("起始节点", "START_NODE"), // 起始节点
	NORMAL_NODE("正常节点", "NORMAL_NODE"), // 正常节点
	COMMIT_NODE("申报节点", "COMMIT_NODE"), // 申报节点
	BRANCH_NODE("分支节点", "BRANCH_NODE"), // 分支节点
	MERME_NODE("合并节点", "MERME_NODE"), // 合并节点
	COUNTERSIGN_NODE("会签节点", "COUNTERSIGN_NODE"), // 会签节点
	END_NODE("终止节点", "END_NODE");// 终止节点

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private NodeTypeEnum(String text, String key) {
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
