package com.zfsoft.orcus.lang.reflect;

/**
 * 读取属性的属性值出错则抛出该异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class ReadPropertyException extends ReflectException {

	private static final long serialVersionUID = -4357003620034740657L;

	public ReadPropertyException() {
		super();
	}
	
	public ReadPropertyException(String message) {
		super(message);
	}
	
	public ReadPropertyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ReadPropertyException(Throwable cause) {
		super(cause);
	}
}
