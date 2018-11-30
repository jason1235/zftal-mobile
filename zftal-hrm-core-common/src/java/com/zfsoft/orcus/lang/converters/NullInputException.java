package com.zfsoft.orcus.lang.converters;

/**
 * 该异常标志数据类型转变时接收到的被转换数据为null，该异常属于运行期异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class NullInputException extends RuntimeException {
	
	private static final long serialVersionUID = -3404292269398424711L;

	public NullInputException() {
		super();
	}
	
	public NullInputException( String message ) {
		super(message);
	}
	
	public NullInputException( String message, Throwable cause ) {
		super(message, cause);
	}
	
	public NullInputException( Throwable cause ) {
		super(cause);
	}

}
