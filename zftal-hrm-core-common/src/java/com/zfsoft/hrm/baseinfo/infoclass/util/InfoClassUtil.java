package com.zfsoft.hrm.baseinfo.infoclass.util;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

/**
 * 信息类操作工具类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-9
 * @version V1.0.0
 */
public class InfoClassUtil {
	
	/**
	 * 默认信息类属性类型 
	 */
	public static String DEFAULT_PROPERTY_TYPE = "COMMON";
	
	/**
	 * 默认主键字段长度
	 */
	public static int DEFAULT_PRIMARY_FIELD_LEN = 20;
	
	/**
	 * 信息类属性：全局ID
	 */
	public static InfoProperty GLOBALID;
	
	/**
	 * 信息类属性：最后更新时间
	 */
	public static InfoProperty LAST_MODIFY_TIME;
	
	/**
	 * 信息类属性：日志_操作人
	 */
	public static InfoProperty LOG_OPERATOR;
	
	/**
	 * 信息类属性：日志_操作
	 */
	public static InfoProperty LOG_OPERATION;
	
	/**
	 * 信息类属性：日志_操作时间
	 */
	public static InfoProperty LOG_OPERATION_TIME;
	
	/**
	 * 信息类属性： 移动_工号（必须字段）
	 */
	public static InfoProperty MOBILE_GH;
	
	static {
		GLOBALID = new InfoProperty( "全局ID", "globalid", DEFAULT_PROPERTY_TYPE, false, false );
		
		LAST_MODIFY_TIME = new InfoProperty("最后修改时间", "lastModifyTime", Type.DATE, false, false);
		
		LOG_OPERATOR = new InfoProperty("操作人", "operator_", DEFAULT_PROPERTY_TYPE, false, true);
		
		LOG_OPERATION = new InfoProperty("操作", "operation_", DEFAULT_PROPERTY_TYPE, false, true);
		
		LOG_OPERATION_TIME = new InfoProperty("操作时间", "operation_time_", DEFAULT_PROPERTY_TYPE, false, false);
		
		MOBILE_GH = new InfoProperty( "工号", "gh", DEFAULT_PROPERTY_TYPE, false, false );
	}
	
	private static InfoClass BASE_INFO_CLASS;
	
	static {
		BASE_INFO_CLASS = new InfoClass();
		BASE_INFO_CLASS.getProperties().add( GLOBALID );
		BASE_INFO_CLASS.getProperties().add( LAST_MODIFY_TIME );
		BASE_INFO_CLASS.getProperties().add( MOBILE_GH );
	}
	
	/**
	 * 创建基础信息类
	 * @return
	 */
	public static InfoClass createBaseInfoClass() {
		
		System.out.println( BASE_INFO_CLASS.getProperties().size() );
		
		return BASE_INFO_CLASS.clone();
	}
	
	/**
	 * 创建日志信息类
	 * @return
	 */
	public static InfoClass createLogInfoClass() {
		
		return createLogInfoClass( BASE_INFO_CLASS );
	}
	
	/**
	 * 创建信息类主键属性描述信息
	 * @param name 主键中文名称
	 * @param fieldName 主键字段名称
	 * @return
	 */
	public static InfoProperty createPrimary( String name, String fieldName ) {
		InfoProperty property = new InfoProperty("", "", DEFAULT_PROPERTY_TYPE, false, false);
		
		property.setName( name );
		property.setDescription( name );
		property.setFieldName( fieldName );
		
		return property;
	}
	
	/**
	 * 更加信息类创建日志信息
	 * <p>
	 * 该方法不会对infoClass的属性进行修改
	 * </p>
	 * @param infoClass 动态Bean属性描述
	 * @return
	 */
	public static InfoClass createLogInfoClass( InfoClass infoClass ) {

		InfoClass clazz = new InfoClass();
		
		for ( InfoProperty property : infoClass.getProperties() ) {
			clazz.getProperties().add( property );
		}
		
		clazz.getProperties().add( LOG_OPERATOR );
		clazz.getProperties().add( LOG_OPERATION );
		clazz.getProperties().add( LOG_OPERATION_TIME );
		
		return clazz.clone();
	}
	
}
