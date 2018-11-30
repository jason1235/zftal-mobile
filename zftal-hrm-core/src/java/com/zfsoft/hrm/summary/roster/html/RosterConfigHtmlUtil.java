package com.zfsoft.hrm.summary.roster.html;

import java.util.List;

import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.summary.roster.entity.RosterConfig;
import com.zfsoft.util.base.StringUtil;

/** 
 * @author jinjj
 * @date 2012-9-4 下午02:22:19 
 *  
 */
public class RosterConfigHtmlUtil {

	public static String parse(RosterConfig config,String[] params){
		InfoProperty property = config.getInfoProperty();
		String result = "";

		if (Type.COMMON.equals(property.getFieldType())) {
			result = commonParser(property,params);
		} else if (Type.LONG_STR.equals(property.getFieldType())) {
			result = commonParser(property,params);
		} else if (Type.CODE.equals(property.getFieldType())) {
			result = codeParser(property,params);
		} else if (Type.DATE.equals(property.getFieldType())) {
			result = dateParser(property,params);
		} else if (Type.MONTH.equals(property.getFieldType())) {
			result = dateParser(property,params);
		} else if (Type.YEAR.equals(property.getFieldType())) {
			result = dateParser(property,params);
		} else if (Type.IMAGE.equals(property.getFieldType())) {
			//result = imageParser(property);
			throw new RuleException("无法支持图片类型字段的查询");
		} else if (Type.SIGLE_SEL.equals(property.getFieldType())) {
			//result = sigleParser(property);
			throw new RuleException("无法支持单选类型字段的查询");
		} else if (Type.NUMBER.equals(property.getFieldType())){
			if(config.getQueryType().equals("3")){
				result = numParser(property,params);
			}else{
				throw new RuleException("无法支持的数字类型查询方式");
			}
		} else {
			result = commonParser(property,params);;//默认转换
		}

		return result;
	}
	
	public static String parse(RosterConfig config){
		return parse(config,null);
	}
	
	private static String commonParser(InfoProperty property,String[] params){
		StringBuilder out = new StringBuilder();
		out.append("<dl id=\""+property.getGuid()+"\" style=\"position:relative;\">");
		out.append("<dt style='padding:3px 0;'>"+property.getName()+"</dt>");
		out.append("<dd><ul><li>");
		out.append("<input style=\"width:180px\" class=\"text_nor\" name=\""+property.getGuid()+"\" ");
		if(params != null && params.length>0){
			out.append("value = '"+params[0]+"'");
		}
		out.append(" />");
		out.append("</li></ul></dd>");
		out.append("<a title=\"关闭\" href=\"#\" class=\"ico_close03\"></a>");
		out.append("</dl>");
		return out.toString();
	}
	
	private static String dateParser(InfoProperty property,String[] params){
		//String pName = property.getFieldName();
		String pName = property.getGuid();
		StringBuilder out = new StringBuilder();
		out.append("<dl id=\""+property.getGuid()+"\" style=\"position:relative;\">");
		out.append("<dt style='padding:3px 0;'>"+property.getName()+"<span name='type' style='display:none;'>date</span></dt>");
		out.append("<dd><ul>");
		out.append("<li>");
		out.append("开始时间  <input name=\"" + pName + "\"");
		out.append(" class=\"Wdate\" onfocus=\"WdatePicker({dateFmt:'"+property.getTypeInfo().getFormat()+"'})\" style=\"width: 150px;\"");
		if(params != null && params.length>0){
			out.append(" value='"+params[0]+"'");
		}
		out.append("/>");
		out.append("</li>");
		out.append("<li style=\"margin-left:10px\">");
		out.append("结束时间  <input name=\"" + pName + "\"");
		out.append(" class=\"Wdate\" onfocus=\"WdatePicker({dateFmt:'"+property.getTypeInfo().getFormat()+"'})\" style=\"width: 150px;\"");
		if(params != null && params.length>1){
			out.append(" value='"+params[1]+"'");
		}
		out.append("/>");
		out.append("</li>");
		out.append("</ul></dd>");
		out.append("<a title=\"关闭\" href=\"#\" class=\"ico_close03\"></a>");
		out.append("</dl>");
		return out.toString();
	}
	
	private static String codeParser(InfoProperty property,String[] params){
		StringBuilder out = new StringBuilder();
		out.append("<dl id=\""+property.getGuid()+"\" style=\"position:relative;\">");
		out.append("<dt style='padding:3px 0;'><span name='name'>"+property.getName()+"</span><span name='type' style='display:none;'>code</span></dt>");
		out.append("<dd><ul>");
		out.append(createCodeItem(property,params));
		out.append("</ul>");
		out.append("<a class=\"more_down\" href=\"#\">更多</a>");
		out.append("</dd>");
		out.append("<a title=\"关闭\" href=\"#\" class=\"ico_close03\"></a>");//注意：IE6中此关闭链接和更多链接处于同级，DOM标签解析方式有所不同
		out.append("</dl>");
		return out.toString();
	}
	
	private static String createCodeItem(InfoProperty property,String[] params){
		StringBuilder out = new StringBuilder();
		if(StringUtil.isEmpty(property.getCodeId())){
			throw new RuleException(property.getName()+"未设置对应代码库");
		}
		if(null==CodeUtil.getCatalog(property.getCodeId())){
			throw new RuleException(property.getName()+"所对应代码库不存在或已被删除");
		}
		List<Item> items = CodeUtil.getAvailableItems(property.getCodeId());
		
		for(Item item:items){
			out.append("<li>");
			out.append("<span style=\"display:none;\" >"+item.getGuid()+"</span><a href=\"#\" "+checkSelected(item.getGuid(),params)+">"+item.getDescription()+"</a>");
			out.append("</li>");
		}
		return out.toString();
	}
	
	private static String checkSelected(String guid,String[] params){
		String result = "";
		if(params != null && params.length>0){
			for(String str:params){
				if(guid.equals(str)){
					result = " class='selectedValue'";
					break;
				}
			}
		}
		return result;
	}
	
	private static String numParser(InfoProperty property,String[] params){
		//String pName = property.getFieldName();
		String pName = property.getGuid();
		StringBuilder out = new StringBuilder();
		out.append("<dl id=\""+property.getGuid()+"\" style=\"position:relative;\">");
		out.append("<dt style='padding:3px 0;'>"+property.getName()+"<span name='type' style='display:none;'>date</span></dt>");
		out.append("<dd><ul>");
		out.append("<li>");
		out.append("大于等于  <input name=\"" + pName + "\"");
		out.append(" class=\"text_nor\" style=\"width: 200px;\" maxlength='8'");
		if(params != null && params.length>0){
			out.append(" value='"+params[0]+"'");
		}
		out.append("/>");
		out.append("</li>");
		out.append("<li style=\"margin-left:10px\">");
		out.append("小于等于  <input name=\"" + pName + "\"");
		out.append(" class=\"text_nor\" style=\"width: 200px;\" maxlength='8'");
		if(params != null && params.length>1){
			out.append(" value='"+params[1]+"'");
		}
		out.append("/>");
		out.append("</li>");
		out.append("</ul></dd>");
		out.append("<a title=\"关闭\" href=\"#\" class=\"ico_close03\"></a>");
		out.append("</dl>");
		return out.toString();
	}
}
