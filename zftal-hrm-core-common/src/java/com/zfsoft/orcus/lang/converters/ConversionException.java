package com.zfsoft.orcus.lang.converters;

/**
 * 该异常标志数据类型转变过程中发生的错误，该异常属于运行期异常
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class ConversionException extends RuntimeException {
	
	private static final long serialVersionUID = 6384028527742145566L;

	public ConversionException() {
		super();
	}
	
	public ConversionException( String message ) {
		super(message);
	}
	
	public ConversionException( String message, Throwable cause ) {
		super(message, cause);
	}
	
	public ConversionException( Throwable cause ) {
		super(cause);
	}

}
