package com.zfsoft.wechat.login.filter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.zfsoft.wechat.common.entity.token.WeChatOauth2Token;
import com.zfsoft.wechat.common.utils.WeChatSystemHolder;
import com.zfsoft.wechat.common.utils.WeChatTokenUtil;
import com.zfsoft.wechat.login.action.WeChatLoginAction;
import com.zfsoft.wechat.login.service.IWeChatBsnsService;
import com.zfsoft.wechat.login.service.IWeChatLoginService;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.log.Role;
import com.zfsoft.common.log.User;
import com.zfsoft.common.spring.SpringHolder;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.util.base.StringUtil;
/**
 * 
 * @author ChenMinming
 * @date 2015-6-26
 * @version V1.0.0
 */
public class WeChatLoginFilter implements Filter{

	public final static String STATE="LOGIN_BY_WEIXIN";
	
	public IWeChatBsnsService weChatBsnsService = SpringHolder.getBean("weChatBsnsService", IWeChatBsnsService.class);

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest requestObj, ServletResponse responseObj,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		if(requestObj instanceof HttpServletRequest){
			request = (HttpServletRequest)requestObj;
		}
		if(responseObj instanceof HttpServletResponse){
			response = (HttpServletResponse)responseObj;
		}
		String code = request.getParameter("code");
    	String state = request.getParameter("state");
		//转换失败或者state参数不为则不进行处理
        if(!STATE.equals(state)||request==null||response==null){
        	chain.doFilter(requestObj, responseObj);
        	return;
        } 
        //Object user = request.getSession().getAttribute(BaseAction.USER_INFO_KEY);
        if(!weChatBsnsService.checkLogin(request)){
        	if(StringUtil.isEmpty(code)){
        		// TODO 非法参数报错
        		throw new RuleException("code参数不存在");
        	}
        	// 第三方用户唯一凭证֤
    		String appId = WeChatSystemHolder.getPropertiesValue("appId");
    		// 第三方用户唯一凭证密钥
    		String appSecret = WeChatSystemHolder.getPropertiesValue("appSecret");
    		
    		WeChatOauth2Token t =WeChatTokenUtil.getOauth2AccessToken(appId, appSecret, code);
    		if(t==null||StringUtil.isEmpty(t.getOpenId())){
        		// TODO code无效
        		throw new RuleException("调用微信接口获取用户信息失败！");
        	}
    		String openId=t.getOpenId();
//    		String openId="10011";
    		IWeChatLoginService weChatLoginService = 
    			SpringHolder.getBean("wechatLoginService",IWeChatLoginService.class);
    		String account = weChatLoginService.findByOpenId(openId);
    		if(account==null){
	        	String queryString = request.getQueryString();
	        	if(StringUtil.isNotEmpty(queryString)){
	        		queryString="?"+queryString;
	        	}
	        	request.setAttribute("openId", openId);
	        	request.setAttribute(WeChatLoginAction.FROM_URL_KEY, request.getRequestURL()+queryString);
	        	request.getRequestDispatcher("/WEB-INF/pages/wechat/login/login_page.jsp").forward(request,response);
        	}else{
//	    		request.getSession().setAttribute(BaseAction.USER_INFO_KEY, u);
//	    		List<String> jsdms=new ArrayList<String>();
//				for(Role role:u.getAllRoles()){
//					jsdms.add(role.getJsdm());
//				}
//				u.setJsdms(jsdms);
//				if(jsdms!=null&&!jsdms.isEmpty()){
//					request.getSession().setAttribute(u.getYhm(),u.getJsdms());
//				}
        		weChatBsnsService.login(account, request);
        	}
        }
        chain.doFilter(requestObj, responseObj);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}
}
