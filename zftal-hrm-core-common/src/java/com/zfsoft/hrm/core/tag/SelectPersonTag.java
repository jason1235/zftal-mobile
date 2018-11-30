package com.zfsoft.hrm.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.orcus.lang.StringUtil;

/**
 * 人员选择标签
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-21
 * @version V1.0.0
 */
public class SelectPersonTag extends TagSupport {

	private static final long serialVersionUID = -3433500582902719564L;
	
	private String id 		= "";		//ID
	
	private String name 	= "";		//名字
	
	private String value 	= "";		//人员ID
	
	private boolean single 	= true;		//选择单人
	
	private String width 	= "";		//宽度
	
	private String type 	= "";		//类型
	
	private boolean hasDefault = true; 
	
	private  static String DEFAULT_WIDTH = "200px";

	/* 
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
	 */
	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	/* 
	 * (non-Javadoc)
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		try {
			String out = "<input type=\"hidden\" id=\"${id}\" name=\"${name}\" value=\"${value}\" />";
			out += "<input type=\"text\" id=\"${infoId}\" class=\"text_nor text_sel\" readonly=\"readonly\" style=\"width: ${width};\" onclick=\"${action}\" value=\"${infoValue}\" />";
			pageContext.getOut().write( replace(out) );
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	private String replace( String input ) {
		String info = DynaBeanUtil.getPersonName( value == null ? "" : value );
		String defaultValue = hasDefault?"&defaultValue='+$(this).prev().val()":"";
		input = StringUtil.replace( input, "${id}", id );
		input = StringUtil.replace( input, "${name}", name );
		input = StringUtil.replace( input, "${value}", value );
		input = StringUtil.replace( input, "${infoId}", id+"_info" );
		input = StringUtil.replace( input, "${infoValue}", info == null ? "" : info );
		input = StringUtil.replace( input, "${action}", single ? "selectPersonSingleX(this, '${type}')" : "selectPersonMultipleX(this, '${type}"+defaultValue+")" );
		input = StringUtil.replace( input, "${type}", ( type == null || "".equals(type) ) ? IConstants.INFO_CATALOG_TYPE_DEFAULT : type );
		input = StringUtil.replace( input, "${width}", ( width == null || "".equals(width) ) ? DEFAULT_WIDTH : width );
		pageContext.getSession().setAttribute("SelectPersonMultiple_gh&xm",value+"#"+info );
		return input;
	}

	/**
	 * 设置id [input:id]
	 * @param id 文本框ID
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 设置name [input:name]
	 * @param name 文本框名字
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 设置value [input:value]
	 * @param value 人员id
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * 设置是否单选
	 * @param single true：单选（默认）；false：多选 
	 */
	public void setSingle(boolean single) {
		this.single = single;
	}

	/**
	 * 设置width [input:style:width]
	 * @param width 文本框宽度
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 设置类型
	 * @param type 类型 
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 设置 是否需要在已选择的人员的基础上进行选择
	 * @param hasDefault 
	 */
	public void setHasDefault(boolean hasDefault) {
		this.hasDefault = hasDefault;
	}
	
	
}
