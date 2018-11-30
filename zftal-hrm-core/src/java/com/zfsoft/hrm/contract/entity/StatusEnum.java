package com.zfsoft.hrm.contract.entity;

/**
 * 
 * @author
 * 2014-3-3
 */
public enum StatusEnum {
	SIGNATURE("签订","SIGNATURE"),
	TERMINATE("终止","TERMINATE"),
	RELEASE("解除","RELEASE");
	
	private String text;
	private String key;
	
	private StatusEnum(String text,String key){
		this.text = text;
		this.key = key;
	}
	
	/**
	 * 展示文本
	 * @return
	 */
	public String getText(){
		return text;
	}

	/**
	 * 代码编号
	 * @return
	 */
	public String getKey() {
		return key;
	}
}
