package com.zfsoft.wechat.common.entity.button;

/**
 * 按钮-基础类
 * 描述：所有的按钮需继承本类
 * @author Administrator
 *
 */
public class Button {
    protected final String VIEW = "view";
    protected final String CLICK = "click";
    // 按钮名称
    private String name;
    // 按钮类型
    private String type;
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

}
