package com.zfsoft.common.system;

import java.util.ResourceBundle;

public class SubSystemHolder {
	private static ResourceBundle subsystemBundle = null;
	/**
	 * @return 样式路径
	 */
	public static String getStyleUrl(){
		if(subsystemBundle==null){
			subsystemBundle = ResourceBundle.getBundle("subsystem");
		}
		return subsystemBundle.getString("style");
	}
	/**
	 * 根据配置变量获取配置文件中对应的值
	 * @author chenminming
	 * @param name 配置名
	 * @return 配置值
	 */
	public static String getPropertiesValue(String name)
	{
		if(subsystemBundle==null){
			subsystemBundle = ResourceBundle.getBundle("subsystem");
		}
		try{
			String value=subsystemBundle.getString(name);
			return value;
		}catch (Exception e) {
			return null;
		}
	}
	
}
