package com.zfsoft.hrm.baseinfo.dyna.html;

import java.util.Date;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.hrm.file.util.AttachementUtil;
import com.zfsoft.orcus.lang.TimeUtil;
import com.zfsoft.util.base.StringUtil;

/**
 * 动态类可视属性解析器
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-19
 * @version V1.0.0
 */
public class ViewParse {
	/**
	 * 解析
	 * @return 解析后得到的内容
	 */
	public static String parse(InfoProperty property, Object value) {

		String result = "";
		
		if( property == null ) {
			return value.toString();
		}
		
		if ( Type.COMMON.equals( property.getFieldType() ) ) {
			result = commonParser(property,value);
		} else if ( Type.LONG_STR.equals( property.getFieldType() ) ) {
			result = longStrParser(property,value);
		} else if ( Type.CODE.equals( property.getFieldType() ) ) {
			result = codeParser(property,value);
		} else if (Type.DATE.equals(property.getFieldType())) {
			result = dateParser(property, value);
		} else if (Type.MONTH.equals(property.getFieldType())) {
			result = dateParser(property, value);
		} else if (Type.YEAR.equals(property.getFieldType())) {
			result = dateParser(property, value);
		} else if ( Type.PHOTO.equals( property.getFieldType() ) ) {
			result = imageParser(property, value);
		} else if ( Type.IMAGE.equals( property.getFieldType() ) ) {
			result = imageParser(property, value);
		} else if ( Type.SIGLE_SEL.equals( property.getFieldType() ) ) {
			result = sigleParser(property,value);
		} else if (Type.NUMBER.equals(property.getFieldType())){
			result = commonParser(property, value);
		} else if (Type.FILE.equals(property.getFieldType())){
			result = fileParser(property, value);
		}else {
			result = commonParser(property, value);//默认转换
		}
		
		return result;
	}
	/**
	 * 附件下载
	 * @param value 值
	 * @param value2 
	 * @return
	 */
	private static String fileParser(InfoProperty property, Object value) {
		String pName = property.getFieldName();
		String dName = "";
		if(value==null){
			return null;
		}
		Attachement attachement=AttachementUtil.getAttachementByGuid(value.toString());
		if(attachement!=null){
			dName=attachement.getName();
		}
		String src="";
		if(ActionContext.getContext()!=null){
			src=ServletActionContext.getRequest().getContextPath()
				+"/file/attachement_download.html?model.guId="+value;
		}
		String out = "<a id='" + pName + "' name='" + pName + "' href='"+src+"'>"+dName+"</a>";
		return out;
	}

	/**
	 * 单行文本解析
	 * @param value 值
	 * @param value2 
	 * @return
	 */
	private static String commonParser( InfoProperty property,Object value ) {
		if ( value == null ) {
			value = "";
		}
		value = value.toString().replaceAll("&","&amp;");
		value = value.toString().replaceAll("<","&lt;");
		value = value.toString().replaceAll(">","&gt;");
		value = value.toString().replaceAll("&lt;br/&gt;","<br/>");
		return value.toString();
	}
	
	/**
	 * 多行文本解析
	 * @param value 值
	 * @param value2 
	 * @return
	 */
	private static String longStrParser(InfoProperty property,Object value  ) {
		return commonParser(property,value);
	}
	
	
	/**
	 * 日期解析
	 * @param value 值
	 * @param format 日期格式，如：yyyy-MM
	 * @return
	 */
	private static String dateParser(InfoProperty property,Object value ) {
		String time = commonParser(property,value);
		
		if ( "".equals( time ) ) {
			return time;
		}
		if(value instanceof String ){
			return TimeUtil.format(value.toString(),property.getTypeInfo().getFormat());
		}
		if(property.getTypeInfo() == null || StringUtil.isEmpty(property.getTypeInfo().getFormat())){
			return TimeUtil.format((Date)value,"yyyy-MM-dd");
		}else{
			return TimeUtil.format((Date)value,property.getTypeInfo().getFormat());
		}
	}
	
	private static String imageParser(InfoProperty property, Object value) {

		String pName = property.getFieldName();
		String type="photo";
		String over="";
		if (Type.IMAGE.equals(property.getFieldType())) {
			type="image";
			over="onmouseover=\"magnifyImg(this)\"";
		}
		String src="";
		if(ActionContext.getContext()!=null){
			src=ServletActionContext.getRequest().getContextPath()+"/file/file_"+type+".html?fileGuid=" + String.valueOf( value )+"&date="+new Date().getTime();
		}
		String style = "max-width:"+(property.getWidth()-10)+"px; max-height:"+(property.getHeight()-10)+"px;";
		String out = "<table style='table-layout: fixed;text-align: center; width:"+property.getWidth()+"px;height:"+property.getHeight()+"px' ><tr><td style='padding:0;width:"+property.getWidth()+"px;height:"+property.getHeight()+"px'>"
				+ "<img id='" + pName + "' name='" + pName + "' "+over 
				+ " src='"+src
				+ "' style='"+style+"'/></td></tr></table>"
				+ "<div><a href='#' style='color:#074695' onclick='showCommImage(this);'>展示原图</a></div>";
		return out;
	}
	/**
	 * 代码解析
	 * @param value 值
	 * @param codeTable 代码库
	 * @return
	 */
	private static String codeParser(InfoProperty property,Object value ) {
		if( property.getCodeId() == null || "".equals( property.getCodeId() ) ) {
			return commonParser(property, value);
		}
		if( value == null || value.toString().equals("")){
			return commonParser(property, value);
		}
		
		return CodeUtil.getItemValue2(property.getCodeId(), value.toString());
	}
	
	/**
	 * 是/否 解析
	 * @param value 值
	 * @return
	 */
	private static String sigleParser(InfoProperty property,Object value ) {
		if ( value == null ) {
			return "否";
		}
		
		value = value.toString();
		
		if( "1".equals( value ) ) {
			return "是";
		}
		
		if( "-1".equals( value ) ) {
			return "审核拒绝";
		}
		
		if( "2".equals( value ) ) {
			return "审核通过";
		}
		
		return "否";
	}
}
