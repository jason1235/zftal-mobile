package com.zfsoft.hrm.baseinfo.finfo.exception;

/**
 * 标记信息维护异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public class FormInfoException extends RuntimeException {

	private static final long serialVersionUID = -6023781654826106136L;

	public FormInfoException() {
		super();
	}
	
	public FormInfoException( String message ) {
		super( message );
	}
	
	public FormInfoException( String message, Throwable cause ) {
		super( message, cause );
	}
	
	public FormInfoException( Throwable cause ) {
		super( cause );
	}
}
