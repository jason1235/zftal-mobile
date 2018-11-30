package com.zfsoft.fifa.identity;

/**
 * 参数格式不符合约定时抛出异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-15
 * @version V1.0.0
 */
public class WrongFormatException extends RuntimeException {

	private static final long serialVersionUID = -8042795894064156728L;

	public WrongFormatException() {
		super();
	}
	
	public WrongFormatException(String message) {
		super(message);
	}
	
	public WrongFormatException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public WrongFormatException(Throwable cause) {
		super(cause);
	}
}
