package com.zfsoft.mobile.oauth.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;


import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.mobile.services.entity.OauthYhDyXt;
import com.zfsoft.mobile.services.service.IBusinessService;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.untils.ApptokenUtils;
import com.zfsoft.untils.CodeUtil;
import com.zfsoft.util.base.StringUtil;

public class OauthAction extends HrmAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4369457799071695620L;
	private IBusinessService businessService;

	public void setBusinessService(IBusinessService businessService) {
		this.businessService = businessService;
	}

	public IBusinessService getBusinessService() {
		return businessService;
	}
	/**
	 * 返回用户授权页面
	 * @return
	 */
	public String authorize(){
		return "authorize";
	}
	/**
	 * 为用户授权
	 * @return
	 */
	public String authorizeForYh(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String access_token = request.getParameter("access_token");
	    if(!ApptokenUtils.compare(access_token) || StringUtil.isEmpty(access_token)){
	    	this.setErrorMessage("app_token error，请重新登录!");
			getValueStack().set(DATA, getMessage());
			return DATA;
	    }
	    String procode = "";
	    String timeout = "";
	    String desProcode = request.getParameter("procode");
	    String destimeout = request.getParameter("timeout");
	    
	    try {
		   procode  		= CodeUtil.decode(desProcode, access_token);
		   timeout  		= CodeUtil.decode(destimeout, access_token);
		} catch (Exception e) {
		   e.printStackTrace();
		   this.setErrorMessage("app_token error，请重新登录!");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		OauthYhDyXt model = new OauthYhDyXt();
		Date date = new Date();
		long endtime = new Date(date.getTime() + 2 * 24 * 60 * 60 * 1000).getTime();
		model.setEndtime(endtime);
		model.setProcode(procode);
		ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
	    String zgh = loginServiceImpl.getStrKey(access_token);
		model.setYhid(zgh);
		if(timeout.equals("1")){
			businessService.updateOauthYhDyXt(model);
		}
		if(timeout.equals("0")){
			int count = businessService.getOauthYhDyXtCount(model);
			if(count == 1){
				businessService.updateOauthYhDyXt(model);
			}else if(count == 0){
				businessService.insertOauthYhDyXt(model);
			}else{
				try {
					throw new Exception("此用户与此系统有2条绑定的授权记录，数据有异常，需操作数据库解决，procode:"+procode+",zgh:"+zgh);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		String redirect_uri = request.getParameter("redirect_uri");
		Map<String, Object> data = new HashMap<String, Object>();
        data.put("success", true);
        boolean flag = redirect_uri.contains("?") ? true : false;
	    if(flag){
	    	redirect_uri += "&access_token="+access_token;
	    }else{
	    	redirect_uri += "?access_token="+access_token;
	    }
	    String flagURL = request.getParameter("flag");
	    if(!StringUtil.isEmpty(flagURL) && flagURL.equals(destimeout)){
	    	getValueStack().set("flagURL", "1");
	    	getValueStack().set("redirect_uri", redirect_uri);
	    	return "redirect";
	    }
        data.put("redirect_uri", redirect_uri);
        getValueStack().set(DATA, data);
		return DATA;
	}
}
