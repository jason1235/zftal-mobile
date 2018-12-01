package com.zfsoft.hrm.core.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

/**
 * 信息属性选择标签
 * @author jinjj
 * @date 2012-9-3 下午03:11:33 
 *
 */
public class InfoPropertyPickerTag extends TagSupport {
	
	private static final long serialVersionUID = 2330153965162710299L;

	private String classId;
	
	private String propId;

	private String name;
	
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
		if(!StringUtils.isEmpty(propId)){
			out.append("value=\"" + propId + "\"");
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
		InfoClass clazz = InfoClassCache.getInfoClass( classId );
		InfoProperty property = clazz.getPropertyById( propId);
		String val = "无法解析";
		if(property != null){
			val = property.getName();
		}
		out.append("<input type=\"text\"  class=\""+style+"\" ");
		if(StringUtils.isEmpty(propId)){
			out.append("value=\"\"");
		}else{
			out.append("value=\"" + val + "\"");
		}
		out.append(" style=\"width: " + width + "px;\" ");
		out.append(" readonly=\"readonly\"");
		if("true".equals(editable)){
			out.append("onclick=\"propertyPicker(this, '" + classId + "');\"");
		}
		out.append("/>");
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public void setPropId(String propId) {
		this.propId = propId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEditable(String editable) {
		this.editable = editable;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
}
