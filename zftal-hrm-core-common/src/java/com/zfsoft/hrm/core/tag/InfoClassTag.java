package com.zfsoft.hrm.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;

/**
 * 信息类名称解析标签
 * @author jinjj
 * @date 2012-9-28 下午02:26:33 
 *
 */
public class InfoClassTag extends TagSupport {
	
	private static final long serialVersionUID = 2330153965162710299L;

	private String classId;

	@Override
	public int doEndTag() throws JspException {
		
		return EVAL_PAGE;
	}
	
	@Override
	public int doStartTag() throws JspException {
		String out = "";
		
		try {
			InfoClass clazz = InfoClassCache.getInfoClass( classId );
			out = clazz.getName();
		} catch (Exception e) {
			e.printStackTrace();
			out = "异常信息类";
		}
		
		try {
			pageContext.getOut().write( out );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

}
