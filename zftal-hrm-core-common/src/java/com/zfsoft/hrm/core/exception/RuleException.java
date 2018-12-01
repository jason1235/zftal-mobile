package com.zfsoft.hrm.core.exception;

/**
 * 规则异常
 * <p>
 * 用于业务规则验证处理抛出的常规异常父类
 * </p>
 * @author jinjj
 * @date 2012-6-6 上午11:50:19 
 */
public class RuleException extends RuntimeException {

	private static final long serialVersionUID = -8440824589436222377L;
	
	/**
	 * （空）构造函数
	 */
	public RuleException() {
		super();
	}

	/**
	 * 构造函数
	 * @param message 异常信息
	 */
	public RuleException(String message) {
		super(message);
	}

	/**
	 * 构造函数
	 * @param message 异常信息
	 * @param cause 异常原因
	 */
	public RuleException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * 构造函数
	 * @param cause 异常原因
	 */
	public RuleException(Throwable cause) {
		super(cause);
	}
}
