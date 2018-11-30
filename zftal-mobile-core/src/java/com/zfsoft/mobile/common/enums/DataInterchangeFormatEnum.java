package com.zfsoft.mobile.common.enums;

/**
 * 类描述：数据交互枚举类
 */
public enum DataInterchangeFormatEnum {
    DICF_XML("XML", "DICF_XML"),           // XML
    DICF_JSON("JSON", "DICF_JSON"),        // JSON
    DICF_OBJECT("OBJECT", "DICF_OBJECT");  // OBJECT

    /**
     * 定义枚举类型自己的属性
     */
    private final String text;
    private final String key;

    private DataInterchangeFormatEnum(String text, String key) {
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
