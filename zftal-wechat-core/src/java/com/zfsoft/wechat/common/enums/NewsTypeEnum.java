package com.zfsoft.wechat.common.enums;

/**
 * 
 * @author ChenMinming
 * @date 2015-7-28
 * @version V1.0.0
 */
public enum NewsTypeEnum {
	TEXT("text","文本"),VOICE("voice","语音"),MPNEWS("mpnews","图文"),IMAGE("image","图片");
	private String key;
	private String name;
	private NewsTypeEnum(String key,String name){
		this.key=key;
		this.name=name;
	}
	/**
	 * 返回
	 */
	public String getKey() {
		return key;
	}
	/**
	 * 返回
	 */
	public String getName() {
		return name;
	}
	
}
