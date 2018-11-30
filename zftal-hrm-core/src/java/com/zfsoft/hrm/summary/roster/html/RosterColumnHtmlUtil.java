package com.zfsoft.hrm.summary.roster.html;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.summary.roster.entity.RosterColumn;

/** 
 * @author jinjj
 * @date 2012-9-11 上午10:52:16 
 *  
 */
public class RosterColumnHtmlUtil {

	public static String parse(List<RosterColumn> columnList){
		String html = createContent(columnList);
		
		return html;
	}
	
	/**
	 * 过滤可选的信息类，踢出一对多的信息类
	 * @return
	 */
	private static List<InfoClass> getSelectableInfoClass(){
		List<InfoClass> list = InfoClassCache.getInfoClasses();
		List<InfoClass> target = new ArrayList<InfoClass>();
		target.add(InfoClassCache.getOverallInfoClass());
		for(InfoClass c : list){
			if(!c.isMoreThanOne()){
				target.add(c);
			}
		}
		return target;
	}
	
	private static String createContent(List<RosterColumn> columnList){
		StringBuilder sb = new StringBuilder();
		Map<String,RosterColumn> map = convertListToMap(columnList);
		List<InfoClass> clazzList = getSelectableInfoClass();
		for(InfoClass c : clazzList){
			if(sb.length()==0){
				sb.append("<ul>");
			}
			sb.append("<li name='title'><a href='#'>");
			sb.append(c.getName());
			sb.append("<input type='hidden' name='column.classId' value='"+c.getGuid()+"'/>");
			sb.append("</a></li>");
			sb.append(createPropertyListHtml(map,c));
		}
		if(sb.length()>0){
			sb.append("</ul>");
		}
		return sb.toString();
	}
	
	private static String createPropertyListHtml(Map<String,RosterColumn> map,InfoClass c){
		StringBuilder sb = new StringBuilder();
		for(InfoProperty p : c.getViewables()){
			if(p.getFieldType().equals(Type.IMAGE)){
				continue;
			}
			if(p.getFieldType().equals(Type.FILE)){
				continue;
			}
			if(p.getFieldType().equals(Type.SIGLE_SEL)){
				continue;
			}
			if(p.getFieldName().equals("gh")){
				continue;
			}
			if(sb.length()==0){
				sb.append("<li name='list' style='display:none;height:auto'>");
			}
			sb.append("<a href='#'>");
			sb.append("<span class=\"ico\"></span>");
			sb.append("<input type='checkbox' name='column.columnId' value='"+p.getGuid()+"' ");
			if(map.containsKey(p.getGuid())){
				sb.append("checked");
			}
			sb.append(" />");
			sb.append(p.getName());
			sb.append("</a>");
		}
		if(sb.length()>0){
			sb.append("</li>");
		}
		return sb.toString();
	}
	
	private static Map<String,RosterColumn> convertListToMap(List<RosterColumn> columnList){
		Map<String,RosterColumn> map = new HashMap<String,RosterColumn>();
		for(RosterColumn c : columnList){
			map.put(c.getColumnId(), c);
		}
		return map;
	}
}
