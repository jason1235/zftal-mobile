package com.zfsoft.orcus.beans.xdo.sql;

import java.util.Properties;

import com.zfsoft.orcus.beans.xdo.EntityProperty;
import com.zfsoft.orcus.lang.Cleaner;
import com.zfsoft.orcus.lang.eval.NameReviser;
import com.zfsoft.orcus.lang.eval.vars.NameMapping;

/**
 * 实体属性描述信息
 * <p>
 * 该类包含了描述数据库实体的元数据
 * </p>
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-27
 * @version V1.0.0
 */
@SuppressWarnings("unchecked")
public class SQLEntityProperty implements EntityProperty, NameMapping {//implements EntityProperty {

	private static final long serialVersionUID = -7005746677276248163L;

	private String propertyid;				//属性的全局ID
	
	private String title;					//属性的标题
	
	private String description;				//属性的描述
	
	private String propertyName;			//属性名
	
	private Class propertyType;				//属性类型
	
	private String fieldName;				//字段名
	
	private String fieldType;				//字段类型（取值为com.zfsoft.orcus.sql.UnifiedTypes定义的常量）
	
	private String fieldDeclarer;			//该字段的定义者，及该字段所从属的表的表名
	
	private String fieldDeclarerAlias;		//该字段定义者的别名	
	
	private boolean primary;				//是否为主键
	
	private boolean nullable;				//是否能为空
	
	private int displaySize;				//字段的最大长度
	
	private int precision;					//数值类型字段的精度
	
	private int scale;						//数值类型字段的小数位数
	
	private Properties appendix;			//字段的附属数据
	
	/**
	 * 销毁该对象
	 */
	public void destory() {
		
		Cleaner.clean( appendix );
	}

	@Override
	public String propertyName() {

		return propertyName;
	}

	@Override
	public String variableName() {

		return NameReviser.reviser( title );
	}
	
	/**
	 * 返回属性的全局ID
	 */
	public String getPropertyid() {
		return propertyid;
	}

	/**
	 * 设置属性的全局ID
	 * @param propertyid 属性的全局ID
	 */
	public void setPropertyid(String propertyid) {
		this.propertyid = propertyid;
	}

	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * 设置属性的标题
	 * @param title 属性的标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * 设置属性的描述
	 * @param description 属性的描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * 设置属性名
	 * @param propertyName 属性名
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * 返回属性类型
	 */
	public Class getPropertyType() {
		return propertyType;
	}

	/**
	 * 设置属性类型
	 * @param propertyType 属性类型
	 */
	public void setPropertyType(Class propertyType) {
		this.propertyType = propertyType;
	}

	@Override
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 设置字段名
	 * @param fieldName 字段名
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Override
	public String getFieldType() {
		return fieldType;
	}

	/**
	 * 设置字段类型（取值为com.zfsoft.orcus.sql.UnifiedTypes定义的常量）
	 * @param fieldType 字段类型  @see {@link UnifiedTypes}
	 */
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	@Override
	public String getFieldDeclarer() {
		return fieldDeclarer;
	}

	/**
	 * 设置该字段的定义者，及该字段所从属的表的表名
	 * @param fieldDeclarer 该字段的定义者，及该字段所从属的表的表名
	 */
	public void setFieldDeclarer(String fieldDeclarer) {
		this.fieldDeclarer = fieldDeclarer;
	}

	@Override
	public String getFieldDeclarerAlias() {
		return fieldDeclarerAlias;
	}

	/**
	 * 设置该字段定义者的别名	
	 * @param fieldDeclarerAlias 该字段定义者的别名	
	 */
	public void setFieldDeclarerAlias(String fieldDeclarerAlias) {
		this.fieldDeclarerAlias = fieldDeclarerAlias;
	}

	@Override
	public boolean isPrimary() {
		return primary;
	}

	/**
	 * 设置是否为主键
	 * @param primary 是否为主键
	 */
	public void setPrimary(boolean primary) {
		this.primary = primary;
	}

	@Override
	public boolean isNullable() {
		return nullable;
	}

	/**
	 * 设置是否能为空
	 * @param nullable 是否能为空
	 */
	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	@Override
	public int getDisplaySize() {
		return displaySize;
	}

	/**
	 * 设置字段的最大长度
	 * @param displaySize 字段的最大长度 
	 */
	public void setDisplaySize(int displaySize) {
		this.displaySize = displaySize;
	}

	@Override
	public int getPrecision() {
		return precision;
	}

	/**
	 * 设置数值类型字段的精度
	 * @param precision 数值类型字段的精度
	 */
	public void setPrecision(int precision) {
		this.precision = precision;
	}

	@Override
	public int getScale() {
		return scale;
	}

	/**
	 * 设置数值类型字段的小数位数
	 * @param scale 数值类型字段的小数位数
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	@Override
	public Properties getAppendix() {
		if( appendix == null ) {
			appendix = new Properties();
		}
		
		return appendix;
	}

	/**
	 * 设置字段的附属数据
	 * @param appendix 字段的附属数据
	 */
	public void setAppendix(Properties appendix) {
		this.appendix = appendix;
	}

}
