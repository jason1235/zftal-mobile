package com.zfsoft.hrm.baseinfo.search.exception;

/**
 * 标记查询定义操作异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-13
 * @version V1.0.0
 */
public class SearchException extends RuntimeException {
	
	private static final long serialVersionUID = 727186479783284640L;

	public SearchException() {
		super();
	}
	
	public SearchException(String message) {
		super(message);	
	}
	
	public SearchException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public SearchException(Throwable cause) {
		super(cause);
	}

}
