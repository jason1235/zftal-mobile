package com.zfsoft.hrm.baseinfo.infoclass.exception;

/**
 * 信息类操作异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-5
 * @version V1.0.0
 */
public class InfoClassException extends RuntimeException {

	private static final long serialVersionUID = -8440824589436222377L;
	
	public InfoClassException() {
		super();
	}

	public InfoClassException(String message) {
		super(message);
	}

	public InfoClassException(String message, Throwable cause) {
		super(message, cause);
	}

	public InfoClassException(Throwable cause) {
		super(cause);
	}
}
