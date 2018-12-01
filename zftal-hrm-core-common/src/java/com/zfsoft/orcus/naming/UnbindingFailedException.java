package com.zfsoft.orcus.naming;

/**
 * 注销处理失败
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public class UnbindingFailedException extends RegistryException {

	private static final long serialVersionUID = 2964755249328101363L;

	public UnbindingFailedException() {
		super();
	}
	
	public UnbindingFailedException(String message) {
		super(message);
	}
	
	public UnbindingFailedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UnbindingFailedException(Throwable cause) {
		super(cause);
	}
}
