package com.zfsoft.orcus.lang.reflect;

/**
 * 解析JavaBean类型时出错则抛出该异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class BeanIntrospectionException extends Exception {
	
	private static final long serialVersionUID = -1642851767753357932L;

	public BeanIntrospectionException() {
		super();
	}
	
	public BeanIntrospectionException(String message) {
		super(message);
	}
	
	public BeanIntrospectionException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BeanIntrospectionException(Throwable cause) {
		super(cause);
	}
}
