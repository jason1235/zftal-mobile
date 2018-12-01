package com.zfsoft.wechat.login.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.zfsoft.wechat.common.entity.token.WeChatOauth2Token;
import com.zfsoft.wechat.common.utils.WeChatSystemHolder;
import com.zfsoft.wechat.common.utils.WeChatTokenUtil;
import com.zfsoft.wechat.login.service.IWeChatLoginService;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.Role;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.dao.entities.LoginModel;
import com.zfsoft.service.svcinterface.ILoginService;
import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author Administrator
 *
 */
public class WeChatLoginInterceptor extends AbstractInterceptor {

    /**
     * 
     */
    private static final long serialVersionUID = -4676638636142300285L;
    Log log = LogFactory.getLog(WeChatLoginInterceptor.class);
    /**
     *@see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
     */
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
    	log.info("intercept START");
        HttpServletRequest request = ServletActionContext.getRequest();
        HttpServletResponse response =  ServletActionContext.getResponse();
        
        User user = SessionFactory.getUser();
        
        if(user == null){
        	String code = request.getParameter("code");
        	String state = request.getParameter("state");
        	log.error("code="+code+",state="+state);
//        	if(StringUtil.isEmpty(state) || StringUtil.isEmpty(code)){
//        		response.sendError(500, "code参数或者state参数不存在");
//        		return null;
//        	}
        	if(StringUtil.isEmpty(state)){
        		return invocation.invoke();
        	}
        	/*if(StringUtil.isEmpty(code) && StringUtil.isEmpty(state)){
        		return invocation.invoke();
        	}*/
        	if(StringUtil.isEmpty(code)){
        		response.sendError(500, "您已拒绝授权，请自行返回");
        		return null;
        	}
        	// 第三方用户唯一凭证֤
    		String appId = WeChatSystemHolder.getPropertiesValue("appId");
    		// 第三方用户唯一凭证密钥
    		String appSecret = WeChatSystemHolder.getPropertiesValue("appSecret");
    		
    		WeChatOauth2Token t =WeChatTokenUtil.getOauth2AccessToken(appId, appSecret, code);
    		if(t==null||StringUtil.isEmpty(t.getOpenId())){
        		// TODO code无效
    			// TODO 非法参数报错
        		response.sendError(500, "获取用户信息失败！");
        		return null;
        	}
    		String openId=t.getOpenId();
    		//String openId="10000";
    		IWeChatLoginService weChatLoginService = 
    			SpringHolder.getBean("wechatLoginService",IWeChatLoginService.class);
    		String yhm = weChatLoginService.findByOpenId(openId);
    		log.error("yhm="+yhm);
    		if(StringUtil.isEmpty(yhm)){
    			ServletActionContext.getContext().getValueStack().set("openId", openId);
	        	String queryString = request.getQueryString();
	        	if(StringUtil.isNotEmpty(queryString)){
	        		queryString="?"+queryString;
	        	}
	        	ServletActionContext.getContext().getValueStack().set("wechat_jump_url", request.getRequestURL());
	        	return "login";
    		}
    		LoginModel model = new LoginModel();
			model.setYhm(yhm);
			ILoginService loginServiceImpl = (ILoginService)SpringHolder.getBean("loginService");
			User u = loginServiceImpl.cxYhxx(model);
    		if(u==null){
	        	ServletActionContext.getContext().getValueStack().set("openId", openId);
	        	String queryString = request.getQueryString();
	        	if(StringUtil.isNotEmpty(queryString)){
	        		queryString="?"+queryString;
	        	}
	        	
	        	ServletActionContext.getContext().getValueStack().set("wechat_jump_url", request.getRequestURL());
	        	return "weixinlogin";
        	}
    		request.getSession().setAttribute(BaseAction.USER_INFO_KEY, u);
    		List<String> jsdms=new ArrayList<String>();
			for(Role role:u.getAllRoles()){
				jsdms.add(role.getJsdm());
			}
			u.setJsdms(jsdms);
			request.getSession().setAttribute(u.getYhm(), u.getJsdms());
        }
        return invocation.invoke();
    }  

}
