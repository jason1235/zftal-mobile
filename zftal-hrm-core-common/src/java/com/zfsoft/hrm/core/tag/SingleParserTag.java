package com.zfsoft.hrm.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 是/否类型解析
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-25
 * @version V1.0.0
 */
public class SingleParserTag extends TagSupport {

	private static final long serialVersionUID = -6398759202980378166L;
	
	private String falseText = "<font class=\"red\">否</font>";
	
	private String trueText = "是";
	
	private Object value;
	
	@Override
	public int doEndTag() throws JspException {
		
		return EVAL_PAGE;
	}
	
	@Override
	public int doStartTag() throws JspException {
		boolean res = false;
		
		if( value instanceof Boolean ) {
			res = (Boolean) value;
		}
		
		if( value instanceof String ) {
			System.out.println( "String" );
		} 
		
		if( value instanceof Integer ) {
			System.out.println( "Integer" );
		}
		
		try {
			pageContext.getOut().write( res ? trueText : falseText );
		} catch ( IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}
	
	public void setFalseText(String falseText) {
		this.falseText = falseText;
	}

	public void setTrueText(String trueText) {
		this.trueText = trueText;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
