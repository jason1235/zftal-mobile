package com.zfsoft.wechat.common.entity.button;


/**
 * 浏览按钮-子按钮
 * 描述：没有子菜单的菜单项。有可能是二级菜单项，也有可能是不含二级菜单的一级菜单。
 * @author Administrator
 *
 */
public class ViewButton extends Button {
    private String url;

    /**
     * 构造函数
     */
    public ViewButton(){
        super.setType(VIEW);
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    } 
    
}
