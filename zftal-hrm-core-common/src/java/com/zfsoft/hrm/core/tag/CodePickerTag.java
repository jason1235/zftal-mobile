package com.zfsoft.hrm.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;

/** 
 * @author jinjj
 * @date 2012-8-10 上午07:37:00 
 *  
 */
public class CodePickerTag extends TagSupport {

	private static final long serialVersionUID = 4676208152739862359L;
	private String name;
	private String catalog;
	private String code;
	private String editable = "true";
	private int width = 180;
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			StringBuffer out = new StringBuffer(1024);
			out.append("<div>");
			createHiddenComponent(out);
			createViewComponent(out);
			out.append("</div>");
			pageContext.getOut().write(out.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	
	/**
	 * 生成隐藏input组件
	 * @param out
	 */
	private void createHiddenComponent(StringBuffer out){
		out.append("<input type=\"hidden\" ");
		if(!StringUtils.isEmpty(name)){
			out.append("id=\"" + name + "\" name=\""+ name+ "\"");
		}
		if(!StringUtils.isEmpty(code)){
			out.append("value=\"" + code + "\"");
		}else{
			out.append("value=\"\"");
		}
		out.append(" />");
	}
	
	/**
	 * 生成文本input组件，用于显示代码解释
	 * @param out
	 */
	private void createViewComponent(StringBuffer out){
		String style = "text_nobor";
		if("true".equals(editable)){
			style = "text_nor text_sel";
		}
		String val = CodeUtil.getItemValue(catalog, code);
		out.append("<input type=\"text\"  class=\""+style+"\" ");
		if(StringUtils.isEmpty(code)){
			out.append("value=\"\"");
		}else{
			if(!StringUtils.isEmpty(val)){
				out.append("value=\"" + val + "\"");
			}else{
				out.append("value=\"代码无法解析\"");
			}
		}
		out.append(" style=\"width: " + width + "px;\" ");
		out.append(" readonly=\"readonly\"");
		if("true".equals(editable)){
			out.append("onclick=\"codePicker(this, '" + catalog + "');\"");
		}
		out.append("/>");
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
