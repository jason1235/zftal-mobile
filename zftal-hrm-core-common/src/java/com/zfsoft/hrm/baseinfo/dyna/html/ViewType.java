package com.zfsoft.hrm.baseinfo.dyna.html;

/**
 * 
 * @author ChenMinming
 * @date 2013-9-9
 * @version V1.0.0
 */
public enum ViewType {
	DEFAULT("DEFAULT","默认样式"),
	SELECT("SELECT","下拉菜单"),
	CHOOSE("CHOOSE","单选框");
	/**
	 * 类型
	 */
	private String key;
	/**
	 * 描述
	 */
	private String text;
	private ViewType(String key,String text){
		this.key = key;
		this.text = text;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getText() {
		return text;
	}

}
