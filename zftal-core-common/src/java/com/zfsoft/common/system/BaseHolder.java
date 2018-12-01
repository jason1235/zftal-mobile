package com.zfsoft.common.system;

import java.util.ResourceBundle;

import com.zfsoft.util.base.StringUtil;

public class BaseHolder {

	private static ResourceBundle baseBundle = null;
	/**
	 * 根据配置变量获取配置文件中对应的值
	 * @author chenminming
	 * @param name 配置名
	 * @return 配置值
	 */
	public static String getPropertiesValue(String name)
	{
		if(baseBundle==null){
			baseBundle = ResourceBundle.getBundle("base");
		}
		try{
			String value=baseBundle.getString(name);
			return value;
		}catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 根据配置变量获取配置文件中对应的值
	 * @author chenminming
	 * @param name 配置名
	 * @return 配置值
	 */
	public static String getPropertiesValue(String name,String defaultvalue)
	{
		if(baseBundle==null){
			baseBundle = ResourceBundle.getBundle("base");
		}
		try{
			String value=baseBundle.getString(name);
			return StringUtil.isEmpty(value) ? defaultvalue : value;
		}catch (Exception e) {
			return defaultvalue;
		}
	}
	
}
