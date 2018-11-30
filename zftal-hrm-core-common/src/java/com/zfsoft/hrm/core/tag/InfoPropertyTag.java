package com.zfsoft.hrm.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

/**
 * 信息类属性标签
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-28
 * @version V1.0.0
 */
public class InfoPropertyTag extends TagSupport {
	
	private static final long serialVersionUID = 2330153965162710299L;

	private String classId;

	private String name;
	
	@Override
	public int doEndTag() throws JspException {
		
		return EVAL_PAGE;
	}
	
	@Override
	public int doStartTag() throws JspException {
		String out = "";
		
		try {
			InfoClass clazz = InfoClassCache.getInfoClass( classId );
			InfoProperty property = clazz.getPropertyByName( name );
			out = property.getName();
		} catch (Exception e) {
			e.printStackTrace();
			out = "异常属性";
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

	public void setName(String name) {
		this.name = name;
	}
}
