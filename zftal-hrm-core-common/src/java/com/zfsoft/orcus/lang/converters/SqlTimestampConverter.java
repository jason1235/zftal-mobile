package com.zfsoft.orcus.lang.converters;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import com.zfsoft.orcus.lang.TimeUtil;

/**
 * 标准的SQL时间标记型数据类型转换器
 * <p>
 * 访类将输入数据转换为java.sql.Timestamp
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class SqlTimestampConverter extends AbstractConverter {

	/**
	 * （空）构造函数
	 */
	public SqlTimestampConverter() {
		super();
	}

	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 */
	public SqlTimestampConverter(Object defaultValueAsNullInput) {
		super(defaultValueAsNullInput);
	}
	
	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 * @param defaultValueAsFailure 当转换失败时的缺省返回值
	 */
	public SqlTimestampConverter(Object defaultValueAsNullInput, Object defaultValueAsFailure) {
		super(defaultValueAsNullInput, defaultValueAsFailure);
	}

	/**
	 * 将value转换为java.sql.Timestamp对象
	 * @return 转换后的数据( 类型：java.sql.Timestamp )
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
			if( value instanceof Date ) {
			    return new Timestamp( ( (Date) value ).getTime() );
			    
			} else if( value instanceof Calendar ) {
			    return new Timestamp( ( (Calendar) value ).getTime().getTime() );
			    
			} else if( value instanceof Number ) {
			    return new Timestamp( ( (Number) value ).longValue() );
			    
			} else {
			    return new Timestamp( TimeUtil.toDate( value.toString() ).getTime() );
			}
			
		} catch( Throwable t ) {
			if( _useDefaultAsFailure ) {
				return _defaultValueAsFailure;
			} else {
				throw new ConversionException( "Can not convert to java.sql.Timestamp:" + value, t );
			}
		}
	}
}

