package com.zfsoft.hrm.core.tag.page;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zfsoft.common.query.QueryModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.util.base.StringUtil;

/** 
 * 分页标签
 * @author jinjj
 * @date 2012-11-7 下午05:33:01 
 *  
 */
public class PageTag extends TagSupport {

	private static final long serialVersionUID = -3393170816698307641L;
	private PageList<Object> pageList;
	private String function;
	private Object query;
	private String queryName;
	
	@Override
	public int doEndTag() throws JspException {
		try{
			if(pageList == null){
				throw new Exception("pageList is null, create Pagination error");
			}
			if(StringUtil.isEmpty(queryName)){
				queryName = "query";
			}
			String val = "";
			if(query != null && query instanceof QueryModel){
				val = new PageModelHtml(pageList.getPaginator(),function,queryName).generator();
			}else{
				val = new PageHtml(pageList.getPaginator(),function,queryName).generator();
			}
			pageContext.getOut().write(val);
		}catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	@Override
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	public void setPageList(PageList<Object> pageList) {
		this.pageList = pageList;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public void setQuery(Object query) {
		this.query = query;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

}
