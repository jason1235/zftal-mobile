package com.zfsoft.hrm.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.hrm.common.Message;

/** 
 * @ClassName: AjaxExceptionInterceptor 
 * @Description: 
 * @author jinjj
 * @date 2012-5-24 下午05:22:19 
 *  
 */
public class AjaxExceptionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = -3722179973134636629L;
	Log log = LogFactory.getLog(AjaxExceptionInterceptor.class);
	/**
	 *@see com.opensymphony.xwork2.interceptor.AbstractInterceptor#intercept(com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result;   
	    try {   
	        //先执行action，如果action中抛出异常，进入catch块，没有异常直接返回   
	        result = invocation.invoke();   
	    } catch (Exception e) {   
	    	ValueStack vs = ActionContext.getContext().getValueStack();
	    	HttpServletRequest req = ServletActionContext.getRequest();
	    	String flag = req.getHeader("X-Requested-With");
	    	if(!StringUtils.isEmpty(flag)&&flag.equalsIgnoreCase("XMLHttpRequest")){
	    		log.debug("catch exception:"+e.getMessage());
	    		e.printStackTrace();
	    		Message msg = new Message(false,e.getMessage());
	    		if( e instanceof DuplicateKeyException){
	    			msg.setText("该数据已存在");
	    		}else if( e instanceof DataAccessException){
	    			msg.setText("数据操作异常");
	    		}
	    		vs.set("data", msg);
	    		return "data";
	    	}else{
	    		log.debug("exception passed,not a ajax request:"+e.getMessage());
	    		e.printStackTrace();
	    		throw e;
	    	}
	    }   
	    return result;   
	}  
}
