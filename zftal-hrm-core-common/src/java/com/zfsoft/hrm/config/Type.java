package com.zfsoft.hrm.config;

import java.io.Serializable;
import java.util.Properties;

/**
 * 类型描述信息接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-28
 * @version V1.0.0
 */
public interface Type extends Serializable {
	
	/**
	 * 返回类型名字
	 */
	public String getName();

	/**
	 * 设置类型名字
	 * @param name 类型名字
	 */
	public void setName( String name );

	/**
	 * 返回类型文本
	 */
	public String getText();

	/**
	 * 设置类型文本
	 * @param text 类型文本
	 */
	public void setText( String text );
	
	/**
	 * 返回附加属性(never null)
	 */
	public Properties getAppendix();

	/**
	 * 设置附加属性，以备不时之需
	 * @param appendix 附加属性
	 */
	public void setAppendix( Properties appendix );
}
