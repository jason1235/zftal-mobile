package com.zfsoft.mobile.common.enums;

/**
 * 类描述：服务类型枚举类
 */
public enum ServiceTypeEnum {
	APP_SERVICE("APP服务", "APP_SERVICE"),           // APP服务
    WEB_SERVICE("WEB服务", "WEB_SERVICE"),           // WEB服务
    APP_APPLICATION("APP应用", "APP_APPLICATION");   // APP应用
    //NEWS_APPLICATION("资讯应用", "NEWS_APPLICATION");   // 资讯应用

    /**
     * 定义枚举类型自己的属性
     */
    private final String text;
    private final String key;

    private ServiceTypeEnum(String text, String key) {
        this.text = text;
        this.key = key;
    }

    /**
     * 展示文本
     * 
     * @return
     */
    public String getText() {
        return text;
    }

    /**
     * 代码编号
     * 
     * @return
     */
    public String getKey() {
        return key;
    }
}
