package com.zfsoft.orcus.beans.xdo;

import java.io.Serializable;
import java.util.Properties;

/**
 * 实体属性描述信息，该类包含了描述实体属性的元数据
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-27
 * @version V1.0.0
 */
public interface EntityProperty extends Serializable {

	/**
	 * 返回属性的标题
	 */
	public String getTitle();

	/**
	 * 返回属性的描述
	 */
	public String getDescription();
	
	/**
	 * 返回JavaBean属性的名字
	 */
	public String getPropertyName();

	/**
	 * 返回JavaBean属性类型
	 */
	@SuppressWarnings("unchecked")
	public Class getPropertyType();
	
	/**
	 * 返回存储空间实体域的名字
	 */
	public String getFieldName();

	/**
	 * 返回存储空间实体域的类型
	 */
	public String getFieldType();

	/**
	 * 返回存储空间实体域的定义者
	 */
	public String getFieldDeclarer();

	/**
	 * 返回存储空间实体域定义者的别名(null 表示没有别名)
	 */
	public String getFieldDeclarerAlias();

	/**
	 * 返回实体域是否是实体的主键
	 */
	public boolean isPrimary();

	/**
	 * 返回实体域的值是否能为null
	 */
	public boolean isNullable();
	
	/**
	 * 返回实体域的数据最大长度（0代表没有最大长度限制）
	 */
	public int getDisplaySize();

	/**
	 * 返回数值型实体域的精度
	 */
	public int getPrecision();
	
	/**
	 * 返回数值型实体域的小数位数
	 */
	public int getScale();
	
	/**
	 * 返回实体域的附属数据（never null）
	 */
	public Properties getAppendix();
}
