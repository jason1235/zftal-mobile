package com.zfsoft.orcus.lang.reflect;

/**
 * 无法生成指类型的实例时抛出该异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class InitializationException extends RuntimeException {
	
	private static final long serialVersionUID = 3334926216615428004L;

	public InitializationException() {
		super();
	}
	
	public InitializationException(String message) {
		super(message);
	}
	
	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public InitializationException(Throwable cause) {
		super(cause);
	}
}
