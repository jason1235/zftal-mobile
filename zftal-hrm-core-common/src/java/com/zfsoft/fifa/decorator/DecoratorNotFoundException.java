package com.zfsoft.fifa.decorator;

/**
 * Statement元数据中定义的Decorator无法加载异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class DecoratorNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -1329551690491048324L;

	public DecoratorNotFoundException() {
		super();
	}
	
	public DecoratorNotFoundException(String message) {
		super(message);
	}
	
	public DecoratorNotFoundException(String meesage, Throwable cause) {
		super(meesage, cause);
	}
	
	public DecoratorNotFoundException(Throwable cause) {
		super(cause);
	}

}
