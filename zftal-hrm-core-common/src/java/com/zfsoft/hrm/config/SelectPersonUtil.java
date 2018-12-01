package com.zfsoft.hrm.config;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.orcus.lang.TimeUtil;

/**
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-13
 * @version V1.0.0
 */
public class SelectPersonUtil {

	/**
	 * 获取查询条件字段的信息类属性描述信息
	 * @param config 人员选择配置信息
	 * @return
	 */
	public static List<InfoProperty> getConditionProperties( SelectPersonConfig config ) {
		List<InfoProperty> properties = new ArrayList<InfoProperty>();
		
		InfoClass clazz = InfoClassCache.getOverallInfoClass( config.getType().getName() );
		
		for ( SearchCondition condition : config.getConditions() ) {
			InfoProperty property = clazz.getPropertyByName( condition.getColumn() );
			
			if( property == null ) {
				continue;
			}
			
			property = property.clone();

			property.setName( condition.getTitle() );
			property.setFieldName( condition.getName() );
			properties.add( property );
		}
		
		
		return properties;
	}
	
	/**
	 * 
	 * @param type
	 * @param conditionName
	 * @param value
	 * @return
	 */
	public static Object getConditionValue( String type, String conditionName, Object value ) {
		InfoProperty property = getConditionProperty( type, conditionName );
		
		if( value != null && "".equals( value ) ) {
			return "";
		}
		
		if( Type.DATE.equals( property.getFieldType() ) 
				|| Type.MONTH.equals( property.getFieldType() )
				|| Type.YEAR.equals( property.getFieldType() ) ) {
			value = TimeUtil.toDate( String.valueOf( value ) );
		}
		
		return value;
	}
	
	/**
	 * 
	 * @param type
	 * @param conditionName
	 * @return
	 */
	public static InfoProperty getConditionProperty( String type, String conditionName ) {
		SearchCondition conditon = SelectPersonConfigFactory.getCondition( conditionName );
		
		InfoClass clazz = InfoClassCache.getOverallInfoClass( type );
		
		return clazz.getPropertyByName( conditon.getColumn() );
	}
}
