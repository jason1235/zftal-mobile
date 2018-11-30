package com.zfsoft.wechat.common.entity.button;


/**
 * 菜单
 * 描述：有按钮组成
 * @author Administrator
 *
 */
public class Menu {
    // 按钮集合
    private Button[] button;

    /**
     * @return the button
     */
    public Button[] getButton() {
        return button;
    }

    /**
     * @param button the button to set
     */
    public void setButton(Button[] button) {
        this.button = button;
    }

}
