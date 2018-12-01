package com.zfsoft.common.exception;

/**
 * 
 * 类名称： 自定义异常
 * 创建人：ZhenFei.Cao
 * 创建时间：2012-8-2
 */
public class ServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 3583566093089790852L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}
}
