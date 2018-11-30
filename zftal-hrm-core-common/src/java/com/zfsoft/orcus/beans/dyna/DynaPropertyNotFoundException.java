package com.zfsoft.orcus.beans.dyna;

/**
 * 当用户试图访问不存在的动态JavaBean属性时抛出该异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class DynaPropertyNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -1133754766929852129L;

	public DynaPropertyNotFoundException() {
		super();
	}
	
	public DynaPropertyNotFoundException(String message) {
		super(message);
	}
	
	public DynaPropertyNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DynaPropertyNotFoundException(Throwable cause) {
		super(cause);
	}
}
