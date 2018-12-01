package com.zfsoft.hrm.baseinfo.struct.exception;

/**
 * 标记数据字典业务处理异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-2
 * @version V1.0.0
 */
public class StructException extends RuntimeException {
	
	private static final long serialVersionUID = 1630479137617058454L;

	public StructException() {
		super();
	}
	
	public StructException( String message ) {
		super( message );
	}
	
	public StructException( String message, Throwable cause ) {
		super( message, cause );
	}
	
	public StructException( Throwable cause ) {
		super( cause );
	}

}
