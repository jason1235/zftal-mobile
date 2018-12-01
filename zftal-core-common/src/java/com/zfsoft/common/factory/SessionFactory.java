package com.zfsoft.common.factory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.zfsoft.common.log.User;


/**
 * 会话工厂：只能用于当前线程，EJB端不支持使用（以后可能支持）
 * 
 * @author Administrator
 * 
 */
public class SessionFactory {
	private SessionFactory() {

	}
	/**
	 * 常用于主进程取会话
	 * 
	 * 
	 * @return
	 */
	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();

	}

	/**
	 * 常用于主进程取请求信息
	 * 
	 * @param svrCode
	 * @return
	 */
	public static HttpServletRequest getHttpRequest() {
		return ServletActionContext.getRequest();

	}

	/**
	 * 取主机信息【ip,port,name】
	 * 
	 * @return
	 */
	public static String[] getHostInfo() {
		HttpServletRequest request = null;
		try {
			request = getHttpRequest();
		} catch (NullPointerException npEx) {
			request=null;
		}
		if (request == null) {
			return new String[] { "", "", "" };
		}
		return new String[] { request.getRemoteAddr(),
				request.getRemotePort() + "", request.getRemoteHost()};
	}

	/**
	 * 取用户权限信息[用户编号,岗位级别,保留]
	 * 
	 * @return
	 */
	@Deprecated
	public static String[] getUserInfo() {
		HttpServletRequest request = getHttpRequest();
		if (request == null) {
			return new String[] { "", "", "" };
		}
		return new String[] { "1", "1", "1" };
	}
	
	
	/**
	 * 取当前会话用户信息
	 * @return User
	 */
	public static User getUser() {
		HttpSession session = getSession();
		return (User) session.getAttribute("user");
	}

	

	/**
	 * 取请求上下文路径
	 * 
	 * @return
	 */
	public static String getContextPath() {
		HttpServletRequest request = getHttpRequest();
		if (request == null) {
			return "xsgzgl";
		}
		return request.getContextPath();
	}

}
