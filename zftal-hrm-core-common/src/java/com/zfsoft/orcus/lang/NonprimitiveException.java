package com.zfsoft.orcus.lang;

/**
 * 当指定类型不是原始类型则可以抛出该异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class NonprimitiveException extends RuntimeException {
	
	private static final long serialVersionUID = 6384465951790319631L;

	public NonprimitiveException() {
		super();
	}
	
	public NonprimitiveException(String message) {
		super(message);
	}
	
	public NonprimitiveException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NonprimitiveException(Throwable cause) {
		super(cause);
	}

}
