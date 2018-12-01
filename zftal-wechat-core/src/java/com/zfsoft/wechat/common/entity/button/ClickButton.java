package com.zfsoft.wechat.common.entity.button;

/**
 * 单击按钮-子按钮
 * 描述：没有子菜单的菜单项。有可能是二级菜单项，也有可能是不含二级菜单的一级菜单。
 * @author Administrator
 *
 */
public class ClickButton extends Button {
    private String key;
    
    /**
     * 构造函数
     */
    public ClickButton(){
        super.setType(CLICK);
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    } 
    
}