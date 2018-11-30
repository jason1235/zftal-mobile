package com.zfsoft.orcus.mast.servlet;

/**
 * 该类标志从HTTP请求中获取请求参数过程中出现的错误
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-12
 * @version V1.0.0
 */
public class ParametersException extends Exception {
	
	private static final long serialVersionUID = 1812921192708006378L;

	public ParametersException() {
		super();
	}
	
	public ParametersException(String message) {
		super();
	}
	
	public ParametersException(String message, Throwable cause) {
		super();
	}
	
	public ParametersException(Throwable cause) {
		super();
	}

}
