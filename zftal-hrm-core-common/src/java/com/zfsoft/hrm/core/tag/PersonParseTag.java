package com.zfsoft.hrm.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;

/** 
 * 人员名称解析标签，将员工号解析成姓名
 * @author jinjj
 * @date 2012-8-21
 *  
 */
public class PersonParseTag extends TagSupport {

	private static final long serialVersionUID = 5505794655560018687L;

	private String code;
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		//TODO caution: 该人员解析直接读取数据库,待优化
		String val = DynaBeanUtil.getPersonName(code);
		try {
			if(StringUtils.isEmpty(val)){
				val = code;
			}
			pageContext.getOut().write(val);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
