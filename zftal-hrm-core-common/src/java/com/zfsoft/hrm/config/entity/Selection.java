package com.zfsoft.hrm.config.entity;

import java.io.Serializable;

/** 
 * @author jinjj
 * @date 2013-1-22 下午12:00:32 
 *  
 */
public class Selection implements Serializable{

	private static final long serialVersionUID = -3712469576223041674L;

	private String value;
	
	private String name;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
