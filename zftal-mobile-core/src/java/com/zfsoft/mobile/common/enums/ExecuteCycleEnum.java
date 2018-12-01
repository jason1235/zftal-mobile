package com.zfsoft.mobile.common.enums;

/**
 * 类描述：执行周期枚举类
 */
public enum ExecuteCycleEnum {
    CYCLE_MONTH("每月", "CYCLE_MONTH"),                // 每月
    CYCLE_QUARTER("每季度", "CYCLE_QUARTER"),          // 每季度
    CYCLE_HALFOFYEAR("每半年", "CYCLE_HALFOFYEAR"),    // 每半年
    CYCLE_YEAR("每年", "CYCLE_YEAR");                  // 每年

    /**
     * 定义枚举类型自己的属性
     */
    private final String text;
    private final String key;

    private ExecuteCycleEnum(String text, String key) {
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
