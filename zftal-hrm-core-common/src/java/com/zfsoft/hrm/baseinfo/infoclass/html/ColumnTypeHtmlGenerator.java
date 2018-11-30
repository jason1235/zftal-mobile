package com.zfsoft.hrm.baseinfo.infoclass.html;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;

import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.dyna.html.ViewType;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.enums.VerifyType;
import com.zfsoft.util.base.StringUtil;

/** 
 * @author jinjj
 * @date 2013-1-17 上午10:24:46 
 *  
 */
public class ColumnTypeHtmlGenerator {

	private InfoProperty p;
	
	private String prefix;
	
	private int width = 200;
	
	public ColumnTypeHtmlGenerator(InfoProperty p){
		this.p = p;
	}
	
	public ColumnTypeHtmlGenerator(InfoProperty p,String prefix){
		this.p = p;
		this.prefix=prefix;
	}
	
	public String html(){
		String columnType = p.getFieldType();
		Assert.notNull(columnType, "字段类型不能为空");
		StringBuilder sb = new StringBuilder();
		if(columnType.equals(Type.CODE)){
			sb.append(codeTypeHtml());
		}else if(columnType.equals(Type.SIGLE_SEL)){
			sb.append(singleTypeHtml());
		}else if(columnType.equals(Type.NUMBER)){
			sb.append(numTypeHtml());
		}else if(columnType.equals(Type.IMAGE)||columnType.equals(Type.PHOTO)){
			sb.append(picSizeTypeHtml());
		}else if(columnType.equals(Type.FILE)){
			sb.append(fileSizeTypeHtml());
		}else if(columnType.equals(Type.COMMON)){
			sb.append(commonTypeHtml());
		}
		return sb.toString();
	}
	
