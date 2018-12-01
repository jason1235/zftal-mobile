package com.zfsoft.common.exception;

/**
 * 
 * 类名称： 自定义异常
 * 创建人：ZhenFei.Cao
 * 创建时间：2012-8-2
 */
@SuppressWarnings("serial")
public class BusinessException extends Exception {
	
	private String msg;

	public BusinessException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
