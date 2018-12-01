package com.zfsoft.orcus.mast.servlet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zfsoft.orcus.lang.Cleaner;
import com.zfsoft.orcus.lang.converters.ConvertUtil;
import com.zfsoft.orcus.lang.converters.Converter;
import com.zfsoft.orcus.lang.reflect.BeanClass;
import com.zfsoft.orcus.lang.reflect.BeanClassUtil;
import com.zfsoft.orcus.lang.reflect.Property;
import com.zfsoft.orcus.lang.reflect.SelfDescribed;
import com.zfsoft.orcus.lang.reflect.SingletonClassLoader;
import com.zfsoft.orcus.lang.reflect.Type;

/**
 * 从HTTP请求中获取请求参数的帮助类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-12
 * @version V1.0.0
 */
public class Parameters {
	
	/**
	 * 从HTTP请求中获取JavaBean对象各个属性的值
	 * <p>如果请求参数中找不到与属性对应的参数，则该属性将不会被设置</p>
	 * @param request HTTP请求对象
	 * @param className JavaBean的类名
	 * @return JavaBean实例
	 * @throws ParametersException 如果操作出现异常
	 */
	public Object getBean(HttpServletRequest request, String className) throws ParametersException {
		
		return getBean(request, className, "");
	}
	
	/**
	 * 从HTTP请求中获取JavaBean对象各个属性的值
	 * <p>如果请求参数中找不到与属性对应的参数，则该属性将不会被设置</p>
	 * @param request HTTP请求对象
	 * @param className JavaBean的类名
	 * @param prefix JavaBean属性名的前缀，如属性名为sex，前缀为student_，则相应的请求参数名应为student_sex
	 * @throws ParametersException 如果获取失败
	 */
	public Object getBean( HttpServletRequest request, String className, String prefix ) throws ParametersException {
		
		try {
			BeanClass clazz = BeanClassUtil.getBeanClass(className);
			Object bean = clazz.newInstance();
			getBean(request, bean, clazz, prefix);
			
			return bean;
		} catch (ParametersException e) {
			throw(e);
		} catch (Throwable t) {
			throw new ParametersException( className, t );
		}
	}
	
	/**
	 * 从HTTP请求中获取JavaBean对象各个属性的值
	 * <p>如果请求参数中找不到与属性对应的参数，则该属性将不会被设置</p>
	 * @param request HTTP请求对象
	 * @param bean JavaBean对象
	 * @throws ParametersException 如果获取失败
	 */
	public void getBean( HttpServletRequest request, Object bean ) throws ParametersException {
	
		getBean(request, bean, "");
	}
	
	/**
	 * 从HTTP请求中获取JavaBean对象各个属性的值
	 * <p>如果请求参数中找不到与属性对应的参数，则该属性将不会被设置</p>
	 * @param request HTTP请求
	 * @param bean JavaBean对象
	 * @param prefix JavaBean属性名的前缀，如属性名为sex，前缀为student_，则相应的请求参数名应为student_sex
	 * @throws ParametersException 如果获取失败
	 */
	public void getBean( HttpServletRequest request, Object bean, String prefix ) throws ParametersException {
		BeanClass clazz = null;
		
		if( bean instanceof SelfDescribed ) {
			clazz = ( (SelfDescribed) bean ).introspect();
		} else {
			try {
				clazz = BeanClassUtil.getBeanClass( bean.getClass().getName() );
			} catch ( Throwable t ) {
				throw new ParametersException( t );
			}
		}
		
		getBean( request, bean, clazz, prefix );
	}
	
	/**
	 * 从HTTP请求中获取JavaBean对象各个属性的值
	 * <p>如果请求参数中找不到与属性对应的参数，则该属性将不会被设置</p>
	 * @param request HTTP请求
	 * @param bean JavaBean对象
	 * @param beanClass JavaBean实体类
	 * @param prefix JavaBean属性名的前缀，如属性名为sex，前缀为student_，则相应的请求参数名应为student_sex
	 * @throws ParametersException 如果获取失败
	 */
	protected void getBean( HttpServletRequest request, Object bean, BeanClass beanClass, String prefix ) throws ParametersException {
		if( prefix == null ) {
			prefix = "";
		}
		
		Enumeration<String> en = request.getParameterNames();
		List<String> list = new ArrayList<String>();
		 
		while( en.hasMoreElements() ) {
			list.add( en.nextElement() ); 
		}
		
		String name = null;
		
		try {
			for ( Property property : beanClass.writable() ) {
				name = prefix + property.getName();
				
				if( list.contains( name ) == false ) continue;
				
				Type type = property.getType();
				
				//非数组类型
				if( type.getElement() == null ) {
					Converter converter = ConvertUtil.lookup( type.getQulifiedName() );
					
					if( converter == null ) {
						property.write( bean, getBean( request, type.getQulifiedName(), name + "_" ) );
						
					} else {
						String value = request.getParameter( name );
						
						if( String.class.getName().equals( type.getQulifiedName() ) ) {
							property.write( bean, value );
						} else {
							if( value != null && "".equals( value ) ) {
								value = null;
							}
							
							property.write( bean, converter.convert(null, value ) );
						}
					}
					
					continue;
				}
				
				//数组类型
				Converter converter = ConvertUtil.lookup( type.getQulifiedName() );
				
				if( converter != null ) {
					String[] values = request.getParameterValues( name );
					
					if( values == null ) {
						String e = request.getParameter( name );
						
						if( e != null ) {
							values = new String[]{e};
						}
					}
					
					if( values == null ) {
						property.write( bean, null );
					} else {
						property.write( bean, converter.convert( 
								SingletonClassLoader.getInstance().loadClass( type.getQulifiedName() ), values) );
					}
					
					continue;
				}
			}
		} catch ( ParametersException e ) {
			throw e;
		} catch ( Throwable t ) {
			throw new ParametersException( t );
		} finally {
			Cleaner.clean( list );
		}
	}

}
