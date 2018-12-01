package com.zfsoft.orcus.lang.reflect;

/**
 * 设置属性的属性值出错则抛出该异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class WritePropertyException extends ReflectException {

	private static final long serialVersionUID = -9023715207188082788L;

	public WritePropertyException() {
		super();
	}
	
	public WritePropertyException(String message) {
		super(message);
	}
	
	public WritePropertyException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public WritePropertyException(Throwable cause) {
		super(cause);
	}
}
