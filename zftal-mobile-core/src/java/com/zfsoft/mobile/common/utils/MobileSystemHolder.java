package com.zfsoft.mobile.common.utils;

import java.util.ResourceBundle;

/**
 * 
 * @author Administrator
 *
 */
public class MobileSystemHolder {
    private static ResourceBundle mobileSystemBundle = null;
    
    /**
     * @return 样式路径
     */
    public static String getStyleUrl() {
        if (mobileSystemBundle == null) {
            mobileSystemBundle = ResourceBundle.getBundle("mobilesystem");
        }
        return mobileSystemBundle.getString("style");
    }
    
    /**
     * 根据配置变量获取配置文件中对应的值
     * @author Administrator
     * @param name 配置名
     * @return 配置值
     */
    public static String getPropertiesValue(String name) {
        
        if (mobileSystemBundle == null) {
            mobileSystemBundle = ResourceBundle.getBundle("mobilesystem");
        }
        
        try {
            String value = mobileSystemBundle.getString(name);
            return value;
        } catch (Exception e) {
            return null;
        }
    }
    
}
