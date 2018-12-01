package com.zfsoft.orcus.lang.converters;

/**
 * {@link Converter}的基础实现类(抽象类)
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-19
 * @version V1.0.0
 */
public abstract class AbstractConverter implements Converter {
	
	/**
	 * 当转换对象为null时的缺省返回值
	 */
	protected Object _defaultValueAsNullInput = null;
	
	/**
	 * 指示转换对象为null时是否返回缺省值:_defaultValueAsNullInput
	 */
	protected boolean _useDefaultAsNullInput = false;
	
	/**
	 * 当转换失败时的缺省返回值
	 */
	protected Object _defaultValueAsFailure = null;
	
	/**
	 * 指示转换失败时是否返回缺省值:_defaultValueAsFailure
	 */
	protected boolean _useDefaultAsFailure = false;
	
	/**
	 * （空）构造函数
	 */
	public AbstractConverter() {
		_defaultValueAsNullInput = null;
		_useDefaultAsNullInput = true;
		
		_defaultValueAsFailure = null;
		_useDefaultAsFailure = false;
	}
	
	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 */
	public AbstractConverter( Object defaultValueAsNullInput ) {
		_defaultValueAsNullInput = defaultValueAsNullInput;
		_useDefaultAsNullInput  = true;
		
		_defaultValueAsFailure = null;
		_useDefaultAsFailure = false;
	}

	/**
	 * 构造函数
	 * @param defaultValueAsNullInput 当转换对象为null时的缺省返回值
	 * @param defaultValueAsFailure 当转换失败时的缺省返回值
	 */
	public AbstractConverter( Object defaultValueAsNullInput, Object defaultValueAsFailure ) {
		_defaultValueAsNullInput = defaultValueAsNullInput;
		_useDefaultAsNullInput  = true;
		
		_defaultValueAsFailure = defaultValueAsFailure;
		_useDefaultAsFailure = true;
	}
	
	@Override
	public void setDefaultValueAsFailure( Object value ) {
		_defaultValueAsFailure = value;
		_useDefaultAsFailure = true;
	}
	
	@Override
	public void setDefaultValueAsNullInput( Object value ) {
		_defaultValueAsNullInput = value;
		_useDefaultAsNullInput = true;
	}
}
