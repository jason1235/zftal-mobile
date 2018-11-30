package com.zfsoft.orcus.lang.reflect;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.zfsoft.orcus.lang.ArrayUtil;

/**
 * {@link BeanClass}的通用实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-11
 * @version V1.0.0
 */
@SuppressWarnings("unchecked")
public class GenericBeanClass implements BeanClass {
	
	protected Type type;
	
	protected Property[] properties;
	
	protected transient Property[] readable;
	
	protected transient Property[] writable;
	
	/**
	 * 构造函数
	 * @param clazz JavaBean类型，不得为null
	 * @throws BeanIntrospectionException 如果解析clazz失败
	 */
	public GenericBeanClass(Class clazz) throws BeanIntrospectionException {
		List<Property> properties = new ArrayList<Property>();
		
		try {
			this.type = TypeUtil.getType( clazz.getName() );
			
			BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
			PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
			
			for (PropertyDescriptor pd : pds) {
				if( pd.getName().equals("class") ) {
					continue;
				}
				
				properties.add( new GenericProperty( pd, clazz ) );
			}
			
			this.properties = properties.toArray( new Property[0] );
			this.properties = ( Property[] ) ArrayUtil.sort( this.properties,
					new Comparator() {

						@Override
						public int compare(Object object1, Object object2) {
							String name1 = ( ( Property ) object1 ).getName();
							String name2 = ( ( Property ) object2 ).getName();
							
							return name1.compareTo( name2 );
						}
					});
			
		} catch (Throwable t) {
			throw new BeanIntrospectionException( clazz.getName(), t);
		}
	}

	@Override
	public Type type() {
		
		return this.type;
	}

	@Override
	public int count() {

		return this.properties.length;
	}
	
	@Override
	public Iterator<Property> all() {
		
		return new IteratorImpl( this.properties );
	}

	@Override
	public Property[] readable() {
		if( this.readable == null ) {
			Property[] properties = new Property[0];
			
			for (int i = 0; i < this.properties.length; i++) {
				if( this.properties[i].isReadable() ) {
					properties = ( Property[] ) ArrayUtil.addElement( properties, this.properties[i], Property.class );
				}
			}
			
			this.readable = properties;
		}
		
		return this.readable;
	}

	@Override
	public Property[] writable() {
		if( this.writable == null ) {
			Property[] properties = new Property[0];
			
			for (int i = 0; i < this.properties.length; i++) {
				if( this.properties[i].isWritable() ) {
					properties = ( Property[] ) ArrayUtil.addElement( properties, this.properties[i], Property.class );
				}
			}
			
			this.writable = properties;
		}
		
		return this.writable;
	}
	
	@Override
	public Property get(String name) {
		if( name == null || "".equals(name) ) {
			throw new RuntimeException("parameter [name] is required; it cannot be null.");
		}
		
		for (Property property : this.properties) {
			if( name.equals(property.getName())) {
				return property;
			}
		}
		
		return null;
	}

	@Override
	public Object newInstance() throws InitializationException,
			IllegalAccessException {
		try {
			return SingletonClassLoader.getInstance().loadClass( type().getQulifiedName() ).newInstance();
		} catch (InstantiationException e) {
			throw new InitializationException( e );
		} catch (ClassNotFoundException e) {
			throw new InitializationException( e );
		}
	}

	private class IteratorImpl implements Iterator {
		
		private Property[]	_props;
		
		private int 		_index;
		
		/**
		 * 构造函数
		 * @param properties JavaBean属性列表
		 */
		private IteratorImpl( Property[] properties ) {
			_props = properties;
			_index = 0;
		}

		@Override
		public boolean hasNext() {
			return _index < _props.length;
		}

		@Override
		public Object next() {
			return _props[ _index++ ];
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove method is unsupported." );
		}
		
	}

}
