package com.zfsoft.hrm.seniorreport.enums;

/**
 * 
 * @author ChenMinming
 * @date 2014-1-24
 * @version V1.0.0
 */
public enum SeniorReportType {
	RSK_TYPE("人事科","RSK_TYPE"),
	LZK_TYPE("劳资科","LZK_TYPE"),
	OTHER_TYPE("其他","OTHER_TYPE");

	private String text;
	private String key;
	private SeniorReportType(String text, String key){
		this.text = text;
		this.key = key;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
}
