package com.zfsoft.hrm.sms.entities;

/**
 * 
 * @author ChenMinming
 * @date 2013-10-9
 * @version V1.0.0
 */
public enum SMSSendTypeEnum {
	
	SMS_WRIT_EXAM("笔试通知","SMS_WRIT_EXAM"),
	
	SMS_ORAL_EXAM("面试通知","SMS_ORAL_EXAM"),
	
	SMS_EMPLOY("录用通知","SMS_EMPLOY")
	;
	
	private String text;

	private String key;

	private SMSSendTypeEnum(String text, String key) {
		this.key = key;
		this.text = text;
	}

	/**
	 * 返回
	 */
	public String getText() {
		return text;
	}

	/**
	 * 设置
	 * @param text 
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 返回
	 */
	public String getKey() {
		return key;
	}

	/**
	 * 设置
	 * @param key 
	 */
	public void setKey(String key) {
		this.key = key;
	}

}
