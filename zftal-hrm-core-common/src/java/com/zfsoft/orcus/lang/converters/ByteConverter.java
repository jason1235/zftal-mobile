package com.zfsoft.orcus.lang.converters;

import com.zfsoft.orcus.lang.Primitives;

/**
 * 标准的字节型数据类型转换器
 * <p>
 * 访类将输入数据转换为java.lang.Byte对象
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class ByteConverter extends AbstractConverter {

	/**
	 * （空）构造函数
	 */
	public ByteConverter() {
		super();
	}

	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 */
	public ByteConverter(Object defaultValueAsNullInput) {
		super(defaultValueAsNullInput);
	}
	
	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 * @param defaultValueAsFailure 当转换失败时的缺省返回值
	 */
	public ByteConverter(Object defaultValueAsNullInput, Object defaultValueAsFailure) {
		super(defaultValueAsNullInput, defaultValueAsFailure);
	}

	/**
	 * 将value转换为java.lang.Byte对象
	 * @return 转换后的数据( 类型：java.lang.Byte )
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
			if( value instanceof Byte ) {
				return new Byte( ( (Byte)value ).byteValue() );
				
			} else if( value instanceof Number ) {
				return new Byte( ( (Number)value ).byteValue() );
				
			} else {
				return new Byte( Primitives.reviseNumber(value.toString()) );
			}
		} catch( Throwable t ) {
			if( _useDefaultAsFailure ) {
				return _defaultValueAsFailure;
			} else {
				throw new ConversionException( "Can not convert to Byte:" + value, t );
			}
		}
	}

}
