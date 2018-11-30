package com.zfsoft.util.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 策略模式接口
 */
public interface IAuthCode {
    /**
     * 生成验证码
     */
    public void code(HttpServletRequest request, HttpServletResponse response);
}
