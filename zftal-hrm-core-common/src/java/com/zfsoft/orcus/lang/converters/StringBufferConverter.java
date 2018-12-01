package com.zfsoft.orcus.lang.converters;

import java.util.Calendar;
import java.util.Date;

import com.zfsoft.orcus.lang.ClassUtil;
import com.zfsoft.orcus.lang.TimeUtil;

/**
 * 标准的字符串缓冲数据类型转换器
 * <p>
 * 访类将输入数据转换为字符串缓冲
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class StringBufferConverter extends AbstractConverter {

	/**
	 * （空）构造函数
	 */
	public StringBufferConverter() {
		super();
	}

	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 */
	public StringBufferConverter(Object defaultValueAsNullInput) {
		super(defaultValueAsNullInput);
	}
	
	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 * @param defaultValueAsFailure 当转换失败时的缺省返回值
	 */
	public StringBufferConverter(Object defaultValueAsNullInput, Object defaultValueAsFailure) {
		super(defaultValueAsNullInput, defaultValueAsFailure);
	}

	/**
	 * 将value转换为字符串缓冲
	 * @return 转换后的数据( 类型：java.lang.StringBuffer )
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object convert(Class type, Object value) {
		if( value == null ) {
			if( _useDefaultAsNullInput ) {
				return _defaultValueAsNullInput;
			} else {
				throw new NullInputException( "No value specified", null );
			}
		}
		
		try {
			if( value instanceof String ) {
			    return new StringBuffer( value.toString() );
			    
			} else if( value instanceof StringBuffer ) {
			    return new StringBuffer( ((StringBuffer)value).toString() );
			    
			} else if( value instanceof Date ) {
			    return new StringBuffer( TimeUtil.format( (Date) value, "yyyy-MM-dd HH:mm:ss.SSS" ) );
			    
			} else if( value instanceof Calendar ) {
			    return new StringBuffer( TimeUtil.format( (Calendar) value, "yyyy-MM-dd HH:mm:ss.SSS" ) );
			    
			} else if( value instanceof Class ) {
			    return new StringBuffer( ClassUtil.getDisplayName( (Class)value ) );
			    
			} else {
			    return new StringBuffer( value.toString() );
			}
		} catch( Throwable t ) {
			if( _useDefaultAsFailure ) {
				return _defaultValueAsFailure;
			} else {
				throw new ConversionException( "Can not convert to String:" + value, t );
			}
		}
	}

}
