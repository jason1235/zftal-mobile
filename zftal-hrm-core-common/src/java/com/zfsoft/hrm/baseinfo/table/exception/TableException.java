package com.zfsoft.hrm.baseinfo.table.exception;

/**
 * 数据库表格操作出现异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-6
 * @version V1.0.0
 */
public class TableException extends RuntimeException {

	private static final long serialVersionUID = -2077350927190360895L;

	public TableException() {
		super();
	}
	
	public TableException(String message) {
		super(message);
	}
	
	public TableException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TableException(Throwable cause) {
		super(cause);
	}
}