	private String commonTypeHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr name='step3'>");
		sb.append(titleHtml("验证类型", true));
		sb.append("<td><select name='verifyType' >");
		for (VerifyType verifyType:VerifyType.values()) {
			if(!Type.COMMON.equals(verifyType.getType())){
				continue;
			}
			sb.append("<option value='"+verifyType+"'");
			if(p.getVerifyType()==verifyType){
				sb.append(" selected='selected'");
			}
			sb.append(">");
			sb.append(verifyType.getDesc());
			sb.append("</option>");
		}
		sb.append("</select></td><th>&nbsp;</th><td>&nbsp;</td>");
		sb.append("</tr>");
		return sb.toString();
	}
	private String codeTypeHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr name='step3'>");
		sb.append(titleHtml("引用代码库", true));
		sb.append(codeHtml("codeId", p.getCodeId()));
		sb.append(titleHtml("显示样式", false));
		sb.append("<td><select name='viewStyle' >");
		for (ViewType viewType:ViewType.values()) {
			sb.append("<option value='"+viewType.getKey()+"'");
			if(viewType.getKey().equals(p.getViewStyle())){
				sb.append(" selected='selected'");
			}
			sb.append(">");
			sb.append(viewType.getText());
			sb.append("</option>");
		}
		sb.append("</td>");
		sb.append("</tr>");
		return sb.toString();
	}
	
	private String codeHtml(String name,String value){
		StringBuilder out = new StringBuilder();
		out.append("<div>");
		out.append("<td>");
		createHiddenComponent(out,name,value);
		createViewComponent(out,value);
		out.append("</td>");
		out.append("</div>");
		return out.toString();
	}
	
	/**
	 * 生成隐藏input组件
	 * @param out
	 */
	private void createHiddenComponent(StringBuilder out,String name,String value){
		if(!StringUtil.isEmpty(prefix)){
			name=prefix+"."+name;
		}
		out.append("<input type=\"hidden\" ");
		if(!StringUtils.isEmpty(name)){
			out.append("id=\"" + name + "\" name=\""+ name+ "\"");
		}
		if(!StringUtils.isEmpty(value)){
			out.append("value=\"" + value + "\"");
		}else{
			out.append("value=\"\"");
		}
		out.append(" />");
	}
	
	/**
	 * 生成文本input组件，用于显示代码解释
	 * @param out
	 */
	private void createViewComponent(StringBuilder out,String value){
		String style = "text_nor text_sel";
		Catalog c = CodeUtil.getCatalog(value);
		out.append("<input type=\"text\"  class=\""+style+"\" ");
		if(c == null){
			out.append("value=\"\"");
		}else{
			String val = c.getName();
			if(!StringUtils.isEmpty(val)){
				out.append("value=\"" + val + "\"");
			}else{
				out.append("value=\"无法解析\"");
			}
		}
		out.append(" style=\"width: " + width + "px;\" ");
		out.append(" readonly=\"readonly\"");
		out.append("onmouseover=\"initSelectConsole(this, '/code/codeCatalog_load.html');\"");
		out.append("/>");
	}
	
	private String singleTypeHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr name='step3'>");
		sb.append(titleHtml("唯一性标识", true));
		sb.append(radioHtml("unique",p.getUnique()));
		sb.append("<th>&nbsp;</th><td>&nbsp;</td>");
		sb.append("</tr>");
		return sb.toString();
	}
	
	private String numTypeHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr name='step3'>");
		sb.append("<th>验证类型</th>");
		sb.append("<td><select name='verifyType' >");
		for (VerifyType verifyType:VerifyType.values()) {
			if(!Type.NUMBER.equals(verifyType.getType())){
				continue;
			}
			sb.append("<option value='"+verifyType+"'");
			if(p.getVerifyType()==verifyType){
				sb.append(" selected='selected'");
			}
			sb.append(">");
			sb.append(verifyType.getDesc());
			sb.append("</option>");
		}
		sb.append("</select></td>");
		sb.append(titleHtml("小数位数", true));
		sb.append(textHtml("digits", p.getDigits()+""));
		sb.append("</tr>");
		return sb.toString();
	}
	
	private String picSizeTypeHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr name='step3'>");
		sb.append(titleHtml("文件大小", true));
		sb.append(fileSizeHtml("size", p.getSize()+"","K"));
		sb.append("<th>&nbsp;</th><td>&nbsp;</td>");
		sb.append("</tr>");
		return sb.toString();
	}
	private String fileSizeTypeHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr name='step3'>");
		sb.append(titleHtml("文件大小", true));
		sb.append(fileSizeHtml("size", p.getSize()+"","M"));
		sb.append("<th>&nbsp;</th><td>&nbsp;</td>");
		sb.append("</tr>");
		return sb.toString();
	}
	
	/**
	 * 标题
	 * @param name
	 * @param need 必填
	 * @return
	 */
	private String titleHtml(String name,boolean need){
		StringBuilder sb = new StringBuilder();
		sb.append("<th>");
		if(need){
			sb.append("<span class=\"red\"></span>");
		}
		sb.append(name);
		sb.append("</th>");
		return sb.toString();
	}
	
	/**
	 * 文本控件html
	 * @param name
	 * @param value
	 * @return
	 */
	private String textHtml(String name,String value){
		if(!StringUtil.isEmpty(prefix)){
			name=prefix+"."+name;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<td>");
		sb.append("<input type=\"text\" class=\"text_nor\" name=\""+name+"\" style=\"width:200px;\" value=\""+value+"\" />");
		sb.append("</td>");
		return sb.toString();
	}
	/**
	 * 文件大小控件html
	 * @param name
	 * @param value
	 * @return
	 */
	private String fileSizeHtml(String name,String value,String unit){
		if(!StringUtil.isEmpty(prefix)){
			name=prefix+"."+name;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<td>");
		sb.append("<input type=\"text\" class=\"text_nor\" name=\""+name+"\" style=\"width:200px;\" value=\""+value+"\" />");
		sb.append(unit);
		sb.append("</td>");
		return sb.toString();
	}
	
	private String radioHtml(String name,boolean value){
		if(!StringUtil.isEmpty(prefix)){
			name=prefix+"."+name;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("<td>");
		if(value){
			sb.append("<input type=\"radio\" class=\"text_nor\" name=\""+name+"\" value=\"true\" checked/>是");
			sb.append("<input type=\"radio\" class=\"text_nor\" name=\""+name+"\" value=\"false\"/><font class=\"red\">否</font>");
		}else{
			sb.append("<input type=\"radio\" class=\"text_nor\" name=\""+name+"\" value=\"true\" />是");
			sb.append("<input type=\"radio\" class=\"text_nor\" name=\""+name+"\" value=\"false\" checked/><font class=\"red\">否</font>");
		}
		sb.append("</td>");
		return sb.toString();
	}
}
