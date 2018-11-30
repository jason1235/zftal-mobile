package com.zfsoft.orcus.beans.xdo;

import java.io.Serializable;
import java.util.Properties;

/**
 * 实体描述信息，该类包含了描述实体的元数据
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-27
 * @version V1.0.0
 */
public interface EntityClass extends Serializable {

	/**
	 * 返回实体的标题
	 */
	public String getTitle();
	
	/**
	 * 返回实体的描述
	 */
	public String getDescription();
	
	/**
	 * 返回实体的类名
	 */
	public String getClassName();
	
	/**
	 * 返回与该实体对应的存储空间对象的名字
	 */
	public String getObjectName();
	
	/**
	 * 返回存储空间对象的类型
	 */
	public String getObjectType();
	
	/**
	 * 返回实体的附属数据（never null）
	 */
	public Properties getAppendix();
	
	/**
	 * 返回所有实体属性的元数据(never null)
	 */
	public EntityProperty[] getProperties();
	
	/**
	 * 查找指定实体属性的元数据
	 * @param propertyName 指定实体属性的属性名
	 * @return 实体属性元数据，如果指定实体属性不存在则返回null
	 */
	public EntityProperty findByPropertyName( String propertyName );
	
	/**
	 * 查找指定实体属性的元数据
	 * @param fieldName 指定实体属性的对应存储空间对象的名字
	 * @return 实体属性元数据，如果指定实体属性不存在则返回null
	 */
	public EntityProperty findByFieldName( String fieldName );
}
