package com.zfsoft.orcus.lang.converters;

/**
 * 标准的布尔型数据类型转换器
 * <p>
 * 访类将输入数据转换为java.lang.Boolean对象
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class BooleanConverter extends AbstractConverter {

	/**
	 * （空）构造函数
	 */
	public BooleanConverter() {
		super();
	}

	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 */
	public BooleanConverter(Object defaultValueAsNullInput) {
		super(defaultValueAsNullInput);
	}
	
	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 * @param defaultValueAsFailure 当转换失败时的缺省返回值
	 */
	public BooleanConverter(Object defaultValueAsNullInput, Object defaultValueAsFailure) {
		super(defaultValueAsNullInput, defaultValueAsFailure);
	}

	/**
	 * 将value转换为java.lang.Boolean对象
	 * @return 转换后的数据( 类型：java.lang.Boolean )
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
			if( value instanceof Boolean ) {
				return Boolean.valueOf( value.toString() );
			} else {
				String stringValue = value.toString().trim();
				
				if ( stringValue.equalsIgnoreCase("yes") || stringValue.equalsIgnoreCase("y")
						|| stringValue.equalsIgnoreCase("true") || stringValue.equalsIgnoreCase("1")
						|| stringValue.equalsIgnoreCase("on") ) {
					
					return Boolean.TRUE;
					
				} else if ( stringValue.equalsIgnoreCase("no") || stringValue.equalsIgnoreCase("n")
						|| stringValue.equalsIgnoreCase("false") || stringValue.equalsIgnoreCase("0")
						|| stringValue.equalsIgnoreCase("off") ) {
					
					return (Boolean.FALSE);
					
				} else {
					throw new ConversionException( "Can not convert to Boolean:" + stringValue, null );
				}
			}
		} catch( ClassCastException e ) {
			if( _useDefaultAsFailure ) {
				return _defaultValueAsFailure;
			} else {
				throw new ConversionException( "Can not convert to Boolean:" + value, e );
			}
		}
	}
}
