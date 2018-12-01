package com.zfsoft.hrm.summary.roster.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.exception.RuleException;

/** 
 * 字段选择action
 * @author jinjj
 * @date 2012-9-3 上午11:34:23 
 *  
 */
public class PropertySelectAction extends HrmAction {

	private static final long serialVersionUID = 4169478492977354620L;
	private String classid;
	private String columnid;
	
	/**
	 * 字段选择数据加载，用于字段单选控件,需要classid
	 * @return
	 * @throws Exception
	 */
	public String load() throws Exception{
		InfoClass clazz = InfoClassCache.getInfoClass(classid);
		List<InfoProperty> props = new ArrayList<InfoProperty>();
		for(InfoProperty p :clazz.getViewables()){
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
			props.add(p);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", props);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String linkedColumn()throws Exception{
		InfoClass clazz = InfoClassCache.getInfoClass(classid);
		InfoProperty p = clazz.getPropertyById(columnid);
		String type = p.getTypeInfo().getName();
		String html = generateQueryTypeHtml(type);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", html);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	private String generateQueryTypeHtml(String type){
		String result="";
		if (Type.COMMON.equals(type)) {
			result = commonParser();
		} else if (Type.LONG_STR.equals(type)) {
			result = commonParser();
		} else if (Type.CODE.equals(type)) {
			result = commonParser();
		} else if (Type.DATE.equals(type)) {
			result = dateParser();
		} else if (Type.MONTH.equals(type)) {
			result = dateParser();
		} else if (Type.YEAR.equals(type)) {
			result = dateParser();
		} else if (Type.IMAGE.equals(type)) {
			//result = imageParser(property);
			throw new RuleException("无法支持图片类型字段的查询");
		} else if (Type.SIGLE_SEL.equals(type)) {
			//result = sigleParser(property);
			throw new RuleException("无法支持单选类型字段的查询");
		} else if (Type.NUMBER.equals(type)){
			result = numParser();
		} else {
			result = commonParser();//默认转换
		}
		return result;
	}
	
	private String dateParser(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div style='display:none;'>");
		sb.append("<input type=\"radio\" name=\"config.queryType\" value=\"3\" checked/><span>范围查询</span>");
		sb.append("</div>");
		return sb.toString();
	}
	
	private String commonParser(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div style='display:none;'>");
		sb.append("<input type=\"radio\" name=\"config.queryType\" value=\"1\" checked/><span>精确查询</span>");
		sb.append("<input type=\"radio\" name=\"config.queryType\" value=\"2\" /><span>模糊查询</span>");
		sb.append("</div>");
		return sb.toString();
	}
	
	private String numParser(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div style='display:none;'>");
		sb.append("<input type=\"radio\" name=\"config.queryType\" value=\"3\" checked/><span>范围查询</span>");
		sb.append("</div>");
		return sb.toString();
	}

	public String getClassid() {
		return classid;
	}

	public void setClassid(String classid) {
		this.classid = classid;
	}

	public String getColumnid() {
		return columnid;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}
	
}
