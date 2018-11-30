package com.zfsoft.orcus.beans.xdo.sql;

import java.util.List;
import java.util.Properties;

import com.zfsoft.orcus.beans.xdo.EntityClass;
import com.zfsoft.orcus.beans.xdo.EntityProperty;
import com.zfsoft.orcus.lang.Cleaner;
import com.zfsoft.orcus.lang.reflect.BeanClassUtil;
import com.zfsoft.orcus.lang.reflect.TypeUtil;

/**
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-27
 * @version V1.0.0
 */
public class SQLEntityClass implements EntityClass {
	
	private static final long serialVersionUID = 8955876234334771677L;

	/**
	 * 实体类型：数据库表，其值为"TABLE"
	 */
	public static String TABLE = "TABLE";
	
	/**
	 * 实体类型：数据库视图，其值为"VIEW"
	 */
	public static String VIEW = "VIEW";
	
	/**
	 * 实体类型：组合体，其值为"COMPOSITE"
	 */
	public static String COMPOSITE = "COMPOSITE";
	
	private String classid;						//实体标识
	
	private String title;						//实体的标题
	
	private String description;					//实体的描述
	
	private String className;					//实体的Java类名
	
	private String objectName;					//数据库对象的名字
	
	private String objectType;					//数据库对象的类型
	
	private Properties appendix;				//实体的附属数据
	
	private SQLEntityProperty[] properties;		//所有实体属性的元数据
	
	
	/**
	 * 销毁该对象
	 */
	public void destory() {
		Cleaner.clean( appendix );
		
		if( className != null && !"".equals( className ) ) {
			BeanClassUtil.deregister( className );
			TypeUtil.deregister( className );
		}
		
		if( properties != null ) {
			for ( SQLEntityProperty property : properties ) {
				property.destory();
			}
		}
		
		properties = null;
	}

	@Override
	public EntityProperty findByFieldName(String fieldName) {
		if( properties == null || fieldName == null ) {
			return null;
		}
		
		for ( EntityProperty property : properties ) {
			if( fieldName.equals( property.getFieldName() ) ) {
				return property;
			}
		}
		
		return null;
	}

	@Override
	public EntityProperty findByPropertyName(String propertyName) {
		if( properties == null || propertyName == null ) {
			return null;
		}
		
		for ( EntityProperty property : properties ) {
			if( propertyName.equals( property.getPropertyName() ) ) {
				return property;
			}
		}
		
		return null;
	}
	
	/**
	 * 返回实体标识
	 */
	public String getClassid() {
		return classid;
	}

	/**
	 * 设置实体标识
	 * @param classid 实体标识
	 */
	public void setClassid(String classid) {
		this.classid = classid;
	}

	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * 设置实体的标题
	 * @param title 实体的标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * 设置实体的描述
	 * @param description 实体的描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getClassName() {
		return className;
	}

	/**
	 * 设置实体的Java类名
	 * @param className 实体的Java类名
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	@Override
	public String getObjectName() {
		return objectName;
	}

	/**
	 * 设置数据库对象的名字
	 * @param objectName 数据库对象的名字
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	@Override
	public String getObjectType() {
		return objectType;
	}

	/**
	 * 设置数据库对象的类型
	 * @param objectType 数据库对象的类型
	 */
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	@Override
	public Properties getAppendix() {
		return appendix;
	}

	/**
	 * 设置实体的附属数据
	 * @param appendix 实体的附属数据
	 */
	public void setAppendix(Properties appendix) {
		this.appendix = appendix;
	}

	@Override
	public EntityProperty[] getProperties() {
		
		return properties;
	}

	/**
	 * 设置所有实体属性的元数据
	 * @param properties 所有实体属性的元数据
	 */
	public void setProperties( EntityProperty[] properties ) {
		this.properties = (SQLEntityProperty[]) properties;
	}
	
	/**
	 * 设置所有实体属性的元数据
	 * @param properties 所有实体属性的元数据
	 */
	public void setProperties( List<EntityProperty> properties ) {
		this.properties = (SQLEntityProperty[]) properties.toArray();
	}

	@Override
	protected void finalize() throws Throwable {
		Cleaner.clean( appendix );
		
		properties = null;
		
		super.finalize();
	}
	
	

}
