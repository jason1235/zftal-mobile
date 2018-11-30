package com.zfsoft.orcus.beans.dyna;

import java.util.Map;

import com.zfsoft.orcus.lang.NonprimitiveException;
import com.zfsoft.orcus.lang.Primitives;
import com.zfsoft.orcus.lang.reflect.BeanClass;
import com.zfsoft.orcus.lang.reflect.Property;
import com.zfsoft.orcus.lang.reflect.Type;

/**
 * {@link DynaBean}的缺省实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public class GenericDynaBean implements DynaBean {

	private static final long serialVersionUID = -1340265033673922687L;

	protected DynaClassDelegate dynaClassDelegate;
	
	protected Map<String, Object> values;
	
	/**
	 * 构造函数
	 * @param dynaClassDelegate
	 */
	public GenericDynaBean(DynaClassDelegate dynaClassDelegate) {
		this.dynaClassDelegate = dynaClassDelegate;
		introspect();
	}
	
	@Override
	public boolean has(String name) {
		return dynaClass().get(name) != null;
	}
	
	@Override
	public Object get(String name) {
		Property property = dynaClass().get(name);
		
		if( property == null ) {
			throw new DynaPropertyNotFoundException(name);
		}
		
		Object value = values.get(name);
		
		if( value != null ) {
			return value;
		}
		
		/*
		 * 如果属性值的类型为原始类型，则值不得为空，必须对其设置默认值
		 */
		if( property.getType().isPrimitive() == false ) {
			return value;
		}
		
		return Primitives.getDefaultValue( property.getType().getQulifiedName() );
	}

	@Override
	public void set(String name, Object value) {
		Property property = dynaClass().get(name);
		
		if( property == null ) {
			throw new DynaPropertyNotFoundException(name);
		}
		
		if( value == null && property.getType().isPrimitive() ) {
			throw new NullPointerException("Can not set " + name
					+ " which types is " + property.getType().getQulifiedName()
					+ " with null");
		} else if( value == null && property.getType().isPrimitive() == false ) {
			//do nothing
		}
		
		values.put(name, value);
		
	}
	
	@Override
	public DynaClass dynaClass() {

		return dynaClassDelegate.getDynaClass();
	}

	@Override
	public BeanClass introspect() {

		return dynaClass();
	}
	
	/**
	 * 判断目标对象是否和源类型匹配(一致或是源类型的子类)
	 * @param dest
	 * @param source
	 * @return
	 */
	protected boolean isAssignable(Object dest, Type source) {
		try {
			if( Primitives.getWrapperClass( source.getQulifiedName())
					.equals(dest.getClass()) ) {
				return true;
			}
			
		} catch (NonprimitiveException e) {
			//do nothing
		}
		
		return false;
	}

}
