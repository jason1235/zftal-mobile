package com.zfsoft.orcus.lang.reflect;

import java.util.Iterator;

/**
 * JavaBean实体类接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public interface BeanClass {
	
	/**
	 * 返回JavaBean类型描述
	 * @return
	 */
	public Type type();
	
	/**
	 * 返回属性的数量
	 * @return
	 */
	public int count();

	/**
	 * 返回所有属性
	 * 
	 * <p>
	 * 迭代器包含对象的类型为{@link Property}
	 * </p>
	 * @return
	 */
	public Iterator<Property> all();
	
	/**
	 * 返回所有可读属性（never null）
	 */
	public Property[] readable();
	
	/**
	 * 返回所有可写属性（never null）
	 */
	public Property[] writable();
	
	/**
	 * 返回指定属性名的属性
	 * @param name 属性，如果指定的属性不存在则返回null
	 */
	public Property get(String name);
	
	/**
	 * 重新实例
	 * @throws InitializationException
	 * @throws IllegalAccessException
	 */
	public Object newInstance() throws InitializationException, IllegalAccessException;
}
