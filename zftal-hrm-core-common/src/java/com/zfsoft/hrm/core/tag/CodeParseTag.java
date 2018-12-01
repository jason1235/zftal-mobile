package com.zfsoft.hrm.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;

/** 
 * 代码解析标签，将代码解析成代码名称或者中文含义
 * @author jinjj
 * @date 2012-8-10 上午05:56:19 
 *  
 */
public class CodeParseTag extends TagSupport {

	private static final long serialVersionUID = 5505794655560018687L;

	private String catalog;
	
	private String code;
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		String val = CodeUtil.getItemValue(catalog, code);
		try {
			pageContext.getOut().write(val);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
