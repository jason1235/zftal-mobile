package com.common;



import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 读取配置文件信息类
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: zfsoft.com </p>
 *
 * @since 2010-9-27下午02:33:56
 * @author:xxh
 * @version 1.0
 */
public class Config {
	private static final String DEFAULT_CONF_FILE = "/base.properties";

	private static PropertiesConfiguration config = null;

	static {
		try {
			String absolutePath = Config.class.getResource(getConfigFile())
					.getFile();
			config = new PropertiesConfiguration(absolutePath);
		} catch (Exception e) {
			throw new ConfigException(e);
		}

	}
	
	
	protected static String getConfigFile(){
		return DEFAULT_CONF_FILE;
	}

	public static String getString(String key, String defaultValue) {
		return config.getString(key, defaultValue);
	}

	public static String getString(String key) {
		return config.getString(key, null);
	}

	public static int getInt(String key, int defaultValue) {
		return config.getInt(key, defaultValue);
	}

	public static int getInt(String key) {
		return config.getInt(key, 0);
	}

	public static String[] getStringArray(String key) {
		return config.getStringArray(key);
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		return config.getBoolean(key, defaultValue);
	}

	public static boolean getBoolean(String key) {
		return config.getBoolean(key, false);
	}

	public static float getFloat(String key, float defaultValue) {
		return config.getFloat(key, defaultValue);
	}

	public static float getFloat(String key) {
		return config.getFloat(key, 0);
	}
}
