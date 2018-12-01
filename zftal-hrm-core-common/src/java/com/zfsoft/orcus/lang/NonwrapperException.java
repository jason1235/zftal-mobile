package com.zfsoft.orcus.lang;

/**
 * 当指定类型不是包覆类则可以抛出该异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class NonwrapperException extends RuntimeException {
	
	private static final long serialVersionUID = -6393168276529250889L;

	public NonwrapperException() {
		super();
	}
	
	public NonwrapperException(String message) {
		super(message);
	}
	
	public NonwrapperException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NonwrapperException(Throwable cause) {
		super(cause);
	}
}
