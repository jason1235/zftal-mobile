package com.zfsoft.orcus.lang.reflect;

/**
 * 该异常标志使用反射机制过程中出现的错误
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class ReflectException extends Exception {

	private static final long serialVersionUID = 276104288561582216L;

	public ReflectException() {
		super();
	}
	
	public ReflectException(String message) {
		super(message);	
	}
	
	public ReflectException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ReflectException(Throwable cause) {
		super(cause);
	}
}
