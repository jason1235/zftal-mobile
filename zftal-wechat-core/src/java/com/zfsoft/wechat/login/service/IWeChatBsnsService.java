package com.zfsoft.wechat.login.service;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author ChenMinming
 * @date 2015-8-24
 * @version V1.0.0
 */
public interface IWeChatBsnsService {
	boolean login(String account,HttpServletRequest response);
	boolean checkLogin(HttpServletRequest response);
	boolean checkAccount(String account,String password);
}
