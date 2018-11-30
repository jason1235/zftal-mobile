package com.zfsoft.hrm.baseinfo.infoclass.html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zfsoft.hrm.baseinfo.dyna.html.EditParse;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.config.GeneralType;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.config.Type;
import com.zfsoft.hrm.config.TypeFactory;
import com.zfsoft.hrm.config.type.InfoPropertyType;
import com.zfsoft.hrm.config.type.VirtualFunction;
import com.zfsoft.util.base.StringUtil;

/** 
 * @author jinjj
 * @date 2013-1-16 下午04:52:35 
 *  
 */
public class VirtualColumnHtmlGenerator {

	private InfoProperty p;
	private List<Type> opList = new ArrayList<Type>();
	
	public VirtualColumnHtmlGenerator(InfoProperty p){
		this.p = p;
	}
	
	public String html(){
		StringBuilder sb = new StringBuilder();
		Type[] types = TypeFactory.getTypes( InfoPropertyType.class );
		initOpList(types);
		if(p.getVirtual()){
			sb.append(virtualColumnHtml());
		}else{
			sb.append(unVirtualColumnHtml());
		}
		return sb.toString();
	}
	
	/**
	 * 初始化字段类型选择项
	 * @param types
	 */
	private void initOpList(Type[] types){
		for (int i = 0; i < types.length; i++) {
			InfoPropertyType t = (InfoPropertyType)types[i];
			String type = t.getDataType();

			if((!p.getVirtual()) && (!StringUtil.isEmpty(p.getGuid()))) {
				
				if(p.getTypeInfo() == null){
					continue;
				}
				String dateType = p.getTypeInfo().getDataType();
				
				if( !type.equals( dateType ) ) {
					continue;
				}
			}
//			if(p.getVirtual()){//虚拟字段只允许字符型字段
//				if(!t.getDataType().equals("VARCHAR2")){
//					continue;
//				}
//			}
			opList.add(t);
		}
	}
	
	/**
	 * 虚拟字段
	 * @return
	 */
	private String virtualColumnHtml(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("<tr name='step2'>");
		sb.append(titleHtml("可显示",false));
		sb.append(radioHtml("viewable",p.getViewable()));
		sb.append(titleHtml("必填",false));
		sb.append(radioHtml("need",p.getNeed()));
		sb.append("<input type='hidden' name='editable' value='false' />");
		sb.append("</tr>");
		sb.append("<tr name='step2'>");
		sb.append(titleHtml("引用字段",true));
		List<Type> list = convertProperties();
		
		String add1;
		String add2;
		if("to_number".equals(p.getReferFunc())){
			add1=p.getRefer();
			add2="";
		}else{
			add1="";
			add2=p.getRefer();
		}
		String add="<input type='text' id='refer1' name='refer' value='"+add1+"'/>";
		sb.append(selectHtml("refer",add2,list," id='refer0' ",add));
		
		sb.append(titleHtml("引用公式",true));
		sb.append(selectHtml("referFunc",p.getReferFunc(),Arrays.asList(TypeFactory.getTypes(VirtualFunction.class))," onchange=changeReferInput() ",""));
		sb.append("</tr>");
		sb.append("<tr name='step2'>");
		sb.append(titleHtml("默认值",false));
		sb.append(textHtml("defaultValue",p.getDefaultValue(),StringUtil.isEmpty(p.getFieldName()) ? false : true));
		sb.append(titleHtml("字段类型",true));
		sb.append(selectHtml("fieldType",p.getFieldType(),opList));
		sb.append("</tr>");
		return sb.toString();
	}
	
