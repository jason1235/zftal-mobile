package com.zfsoft.orcus.lang.reflect;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * {@link Property}的通用实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-11
 * @version V1.0.0
 */
@SuppressWarnings("unchecked")
public class GenericProperty implements Property {

	private static final long serialVersionUID = -9042363646753695566L;

	private String name;
	
	private String ownerClassName;
	
	private Type type;
	
	private Method readMethod;
	
	private Method writeMethod;
	
	/**
	 * 构造函数
	 * @param pd 属性描述，不得为null
	 * @param beanClass 属性所属JavaBean的类型，不得为null
	 * @throws ClassNotFoundException 如果属性的类型无法解析
	 */
	public GenericProperty(PropertyDescriptor pd, Class beanClass) throws ClassNotFoundException {
		name 			= pd.getName();
		ownerClassName 	= beanClass.getName();
		type 			= TypeUtil.getType(pd.getPropertyType().getName());
		readMethod 		= pd.getReadMethod();
		writeMethod 	= pd.getWriteMethod();
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getOwnerClassName() {
		return ownerClassName;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public boolean isReadable() {
		return readMethod != null;
	}

	@Override
	public boolean isWritable() {
		return writeMethod != null;
	}

	@Override
	public Object read(Object bean) throws UnreadablePropertyException,
			ReadPropertyException {
		if( !isReadable() ) {
			throw new UnreadablePropertyException("Property[" + getName() + "] of "
					+ getOwnerClassName() + " is not readable property." );
		}
		
		try {
			
			return readMethod.invoke(bean, null);
			
		} catch (InvocationTargetException e) {
			throw new UnreadablePropertyException( "Fail to read property ["
					+ getName() + "] of " + getOwnerClassName(), e.getTargetException() );
		} catch (Throwable t) {
			throw new UnreadablePropertyException( "Fail to read property ["
					+ getName() + "] of " + getOwnerClassName(), t );
		}
	}

	@Override
	public void write(Object bean, Object propValue)
			throws UnwritablePropertyException, WritePropertyException {
		if( !isWritable() ) {
			throw new UnwritablePropertyException( "Property[" + getName() + "] of "
					+ getOwnerClassName() + " is not writable property." );
		}
		
		try {
			writeMethod.invoke( bean, new Object[]{ propValue } );
		} catch (InvocationTargetException e) {
			throw new WritePropertyException( "Fail to write property ["
					+ getName() + "] of " + getOwnerClassName(), e.getTargetException() );
		} catch (Throwable t) {
			throw new WritePropertyException( "Fail to write property ["
					+ getName() + "] of " + getOwnerClassName(), t );
		}
	}

}
