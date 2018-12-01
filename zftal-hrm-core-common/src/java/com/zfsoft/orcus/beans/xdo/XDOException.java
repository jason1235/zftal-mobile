package com.zfsoft.orcus.beans.xdo;

/**
 * 该类代表持久化处理过程中出现的异常
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-10
 * @version V1.0.0
 */
public class XDOException extends RuntimeException {
	
	private static final long serialVersionUID = 1054072771458004723L;

	/**
	 * {@link XDOException}（空）构造函数
	 */
	public XDOException() {
		super();
	}

	/**
	 * {@link XDOException}构造函数
	 * @param message 异常信息
	 */
	public XDOException(String message) {
		super(message);
	}
	
	/**
	 * {@link XDOException}构造函数
	 * @param message 异常信息
	 * @param cause 异常原因
	 */
	public XDOException(String message, Throwable cause) {
		super(message, cause);
	}


	/**
	 * {@link XDOException}构造函数
	 * @param cause 异常原因
	 */
	public XDOException(Throwable cause) {
		super(cause);
	}
}
