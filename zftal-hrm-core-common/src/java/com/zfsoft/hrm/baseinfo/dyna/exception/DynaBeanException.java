package com.zfsoft.hrm.baseinfo.dyna.exception;

/** 
 * 标记动态类处理异常
 * @ClassName: DynaBeanException 
 * @author jinjj
 * @date 2012-6-20 上午11:09:36 
 *  
 */
public class DynaBeanException extends RuntimeException {

	private static final long serialVersionUID = -2358819099146411268L;

	public DynaBeanException() {
		super();
	}
	
	public DynaBeanException( String message ) {
		super( message );
	}
	
	public DynaBeanException( String message, Throwable cause ) {
		super( message, cause );
	}
	
	public DynaBeanException( Throwable cause ) {
		super( cause );
	}
}
