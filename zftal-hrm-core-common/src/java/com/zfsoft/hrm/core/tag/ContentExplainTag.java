package com.zfsoft.hrm.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;

/** 
 * 内容解析标签
 * @author jinjj
 * @date 2012-8-21
 *  
 */
public class ContentExplainTag extends TagSupport {

	private static final long serialVersionUID = 5505794655560018687L;

	private String content;
	
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
//			String val = DynaBeanUtil.getPersonName(code);
			String val = content;
			val = processGh(val);
			pageContext.getOut().write(val);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 解析工号
	 * @return
	 */
	private String processGh(String content){
		String str = StringUtils.substringBetween(content, "[code]", "[:code]");
		if(StringUtils.isEmpty(str)){
			return content;
		}else{
			String[] ghs = str.split(";");
			StringBuilder sb = new StringBuilder();
			for(String gh : ghs){
				if(sb.length()>0){
					sb.append(";");
				}
				String name = DynaBeanUtil.getPersonName(gh);
				if(StringUtils.isEmpty(name)){
					sb.append(gh);
				}else{
					sb.append(name);
				}
				sb.append("("+gh+")");
			}
			content = StringUtils.replaceOnce(content, "[code]"+str+"[:code]", sb.toString());
			return processGh(content);
		}
	}
}
