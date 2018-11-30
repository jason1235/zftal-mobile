package com.zfsoft.hrm.config;

import java.util.Properties;

/**
 * {@link Type }的通用实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-28
 * @version V1.0.0
 */
public class GeneralType implements Type {

	private static final long serialVersionUID = 780935394932963320L;
	
	protected String name;			//类型名字
	
	protected String text;			//类型文本
	
	protected Properties appendix = new Properties();	//附加属性

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public Properties getAppendix() {
		return appendix;
	}

	@Override
	public void setAppendix(Properties appendix) {
		this.appendix = appendix;
	}

	/**
	 * 如果 obj == null， 
	 */
	@Override
	public boolean equals(Object obj) {
		if( obj == null || getName() == null ) {
			return false;
		}
		
		if( obj instanceof String && getName().equals( obj ) ) {
			return true;
		}
		
		if( obj instanceof Type && getName().equals( ((Type)obj).getName() ) ) {
			return true;
		}
		
		return false;
	}
}
