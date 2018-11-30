package com.zfsoft.orcus.lang.reflect;

import java.io.Serializable;

/**
 * JavaBean属性描述，包含属性的描述系想你级提供访问属性的值
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-8
 * @version V1.0.0
 */
public interface Property extends Serializable {

	/**
	 * 返回属性名称
	 * @return
	 */
	public String getName();
	
	/**
	 * 返回属性所属JavaBean的类名
	 * @return
	 */
	public String getOwnerClassName();
	
	/**
	 * 返回属性类型描述
	 * @return
	 */
	public Type getType();
	
	/**
	 * 返回属性是否可读属性
	 * @return
	 */
	public boolean isReadable();
	
	/**
	 * 返回属性是否可写属性
	 * @return
	 */
	public boolean isWritable();
	
	/**
	 * 读取属性的值
	 * @param bean JavaBean实例
	 * @return 属性值
	 * @throws UnreadablePropertyException 如果属性不可读
	 * @throws ReadPropertyException 读取操作出错
	 */
	public Object read(Object bean) throws UnreadablePropertyException, ReadPropertyException;
	
	/**
	 * 设置属性的值
	 * @param bean JavaBean实例
	 * @param propValue 属性值
	 * @throws UnwritablePropertyException 如果属性不可写
	 * @throws WritePropertyException 设置操作出现异常
	 */
	public void write(Object bean, Object propValue) throws UnwritablePropertyException, WritePropertyException;
	
	
}
