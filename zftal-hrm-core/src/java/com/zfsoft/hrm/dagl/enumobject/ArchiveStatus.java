package com.zfsoft.hrm.dagl.enumobject;

/**
 * 
 * @author ChenMinming
 * @date 2014-10-22
 * @version V1.0.0
 */
public enum ArchiveStatus {
	SAVE("存档","SAVE"),
	OUT("调出","OUT"),
	DISABLE("销毁","DISABLE");
	/**
	 * 定义枚举类型自己的属性
	 */
	private final String text;
	private final String key;

	private ArchiveStatus(String text, String key) {
		this.text = text;
		this.key = key;
	}

	/**
	 * 返回
	 */
	public String getText() {
		return text;
	}

	/**
	 * 返回
	 */
	public String getKey() {
		return key;
	}
	
	
}
