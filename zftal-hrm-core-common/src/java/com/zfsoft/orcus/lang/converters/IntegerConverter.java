package com.zfsoft.orcus.lang.converters;

import com.zfsoft.orcus.lang.Primitives;

/**
 * 标准的整型数据类型转换器
 * <p>
 * 访类将输入数据转换为java.lang.Integer对象
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class IntegerConverter extends AbstractConverter {

	/**
	 * （空）构造函数
	 */
	public IntegerConverter() {
		super();
	}

	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 */
	public IntegerConverter(Object defaultValueAsNullInput) {
		super(defaultValueAsNullInput);
	}
	
	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 * @param defaultValueAsFailure 当转换失败时的缺省返回值
	 */
	public IntegerConverter(Object defaultValueAsNullInput, Object defaultValueAsFailure) {
		super(defaultValueAsNullInput, defaultValueAsFailure);
	}

	/**
	 * 将value转换为java.lang.Integer对象
	 * @return 转换后的数据( 类型：java.lang.Integer )
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
			if( value instanceof Integer ) {
			    return new Integer( ( (Integer) value ).intValue() );
			    
			} else if( value instanceof Number ) {
			    return new Integer( ( (Number) value ).intValue() );
			    
			} else {
			    return new Integer( Primitives.reviseNumber(value.toString()) );
			}
		} catch( Throwable t ) {
			if( _useDefaultAsFailure ) {
				return _defaultValueAsFailure;
			} else {
				throw new ConversionException( "Can not convert to Integer:" + value, t );
			}
		}
	}

}
