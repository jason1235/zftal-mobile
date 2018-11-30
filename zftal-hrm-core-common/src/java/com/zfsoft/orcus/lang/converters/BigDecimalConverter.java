package com.zfsoft.orcus.lang.converters;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.zfsoft.orcus.lang.Primitives;

/**
 * 标准的大数值数据类型转换器
 * <p>
 * 访类将输入数据转换为java.math.BigDecimal对象
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class BigDecimalConverter extends AbstractConverter {
	
	/**
	 * （空）构造函数
	 */
	public BigDecimalConverter() {
		super();
	}

	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 */
	public BigDecimalConverter(Object defaultValueAsNullInput) {
		super(defaultValueAsNullInput);
	}
	
	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 * @param defaultValueAsFailure 当转换失败时的缺省返回值
	 */
	public BigDecimalConverter(Object defaultValueAsNullInput, Object defaultValueAsFailure) {
		super(defaultValueAsNullInput, defaultValueAsFailure);
	}
	
	/**
	 * 将value转换为java.math.BigDecimal对象
	 * @return 转换后的数据( 类型：java.math.BigDecimal )
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
			if( value instanceof BigDecimal ) {
				return new BigDecimal( value.toString() );
				
			} else if( value instanceof Date ) {
				return new BigDecimal( ((Date)value).getTime() );
				
			} else if( value instanceof Calendar ) {
				return new BigDecimal( ((Calendar)value).getTime().getTime() );
				
			} else {
				return new BigDecimal( Primitives.reviseNumber( value.toString() ) );
			}
		} catch (Throwable t) {
			if( _useDefaultAsFailure ) {
				return _defaultValueAsFailure;
			} else {
				throw new ConversionException( "Can not convert to java.math.BigDecimal:" + value, t);
			}
		}
	}
}
