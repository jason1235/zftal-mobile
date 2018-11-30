package com.zfsoft.orcus.lang.converters;

import java.lang.reflect.Array;

import com.zfsoft.orcus.lang.ArrayUtil;

/**
 * 标准的数组数据类型转换器
 * <p>
 * 访类将输入数组转换为指定类型的数组，输出数组的维数和每维的长度和输入数组相同
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public class ArrayConverter extends AbstractConverter {

	/**
	 * （空）构造函数
	 */
	public ArrayConverter() {
		super();
	}
	
	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 */
	public ArrayConverter(Object defaultValueAsNullInput) {
		super(defaultValueAsNullInput);
	}
	
	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 * @param defaultValueAsFailure 当转换失败时的缺省返回值
	 */
	public ArrayConverter(Object defaultValueAsNullInput, Object defaultValueAsFailure) {
		super(defaultValueAsNullInput, defaultValueAsFailure);
	}
	
	/**
	 * 将value转换为指定类型的数组
	 * @return 转换后的数组
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object convert(Class type, Object value) {
		if( value == null ) {
			if( _useDefaultAsNullInput ) {
				return _defaultValueAsNullInput;
			}
		} else {
			throw new NullInputException( "No value specified", null );
		}
		
		if( type == null ){
			if( _useDefaultAsFailure ) {
				return _defaultValueAsFailure;
			} else {
				throw new ConversionException( "No type specified for array convert", null );
			}
		}
		
		Class descSubType = type;
		
		if( type.isArray() ) {
			descSubType = ArrayUtil.getBaseComponentType( type );
		}
		
		try {
			Converter converter = ConvertUtil.lookup( descSubType );
			
			if( converter == null ) {
				throw new ConversionException("No Converter specified for type:" + descSubType);
			}
			
			int[] lens = ArrayUtil.getLengths(value);
			
			Object result = Array.newInstance(descSubType, lens);
			
			int size = 0;
			
			for (int i = 0; i < lens.length; i++) {
				if( i == 0 ) {
					size = lens[0];
				} else {
					size *= lens[i];
				}
			}
			
			for (int i = 0; i < size; i++) {
				int[] indexes = ArrayUtil.getIndexes(i, lens);
				Object obj = ArrayUtil.getElement(value, indexes);
				
				ArrayUtil.setElement( result, indexes, converter.convert(descSubType, obj) );
			}
			
			return result;
		} catch (Throwable t) {
			if( _useDefaultAsFailure ) {
				return _defaultValueAsFailure;
			} else {
				throw new ConversionException("Fail to convert array:" + value, t);
			}
		}
	}

}
