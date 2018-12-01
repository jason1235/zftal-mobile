package com.zfsoft.hrm.tools.web.exception;

/**
 * 标记人员选择异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-10
 * @version V1.0.0
 */
public class SelectPersonException extends RuntimeException {

	private static final long serialVersionUID = -2914459239157664862L;

	/**
	 * {@link SelectPersonException}（空）构造函数
	 */
	public SelectPersonException() {
		super();
	}
	
	/**
	 * {@link SelectPersonException}构造函数
	 * @param message 异常消息
	 */
	public SelectPersonException( String message ) {
		super( message );
	}
	
	/**
	 * {@link SelectPersonException}构造函数
	 * @param message 异常信息
	 * @param cause 导致异常原因
	 */
	public SelectPersonException( String message, Throwable cause ) {
		super( message, cause );
	}
	
	/**
	 * {@link SelectPersonException}构造函数
	 * @param cause 导致异常原因
	 */
	public SelectPersonException( Throwable cause ) {
		super( cause );
	}
}
