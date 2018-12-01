package com.zfsoft.hrm.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zfsoft.hrm.baseinfo.dyna.html.SearchParse;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.config.SearchCondition;

/**
 * 查询属性解析标签
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-14
 * @version V1.0.0
 */
public class SearchPropertyTag extends TagSupport {
	
	private static final long serialVersionUID = 8776034939488609366L;

	private String type;					//类型
	
	private SearchCondition condition;		//查询条件
	
	private Object value;					//值
	
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
		InfoClass clazz = InfoClassCache.getOverallInfoClass( type );
		InfoProperty property = clazz.getPropertyByName( condition.getColumn() );
		String val = SearchParse.parse( property, condition, value );
		
		try {
			pageContext.getOut().write( val == null || "null".equals(val)? "" : val);
		} catch ( IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}

	/**
	 * 设置类型
	 * @param type 类型
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 设置查询条件
	 * @param condition 查询条件
	 */
	public void setCondition(SearchCondition condition) {
		this.condition = condition;
	}

	/**
	 * 设置值
	 * @param value 值
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
}
