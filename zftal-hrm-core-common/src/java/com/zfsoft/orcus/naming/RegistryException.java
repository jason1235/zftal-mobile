package com.zfsoft.orcus.naming;

/**
 * 该异常标志操作注册表过程中出现的异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public class RegistryException extends Exception {

	private static final long serialVersionUID = 2640645189750969788L;
	
	public RegistryException() {
		super();
	}
	
	public RegistryException(String message) {
		super(message);
	}
	
	public RegistryException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public RegistryException(Throwable cause) {
		super(cause);
	}

}
