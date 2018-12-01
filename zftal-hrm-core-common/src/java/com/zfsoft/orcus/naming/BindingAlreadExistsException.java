package com.zfsoft.orcus.naming;

/**
 * 绑定存在异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public class BindingAlreadExistsException extends RegistryException {

	private static final long serialVersionUID = 8926846629918035496L;

	public BindingAlreadExistsException() {
		super();
	}
	
	public BindingAlreadExistsException(String message) {
		super(message);
	}
	
	public BindingAlreadExistsException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BindingAlreadExistsException(Throwable cause) {
		super(cause);
	}
	
}
