package com.zfsoft.util.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 

/**
 * @desc 代理模式的实现类,传入不同的生成验证码的类来生成不同的验证码
 *
 */
public class GenerateCode {

    private static IAuthCode code = new CommonCode();//default use CommonCode

    private GenerateCode(IAuthCode c){
        code = c;
    }
    public static GenerateCode getInstance(IAuthCode c){
        return new GenerateCode(c);
    }
    /**
     * @desc 根据传入的验证码生成器来生成验证码
     * @param request
     * @param response
     * */
    public void generate(HttpServletRequest request, HttpServletResponse response){
        if(code != null){
            code.code(request,response);
        } else {
            //TODO do something
        }
    }
}
