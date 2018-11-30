package com.zfsoft.workflow.enumobject;

/**
 * 
 * 类描述：工作审核节点审核状态枚举类
 * 
 * @version: 1.0
 * @author: yingjie.fan
 * @version: 2013-3-12 下午01:43:43
 */
public enum WorkNodeStatusEnum {
	INITAIL("未上报", "INITAIL"), // 初始化
	WAIT_AUDITING("待审核", "WAIT_AUDITING"), // 待审核
	IN_AUDITING("审核中", "IN_AUDITING"), // 审核中
	PASS_AUDITING("审核通过", "PASS_AUDITING"), // 审核通过
	NO_PASS_AUDITING("审核不通过", "NO_PASS_AUDITING"), // 审核不通过
	RETURN_AUDITING("退回", "RETURN_AUDITING");// 退回

	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private WorkNodeStatusEnum(String text, String key) {
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
