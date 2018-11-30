package com.zfsoft.orcus.naming;

/**
 * 注册处理失败
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-18
 * @version V1.0.0
 */
public class BindingFailedException  extends RegistryException {

	private static final long serialVersionUID = 6350475062883415751L;

	public BindingFailedException() {
		super();
	}
	
	public BindingFailedException(String message) {
		super(message);	
	}
	
	public BindingFailedException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BindingFailedException(Throwable cause) {
		super(cause);
	}
}
