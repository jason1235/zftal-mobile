package com.zfsoft.wechat.common.utils;

import java.util.ResourceBundle;

/**
 * 
 * @author Administrator
 *
 */
public class WeChatSystemHolder {
    private static ResourceBundle wechatsystemBundle = null;
    
    /**
     * @return 样式路径
     */
    public static String getStyleUrl() {
        if (wechatsystemBundle == null) {
        	wechatsystemBundle = ResourceBundle.getBundle("wechatsystem");
        }
        return wechatsystemBundle.getString("style");
    }
    
    /**
     * 根据配置变量获取配置文件中对应的值
     * @author Administrator
     * @param name 配置名
     * @return 配置值
     */
    public static String getPropertiesValue(String name) {
        
        if (wechatsystemBundle == null) {
        	wechatsystemBundle = ResourceBundle.getBundle("wechatsystem");
        }
        
        try {
            String value = wechatsystemBundle.getString(name);
            return value;
        } catch (Exception e) {
            return null;
        }
    }
    
}
