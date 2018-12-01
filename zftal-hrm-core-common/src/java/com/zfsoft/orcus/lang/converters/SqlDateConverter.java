package com.zfsoft.orcus.lang.converters;

import java.sql.Date;
import java.util.Calendar;

import com.zfsoft.orcus.lang.TimeUtil;

/**
 * 标准的SQL日期型数据类型转换器
 * <p>
 * 访类将输入数据转换为java.sql.Date
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class SqlDateConverter extends AbstractConverter {

	/**
	 * （空）构造函数
	 */
	public SqlDateConverter() {
		super();
	}

	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 */
	public SqlDateConverter(Object defaultValueAsNullInput) {
		super(defaultValueAsNullInput);
	}
	
	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 * @param defaultValueAsFailure 当转换失败时的缺省返回值
	 */
	public SqlDateConverter(Object defaultValueAsNullInput, Object defaultValueAsFailure) {
		super(defaultValueAsNullInput, defaultValueAsFailure);
	}

	/**
	 * 将value转换为java.sql.Date对象
	 * @return 转换后的数据( 类型：java.sql.Date )
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
			if( value instanceof java.util.Date ) {
			    return new Date( ( (java.util.Date) value ).getTime() );
			    
			} else if( value instanceof Calendar ) {
			    return new Date( ( (Calendar) value ).getTime().getTime() );
			    
			} else if( value instanceof Number ) {
			    return new Date( ( (Number) value ).longValue() );
			    
			} else {
			    return new Date( TimeUtil.toDate( value.toString() ).getTime() );
			}
		} catch( Throwable t ) {
			if( _useDefaultAsFailure ) {
				return _defaultValueAsFailure;
			} else {
				throw new ConversionException( "Can not convert to java.sql.Date:" + value, t );
			}
		}
	}

}
