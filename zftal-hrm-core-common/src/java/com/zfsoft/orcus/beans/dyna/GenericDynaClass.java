package com.zfsoft.orcus.beans.dyna;

import java.util.Iterator;

import com.zfsoft.orcus.lang.ArrayUtil;
import com.zfsoft.orcus.lang.ClassUtil;
import com.zfsoft.orcus.lang.reflect.BeanClassUtil;
import com.zfsoft.orcus.lang.reflect.Property;
import com.zfsoft.orcus.lang.reflect.Type;
import com.zfsoft.orcus.lang.reflect.TypeUtil;

/**
 * {@link DynaClass}的缺省实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-11
 * @version V1.0.0
 */
public class GenericDynaClass implements DynaClass {
	
	protected Property[] _properties;
	
	protected Type _type;
	
	/**
	 * 构造函数
	 * @param className 类名
	 * @param properties JavaBean属性描述列表
	 */
	public GenericDynaClass(String className, Property[] properties) {
		if( className == null || "".equals(className) ) {
			throw new RuntimeException("className is not null！");
		}
		
		_type = new TypeImpl(className);
		
		if( properties == null ) {
			_properties = new Property[0];
		} else {
			_properties = properties;
		}
		
		registerMe();
	}

	@Override
	public DynaBean createDynaBean() {
		return new GenericDynaBean( new DynaClassDelegate() {
			
			private static final long serialVersionUID = 3635743956375593778L;

			@Override
			public DynaClass getDynaClass() {
				
				try {
					return (DynaClass) BeanClassUtil.getBeanClass( type().getQulifiedName() );
				} catch (Throwable t) {
					registerMe();
					
					return GenericDynaClass.this;
				}
			}
		});
		
	}

	@Override
	public Iterator<Property> all() {
		
		return new IteratorImpl(_properties);
	}
	
	@Override
	public int count() {
		
		return _properties.length;
	}

	@Override
	public Property get(String name) {
		if( name == null || "".equals(name) ) {
			throw new RuntimeException("parameter [name] is required; it cannot be null.");
		}
		
		for (Property property : _properties) {
			if( name.equals( property.getName() ) ) {
				return property;
			}
		}
		
		return null;
	}

	@Override
	public Object newInstance() {
		return createDynaBean();
	}
	
	@Override
	public Type type() {

		return _type;
	}

	@Override
	public Property[] readable() {
		Property[] properties = new Property[0];
		
		for (Property property : _properties) {
			if( property.isReadable() ) {
				properties = (Property[]) ArrayUtil.addElement( properties, _properties, Property.class );
			}
		}
		
		return properties;
	}

	@Override
	public Property[] writable() {
		Property[] properties = new Property[0];
		
		for (Property property : _properties) {
			if( property.isWritable() ) {
				properties = (Property[]) ArrayUtil.addElement( properties, _properties, Property.class );
			}
		}
		
		return properties;
	}
	
	/**
	 * 注册自己到类型注册机构
	 */
	protected void registerMe() {
		if( BeanClassUtil.contains( type().getQulifiedName() ) ) {
			BeanClassUtil.deregister(type().getQulifiedName() );
		}
		
		BeanClassUtil.register(this);
		
		if( TypeUtil.contains( type().getQulifiedName() ) ) {
			TypeUtil.deregister(type().getQulifiedName() );
		}
		
		TypeUtil.register(type());
	}
	
	/**
	 * 动态JavaBean的类型描述
	 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
	 * @date 2012-6-11
	 * @version V1.0.0
	 */
	protected class TypeImpl implements Type {

		private static final long serialVersionUID = 7511047119262135145L;

		protected String ti_name;
		
		protected String ti_qulifiedName;
		
		protected String ti_qulifiedPackageName;
		
		/**
		 * 构造函数
		 * @param className 类名
		 */
		protected TypeImpl(String className) {
			ti_qulifiedName 		= className;
			ti_name 				= ClassUtil.getShortClassName(className);
			ti_qulifiedPackageName 	= ClassUtil.getPackageName(className);
		}
		
		/*
		 * (non-Javadoc)
		 * @see com.zfsoft.hrm.lang.reflect.Type#getDimension()
		 */
		@Override
		public int getDimension() {
			return 0;
		}

		/*
		 * (non-Javadoc)
		 * @see com.zfsoft.hrm.lang.reflect.Type#getDynamicBeanClassName()
		 */
		@Override
		public String getDynamicBeanClassName() {
			return createDynaBean().getClass().getName();
		}

		/*
		 * (non-Javadoc)
		 * @see com.zfsoft.hrm.lang.reflect.Type#getElement()
		 */
		@Override
		public Type getElement() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * @see com.zfsoft.hrm.lang.reflect.Type#getName()
		 */
		@Override
		public String getName() {
			return ti_name;
		}

		/*
		 * (non-Javadoc)
		 * @see com.zfsoft.hrm.lang.reflect.Type#getQulifiedName()
		 */
		@Override
		public String getQulifiedName() {
			return ti_qulifiedName;
		}

		/*
		 * (non-Javadoc)
		 * @see com.zfsoft.hrm.lang.reflect.Type#getQulifiedPackageName()
		 */
		@Override
		public String getQulifiedPackageName() {
			return null;
		}

		/*
		 * (non-Javadoc)
		 * @see com.zfsoft.hrm.lang.reflect.Type#isDynamic()
		 */
		@Override
		public boolean isDynamic() {
			return true;
		}

		/*
		 * (non-Javadoc)
		 * @see com.zfsoft.hrm.lang.reflect.Type#isPrimitive()
		 */
		@Override
		public boolean isPrimitive() {
			return false;
		}
		
	}
	
	protected class IteratorImpl implements Iterator<Property> {
		
		private Property[] _props;
		
		private int _index;
		
		/**
		 * 构造函数
		 * @param properties JavaBean属性列表
		 */
		protected IteratorImpl( Property[] properties ) {
			_props = properties;
			_index = 0;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		@Override
		public boolean hasNext() {
			
			return _index < _props.length;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		@Override
		public Property next() {
			
			return _props[_index++];
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove method is unsupported.");
		}
		
	}

}
