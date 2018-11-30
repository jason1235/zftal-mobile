package com.zfsoft.mobile.common.enums;

/**
 * 类描述：展现形式枚举类
 */
public enum ShowTypeEnum {
	//VEDIO_SHOW("视频", "VEDIO_SHOW"),          // 视频
    TELETEXT_SHOW("图文", "TELETEXT_SHOW");    // 图文
    
    //IMAGE_SHOW("图片", "IMAGE_SHOW"),          // 图片
    //DETAIL_SHOW("详情", "DETAIL_SHOW");        // 详情

    /**
     * 定义枚举类型自己的属性
     */
    private final String text;
    private final String key;

    private ShowTypeEnum(String text, String key) {
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
