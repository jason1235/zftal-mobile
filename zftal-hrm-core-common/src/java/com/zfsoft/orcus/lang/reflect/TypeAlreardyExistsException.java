package com.zfsoft.orcus.lang.reflect;

/**
 * 注册类型描述信息时如果类型描述已存在则需要抛出该异常。
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-11
 * @version V1.0.0
 */
public class TypeAlreardyExistsException extends RuntimeException {

	private static final long serialVersionUID = -6028037497758561903L;

	public TypeAlreardyExistsException() {
		super();
	}
	
	public TypeAlreardyExistsException(String message) {
		super(message);
	}
	
	public TypeAlreardyExistsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TypeAlreardyExistsException(Throwable cause) {
		super(cause);
	}
}
