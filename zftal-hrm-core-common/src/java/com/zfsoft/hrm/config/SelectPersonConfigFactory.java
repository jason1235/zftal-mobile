package com.zfsoft.hrm.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择人员配置工厂
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-8
 * @version V1.0.0
 */
public final class SelectPersonConfigFactory {
	
	private static List<SelectPersonConfig> SINGLE;
	
	private static List<SelectPersonConfig> MULTIPLE;
	
	private static Map<String, SearchCondition> CONDITIONS = new HashMap<String, SearchCondition>();
	
	/**
	 * 设置单条选择的人选择人员配置信息
	 * @param single 单条选择的人选择人员配置信息
	 */
	public void setSingle(List<SelectPersonConfig> single) {
		SINGLE = single;
		
		setConditions( single );
	}

	/**
	 * 设置多条选择的人选择人员配置信息
	 * @param multiple 多条选择的人选择人员配置信息
	 */
	public void setMultiple(List<SelectPersonConfig> multiple) {
		MULTIPLE = multiple;
		
		setConditions( multiple );
	}
	
	/**
	 * 设置条件集合
	 */
	private void setConditions( List<SelectPersonConfig> configs ) {
		for ( SelectPersonConfig config : configs ) {
			for ( SearchCondition condition : config.getConditions() ) {
				CONDITIONS.put( condition.getName(), condition );
			}
		}
	}
	
	/**
	 * 根据条件名称获取条件表达式信息
	 * @param name 条件名称（具有唯一性标识）
	 * @return
	 */
	public static SearchCondition getCondition( String name ) {
		
		return CONDITIONS.get( name );
	}
	
	/**
	 * 获取指定类型的选择单条人员配置信息
	 * @param type 人员类型
	 * @return
	 */
	public static SelectPersonConfig getSelectSinglePerson( String type ) {
		for ( SelectPersonConfig single : SINGLE ) {
			if( single.getType().equals( type ) ) {
				return single;
			}
		}
		
		return null;
	}
	
	/**
	 * 获取指定类型的选择多条人员配置信息
	 * @param type 人员类型
	 * @return
	 */
	public static SelectPersonConfig getSelectMultiplePerson( String type ) {
		for ( SelectPersonConfig multiple : MULTIPLE ) {
			if( multiple.getType().equals( type ) ) {
				return multiple;
			}
		}
		
		return null;
	}
}
