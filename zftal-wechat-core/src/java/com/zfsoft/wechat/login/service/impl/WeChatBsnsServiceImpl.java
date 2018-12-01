package com.zfsoft.wechat.login.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.wechat.login.service.IWeChatBsnsService;

/**
 * 
 * @author ChenMinming
 * @date 2015-8-24
 * @version V1.0.0
 */
public class WeChatBsnsServiceImpl implements IWeChatBsnsService {
	
	private ILoginService loginService;

	@Override
	public boolean checkAccount(String account, String password) {
		LoginModel model = new LoginModel();
		model.setYhm(account);
		model.setMm(password);
		try{
			return loginService.cxYhxx(model)!=null;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}

	@Override
	public boolean checkLogin(HttpServletRequest request) {
		return request.getSession().getAttribute(BaseAction.USER_INFO_KEY)!=null;
	}

	@Override
	public boolean login(String account, HttpServletRequest request) {
		LoginModel model = new LoginModel();
		model.setYhm(account);
		
		try{
			return loginService.cxYhxxSso(model, request.getSession())!=null;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 设置
	 * @param loginService  
	 */
	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

}
