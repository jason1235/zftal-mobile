package com.zfsoft.mobile.common.enums;

/**
 * 类描述：产品枚举类
 */
public enum ProductEnum {
//    MOBILE_CAMPUS("移动校园", "MOBILE_CAMPUS"), // 移动校园
//    MOBILE_OA("移动OA", "MOBILE_OA"),           // 移动OA
//    MOBILE_XXMH("信息门户","MOBILE_XXMH"),
//    MOBILE_BG("移动办公","MOBILE_BG"),
//    MOBILE_JW("移动教务","MOBILE_JW"),
//    MOBILE_XXCX("信息查询","MOBILE_XXCX"),
//    MOBILE_XG("信息门户","MOBILE_XG"),
//    MOBILE_XX("移动学习","MOBILE_XX"),
//    MOBILE_TZGG("通知公告","MOBILE_TZGG");
	MOBILE_CAMPUS("移动校园", "MOBILE_CAMPUS");
    /**
     * 定义枚举类型自己的属性
     */
    private final String text;
    private final String key;

    private ProductEnum(String text, String key) {
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
