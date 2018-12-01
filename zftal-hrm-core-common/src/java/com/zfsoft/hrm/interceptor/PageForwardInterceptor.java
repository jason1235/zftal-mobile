package com.zfsoft.hrm.interceptor;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.dao.daointerface.IGnmkDao;
import com.zfsoft.util.base.StringUtil;

/**
 * 页面跳转拦截器，用于修改result返回页面。
 * 如：学校访问该学校代码下页面，若找不到则访问通用页面
 * @author Penghui.Qu
 *
 */
public class PageForwardInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		
		//根据请求路径加载当前菜单位置
		HttpServletRequest request = ServletActionContext.getRequest();
		String url = request.getRequestURL().toString();
		
		String paramter = request.getQueryString();
		if (!StringUtil.isEmpty(paramter)){
			url+="?"+paramter;
		}		
		
		String contextPath = request.getContextPath();				
		String path = url.substring(url.indexOf(contextPath)+contextPath.length());
		IGnmkDao dao = (IGnmkDao) ServiceFactory.getService("gnmkDao");
		List<HashMap<String, String>> list=dao.getCurrentMenu(path);
		HashMap<String,String> map=null;
		if(list!=null&&list.size()>0){
			map =list.get(0);
		}

		ValueStack stack = ActionContext.getContext().getValueStack();
		if (map != null){
			stack.set("currentMenu", map.get("CURRENTMENU"));
		}
		return  invocation.invoke();
	}

}