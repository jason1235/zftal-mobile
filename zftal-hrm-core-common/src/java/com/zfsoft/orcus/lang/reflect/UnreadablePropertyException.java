package com.zfsoft.orcus.lang.reflect;

/**
 * 该异常标志程序试图读取非可读属性的属性值
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class UnreadablePropertyException extends ReflectException {

	private static final long serialVersionUID = 1631830910419920804L;

	public UnreadablePropertyException() {
		super();
	}
	
	public UnreadablePropertyException(String message) {
		super(message);
	}
		
	public UnreadablePropertyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UnreadablePropertyException(Throwable cause) {
		super(cause);
	}
}
