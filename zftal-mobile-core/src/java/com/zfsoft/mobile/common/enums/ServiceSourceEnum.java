package com.zfsoft.mobile.common.enums;

/**
 * 类描述：服务来源枚举类
 */
public enum ServiceSourceEnum {
    CUSTOM_SOURCE("自定义", "CUSTOM_SOURCE"),           // 自定义来源
    INFOCLASS_SOURCE("信息类", "INFOCLASS_SOURCE"),     // APP服务
    SYSTEM_SOURCE("系统", "SYSTEM_SOURCE");            // 系统

    /**
     * 定义枚举类型自己的属性
     */
    private final String text;
    private final String key;

    private ServiceSourceEnum(String text, String key) {
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