	private List<Type> convertProperties(){
		List<Type> list = new ArrayList<Type>();
		List<InfoProperty> pList=null;
		if(p.getClassId().equalsIgnoreCase(IConstants.INFO_CATALOG_PERSON_SUMMARY)){
			pList = InfoClassCache.getOverallInfoClass().getProperties();
		}else{
			pList = InfoClassCache.getInfoClass(p.getClassId()).getProperties();
		}
		
		for(InfoProperty p : pList){
			GeneralType type = new GeneralType();
			type.setName(p.getFieldName());
			type.setText(p.getName());
			list.add(type);
		}
		return list;
	}
	/**
	 * 非虚拟字段
	 * @return
	 */
	private String unVirtualColumnHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<tr name='step2'>");
		sb.append(titleHtml("字段类型",true));
		sb.append(selectHtml("fieldType",p.getFieldType(),opList));
		sb.append(titleHtml("字段长度",true));
		sb.append(textHtml("fieldLen",p.getFieldLen()+"",StringUtil.isEmpty(p.getFieldName()) ? false : true));
		sb.append("</tr>");
		sb.append("<tr name='step2'>");
		sb.append(titleHtml("可显示",false));
		sb.append(radioHtml("viewable",p.getViewable()));
		sb.append(titleHtml("可编辑",false));
		sb.append(radioHtml("editable",p.getEditable()));
		sb.append("</tr>");
		sb.append("<tr name='step2'>");
		sb.append(titleHtml("必填",false));
		sb.append(radioHtml("need",p.getNeed()));
		//sb.append(titleHtml("默认值",false));
		//sb.append(defaultValueHtml(p));
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
			sb.append("<span class=\"red\">*</span>");
		}
		sb.append(name);
		sb.append("</th>");
		return sb.toString();
	}
	
	/**
	 * 单选
	 * @param attrName
	 * @param value
	 * @return
	 */
	private String radioHtml(String attrName,boolean value){
		StringBuilder sb = new StringBuilder();
		sb.append("<td>");
		if(value){
			sb.append("<input type=\"radio\" class=\"text_nor\" name=\""+attrName+"\" value=\"true\" checked/>是");
			sb.append("<input type=\"radio\" class=\"text_nor\" name=\""+attrName+"\" value=\"false\"/><font class=\"red\">否</font>");
		}else{
			sb.append("<input type=\"radio\" class=\"text_nor\" name=\""+attrName+"\" value=\"true\" />是");
			sb.append("<input type=\"radio\" class=\"text_nor\" name=\""+attrName+"\" value=\"false\" checked/><font class=\"red\">否</font>");
		}
		sb.append("</td>");
		return sb.toString();
	}
	
	/**
	 * 文本控件html
	 * @param attrName
	 * @param value
	 * @return
	 */
	private String textHtml(String attrName,String value,boolean onlyRead){
		StringBuilder sb = new StringBuilder();
		sb.append("<td>");
		sb.append("<input type=\"text\" class=\"form-control\" name=\""+attrName+"\"  value=\""+value+"\" ");
		sb.append(onlyRead ? "style=\"ime-mode:disabled;\" disabled=\"disabled\"" : "").append("/>"); 
		sb.append("</td>");
		return sb.toString();
	}
	
	/**
	 * 选择控件html
	 * @param attrName
	 * @param value
	 * @param opList
	 * @return
	 */
	private String selectHtml(String attrName,String value,List<Type> opList){
		return selectHtml(attrName, value, opList, "","");
	}
	
	/**
	 * 选择控件html
	 * @param attrName
	 * @param value
	 * @param opList
	 * @return
	 */
	private String selectHtml(String attrName,String value,List<Type> opList,String addParam,String addHtml){
		StringBuilder sb = new StringBuilder();
		sb.append("<td>");
//		sb.append("<select name=\""+attrName+"\" "+addParam+" style=\"width:208px;\" class=\"text_nor\">");
		sb.append("<select name=\""+attrName+"\" "+addParam+"  class=\"form-control\"");
		sb.append(StringUtil.isEmpty(value)? "" : "style=\"ime-mode:disabled;\" disabled=\"disabled\"").append(">");
		sb.append("<option value=''>请选择</option>");
		for(Type type : opList){
			sb.append("<option value=\""+type.getName()+"\"");
			if(type.getName().equals(value)){
				sb.append(" selected=\"selected\"");
			}
			sb.append(">"+type.getText()+"</option>");
		}
		sb.append("</select>");
		sb.append(addHtml);
		sb.append("</td>");
		return sb.toString();
	}
	/**
	 * 获取默认值的展示方式
	 * @param p
	 * @return
	 */
	private String defaultValueHtml(InfoProperty p){
		StringBuilder sb = new StringBuilder();
		sb.append("<td>");
		sb.append("<div id='defValIpt'>");
		if (!com.zfsoft.hrm.baseinfo.dyna.html.Type.FILE.equals(p.getFieldType()) &&
				!com.zfsoft.hrm.baseinfo.dyna.html.Type.IMAGE.equals(p.getFieldType())&&
				!com.zfsoft.hrm.baseinfo.dyna.html.Type.PHOTO.equals(p.getFieldType())) {
			String str = p.getFieldName();
			
			p.setFieldName("defaultValue");
			sb.append(EditParse.parse(p, p.getDefaultValue()));
			p.setFieldName(str);
		}
		sb.append("</div>");
		sb.append("<button id='getDefInputStyle' type='button'>" +
				"获取样式" +
				"</button>");
		sb.append("</td>");
		
		return sb.toString();
	}
}
