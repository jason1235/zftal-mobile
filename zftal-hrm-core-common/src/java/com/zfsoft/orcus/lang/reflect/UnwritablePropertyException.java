package com.zfsoft.orcus.lang.reflect;

/**
 * 该异常标志程序试图设置非可写属性的属性值
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class UnwritablePropertyException extends ReflectException {

	private static final long serialVersionUID = 1274197492633369896L;
	
	public UnwritablePropertyException() {
		super();
	}
	
	public UnwritablePropertyException(String message) {
		super(message);
	}
	
	public UnwritablePropertyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public UnwritablePropertyException(Throwable cause) {
		super(cause);
	}

}
