package com.zfsoft.wechat.common.entity.button;


/**
 * 复杂按钮-父按钮
 * 描述：包含有二级菜单项的一级菜单。
 * @author Administrator
 *
 */
public class ComplexButton extends Button {
    // 子按钮集合
    private Button[] sub_button;

    /**
     * @return the sub_button
     */
    public Button[] getSub_button() {
        return sub_button;
    }

    /**
     * @param sub_button the sub_button to set
     */
    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }

}
