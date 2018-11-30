package com.zfsoft.orcus.lang.converters;

/**
 * 数据类型转换器
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
@SuppressWarnings("unchecked")
public interface Converter {
	
	/**
	 * 将输入对象转换为指定类型输出对象
	 * @param type 输出对象的类型
	 * @param value 输入对象
	 * @return 输出对象
	 * @throws NullInputException (runtime)当且仅当 value==null 且 未调用过 setDefaultValueAsNullInput(java.lang.Object)方法 时抛出该异常
	 * @throws ConversionException (runtime)当且仅当 转换出错 且 未调用过 setDefaultValueAsFailure(java.lang.Object)方法 时抛出该异常
	 * @see #setDefaultValueAsNullInput(java.lang.Object)
	 * @see #setDefaultValueAsFailure(java.lang.Object)
	 */
	public Object convert(Class type, Object value);
	
	/**
	 * 设置当被转换对象为null时的缺省返回值
	 * <p>
	 * 如果用户未调用该方法，则当转换对象为null时抛出{@link NullInputException}
	 * </p>
	 * @param value 缺省返回值
	 * @see #convert( java.lang.Class, java.lang.Object )
	 */
	public void setDefaultValueAsNullInput(Object value);
	
	/**
	 * 设置当转换失败时的缺省返回值
	 * <p>
	 * 如果用户未调用该方法，则当失败时时抛出{@link ConversionException}
	 * </p>
	 * @param value 缺省返回值
	 * @see #convert( java.lang.Class, java.lang.Object )
	 */
	public void setDefaultValueAsFailure(Object value);

}
