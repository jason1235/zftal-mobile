package com.zfsoft.common.action;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.cache.ICacheClient;
import com.zfsoft.common.log.User;

public class BaseAction extends ActionSupport implements ServletResponseAware,	
	ServletRequestAware, Preparable {

	private static final transient Logger log = Logger.getLogger(BaseAction.class);
	private static final long serialVersionUID = 7038570508703577185L;
	public final String FAILED = "false";// 返回失败
	public final String OPER_SAVE = "save";// 操作保存
	public static final String DATA = "data";
	public static final String QUERY = "query";
	public static final String STREAM = "stream";
	public static final String USER_INFO_KEY = "user";
	public static final String GUESTUSER_INFO_KEY = "guestUser";
	public String key = "";
	private HttpServletResponse response;
	private HttpServletRequest request;
	/**缓存实例*/
	protected ICacheClient cache;

	/**
	 * 返回值栈对象
	 */
	public ValueStack getValueStack() {
		ActionContext actionContext = ActionContext.getContext();
		return actionContext.getValueStack();
	}

	/**
	 * 返回request对象
	 */
	public HttpServletRequest getRequest() {
		return this.request;
	}

	/**
	 * 返回response对象
	 */
	public HttpServletResponse getResponse() {
		return this.response;
	}

	/**
	 * 返回session对象
	 */
	public HttpSession getSession() {
		return this.request.getSession();
	}
	
	/**
	 * 获取用户信息
	 */
	protected User getUser() {
		return (User) this.getSession().getAttribute("user");
	}	

	/**
	 * 取得Bean方法名
	 */
	private String getBeanMehtodName(String attrName) {
		String a = attrName.substring(0, 1);
		String b = attrName.substring(1, attrName.length());
		return a.toUpperCase() + b;
	}
	
	/**
	 * 公用异常处理
	 */
	protected void logException(Exception ex) {
		try {
			// 调试打印控制台-发包后可删掉
			ex.printStackTrace();
			// 日志记录异常信息
			log.error(ex);
			// request 设置错误消息，用于返回客户端
			getRequest().setAttribute("msg", ex.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 把数据模型输出前台（目前谢的导入导出有用到，等确认后决定是否保留此方法）
	 */
	public ValueStack setModelInstace(Object bean) throws Exception {
		ValueStack vs = getValueStack();
		Class cls = bean.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String name = field.getName();
			String methodName = "get" + getBeanMehtodName(name);
			Method m = null;
			try {
				m = cls.getDeclaredMethod(methodName, new Class[] {});
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}

			try {
				Object obj = m.invoke(bean, new Object[] {});
				if (obj != null) {
					vs.set(name, obj.toString());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return vs;
	}
	
	public ICacheClient getCache() {
		return cache;
	}
	@Autowired(required = false)       
	@Qualifier("newMemcachedClient") 
	public void setCache(ICacheClient cache) {
		this.cache = cache;
	}

	public void prepare() throws Exception {
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}
