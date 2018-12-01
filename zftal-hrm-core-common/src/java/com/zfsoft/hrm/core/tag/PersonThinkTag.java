package com.zfsoft.hrm.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.zfsoft.util.base.StringUtil;

/**
 * 
 * @author ChenMinming
 * @date 2015-6-19
 * @version V1.0.0
 */
public class PersonThinkTag extends TagSupport {

	private static final long serialVersionUID = 2581333540472829854L;
	
	private String id;
	private String selectFunction;
	
	private String width 	= "";		//宽度
	private  static String DEFAULT_WIDTH = "200px";
	private String dept;
	private String maxItemSize;
	
	@Override
	public int doEndTag() throws JspException {
		
		return EVAL_PAGE;
	}
	
	@Override
	public int doStartTag() throws JspException {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("<script type='text/javascript'>");
			sb.append("$(function(){var caches = {};");
			sb.append("$('#"+id+"' ).autocomplete({");
			sb.append("source: function(request,response){");
			sb.append("var key=$.trim(request.term);");
			sb.append("if(key!=''){if(key in caches){response(caches[key]);return false;}");
			sb.append("$.ajax({type:'post',");
			sb.append("url:_path+'/normal/overallInfo_userListScopeThink.html',");
			sb.append("dataType:'json',");
			sb.append("data:'term='+key");
			if(StringUtil.isNotEmpty(dept)){
				sb.append("+'&dept="+dept.toUpperCase()+"'");
			}
			if(StringUtil.isNotEmpty(maxItemSize)){
				sb.append("+'&maxItemSize="+maxItemSize+"'");
			}
			sb.append(",cache:true,success:function(data){caches[key]=data;response(data);}});}}");
			sb.append(",minLength: 1,select: function( event, ui ) {");
			sb.append(selectFunction+"(ui.item);");
			sb.append(" return false;}");
			sb.append("}).data('ui-autocomplete')._renderItem = function( ul, item ) {");
			sb.append("return $('<li>').append( '<a>' + item.userName+'('+item.userId+') '");
			sb.append("+item.departmentName+'('+item.departmentId+') '+item.statusName");
			sb.append("+'('+item.status+') ' + '</a>' ).appendTo( ul );};});");
			sb.append("</script>");
			sb.append("<style>.ui-autocomplete{z-index:12001;width: 500px}</style>");
			sb.append("<input type='text' style='width: ");
			sb.append(StringUtil.isEmpty(width)?DEFAULT_WIDTH:width);
			sb.append("' id='"+id+"' name='xm'/>");
			pageContext.getOut().write(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}
	

	/**
	 * 设置
	 * @param name 
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 设置
	 * @param selectFunction 
	 */
	public void setSelectFunction(String selectFunction) {
		this.selectFunction = selectFunction;
	}

	/**
	 * 设置
	 * @param width 
	 */
	public void setWidth(String width) {
		this.width = width;
	}

	/**
	 * 设置
	 * @param dept 
	 */
	public void setDept(String dept) {
		this.dept = dept;
	}

	/**
	 * 设置
	 * @param maxItemSize 
	 */
	public void setMaxItemSize(String maxItemSize) {
		this.maxItemSize = maxItemSize;
	}

}
