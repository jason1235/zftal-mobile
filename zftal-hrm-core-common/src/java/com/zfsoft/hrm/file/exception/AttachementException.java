package com.zfsoft.hrm.file.exception;

/**
 * 附件操作异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-10-19
 * @version V1.0.0
 */
public class AttachementException extends RuntimeException {

	private static final long serialVersionUID = 1464578678260985727L;
	
	public AttachementException() {
		super();
	}
	
	public AttachementException( String message ) {
		super( message );
	}
	
	public AttachementException( String message, Throwable cause ) {
		super( message, cause );
	}
	
	public AttachementException( Throwable cause ) {
		super( cause );
	}

}
