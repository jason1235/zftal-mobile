package com.zfsoft.hrm.normal.info.exception;

/**
 * 该类标记管理员信息管理操作异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-12
 * @version V1.0.0
 */
public class NormarInfoException extends RuntimeException {
	
	private static final long serialVersionUID = 544193244562996761L;

	public NormarInfoException() {
		super();
	}
	
	public NormarInfoException(String message) {
		super(message);
	}
	
	public NormarInfoException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public NormarInfoException(Throwable cause) {
		super(cause);
	}

}
