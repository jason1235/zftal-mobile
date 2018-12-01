package com.zfsoft.orcus.lang.reflect;

/**
 * 自我描述接口，实现该接口的类必须提供描述该类型的描述信息
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public interface SelfDescribed {
	
	/**
	 * 返回JavaBean描述信息
	 */
	public BeanClass introspect();
}
