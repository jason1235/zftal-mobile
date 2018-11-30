package com.zfsoft.util.base;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * 资源文件工具
 * @author Penghui.Qu
 *
 */
public class MessageUtil {

	private static ResourceBundle messageBundle = null;
	private static ResourceBundle systemBundle = null;
	
	static {
		/*
		 * 加载message.properties和system.properties资源文件
		 * system.properties继承了message.properties
		 * 如果两者中的key重复，优先取system.properties
		 */
		messageBundle = ResourceBundle.getBundle("message");
		systemBundle = ResourceBundle.getBundle("system");
	}
	
	/**
	 * 获取message.properties中的消息
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getText(String key,Object... params){
		String message = null;
		try{
			message = messageBundle.getString(key);
		} catch(MissingResourceException e){
			message = systemBundle.getString(key);
		}
		message = setParams(params, message);
		return message;
	}
	

	
	/**
	 * 为资源文件中的参数设值
	 * @param params
	 * @param message
	 * @return
	 */
	private static String setParams(Object[] params, String message) {
		if (null != params && params.length > 0){
			for (int i = 0 ; i < params.length ; i++){
				message = message.replace("{"+i+"}", String.valueOf(params[i]));
			}
		}
		return message;
	}
	
	/**
	 * 获取message.properties中的消息
	 * @param key
	 * @return
	 */
	public static String getText(String key){
		return getText(key,null);
	}
	
}
