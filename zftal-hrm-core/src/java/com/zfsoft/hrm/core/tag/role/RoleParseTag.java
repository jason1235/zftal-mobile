package com.zfsoft.hrm.core.tag.role;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zfsoft.hrm.baseinfo.role.entity.Role;
import com.zfsoft.hrm.baseinfo.role.util.RoleUtil;

/**
 * 角色解析标签
 * @author jinjj
 * @date 2012-10-8 下午02:28:13 
 *
 */
public class RoleParseTag extends TagSupport {

	private static final long serialVersionUID = 5505794655560018687L;

	private String code;
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		Role role = RoleUtil.getRole(code);
		try {
			String val = "无法解析";
			if(role != null){
				val = role.getName();
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
