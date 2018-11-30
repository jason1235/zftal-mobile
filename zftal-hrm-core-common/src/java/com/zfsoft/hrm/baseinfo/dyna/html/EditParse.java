package com.zfsoft.hrm.baseinfo.dyna.html;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.zfsoft.common.system.BaseHolder;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.file.entity.Attachement;
import com.zfsoft.hrm.file.util.AttachementUtil;
import com.zfsoft.orcus.lang.TimeUtil;
/**
 * 动态可编辑属性解析器
 * @author <a href="mailto:shenluwei@126.com">沈鲁威</a>
 * @since 2012-6-19
 * @version V1.0.0
 */
public class EditParse {

	/**
	 * 解析
	 * 
	 * @return 解析后得到的内容
	 */
	public static String parse(InfoProperty property, Object value) {

		String result = "";

		if (property == null) {
			return value.toString();
		}
try{
		if (Type.COMMON.equals(property.getFieldType())) {
			result = commonParser(property, value);
		} else if (Type.LONG_STR.equals(property.getFieldType())) {
			result = longStrParser(property, value);
		} else if (Type.CODE.equals(property.getFieldType())) {
			result = codeParser(property, value);
		} else if (Type.DATE.equals(property.getFieldType())) {
			result = dateParser(property, value);
		} else if (Type.MONTH.equals(property.getFieldType())) {
			result = dateParser(property, value);
		} else if (Type.YEAR.equals(property.getFieldType())) {
			result = dateParser(property, value);
		} else if (Type.PHOTO.equals(property.getFieldType())) {
			result = imageParser(property, value);
		} else if (Type.IMAGE.equals(property.getFieldType())) {
			result = imageParser(property, value);
		} else if (Type.SIGLE_SEL.equals(property.getFieldType())) {
			result = sigleParser(property, value);
		} else if (Type.NUMBER.equals(property.getFieldType())){
			result = commonParser(property, value);
		} else if (Type.FILE.equals(property.getFieldType())){
			result = fileParser(property, value);
		}else if (Type.CREATOR.equals(property.getFieldType())){
			result = ViewParse.parse(property, value);//暂不提供界面更改创建者
		}else {
			result = commonParser(property, value);//默认转换
		}
	}catch(Exception e){
		result = commonParser(property, value);
	}
		return result;
	}

	/**
	 * 单行文本解析
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	private static String commonParser(InfoProperty property, Object value) {
		
		if (value==null||"".equals(value.toString().trim())) {
			value=property.getDefaultValue();
		}
		if (value == null) {
			value = "";
		}
		
		value = value.toString().replaceAll("&","&amp;");
		value = value.toString().replaceAll("<","&lt;");
		value = value.toString().replaceAll(">","&gt;");

		String pName = property.getFieldName();
		StringBuilder out = new StringBuilder();
		out.append("<input id=\"" + pName + "\" name=\"" + pName + "\"");
		out.append(" type=\"text\" class=\"text_nor\" style=\"width:  "+property.getWidth()+"px;\"");
		out.append(" maxlength=\""+property.getFieldLen()+"\"");
		out.append(" value=\"" + value.toString()+"\"");
//		if(property.getNeed()){
//			out.append(" onfocus=\"tip(this)\"" );
//			out.append(" onblur=\"validate(this)\"" );
//		}
		out.append(" />");

		return out.toString();
	}

	/**
	 * 多行文本解析
	 * 
	 * @param value
	 *            值
	 * @return
	 */
	private static String longStrParser(InfoProperty property, Object value) {
		
		if (value==null||"".equals(value.toString().trim())) {
			value=property.getDefaultValue();
		}
		if (value == null) {
			value = "";
		}
		value = value.toString().replaceAll("&","&amp;");
		value = value.toString().replaceAll("<","&lt;");
		value = value.toString().replaceAll(">","&gt;");
		value = value.toString().replaceAll("&lt;br/&gt;","\n");
		String pName = property.getFieldName();
		StringBuilder out = new StringBuilder();
		out.append("<textarea id=\"" + pName + "\" name=\"" + pName + "\"");
		out.append(" style=\"width: "+property.getWidth()+"px;\" rows=\"5\" ");
//		if(property.getNeed()){
//			out.append(" onfocus=\"tip(this)\"" );
//			out.append(" onblur=\"validate(this)\"" );
//		}
		out.append(">" + value.toString() +"</textarea>");
		return out.toString();
	}

	/**
	 * 日期解析
	 * 
	 * @param value
	 *            值
	 * @param format
	 *            日期格式，如：yyyy-MM
	 * @return
	 */
	private static String dateParser(InfoProperty property, Object value) {

		
		if (value==null||"".equals(value.toString().trim())) {
			value=property.getDefaultValue();
		}
		if (value == "") {
			value = null;
		}
		String pName = property.getFieldName();
		StringBuilder out = new StringBuilder();
		out.append("<input id=\"" + pName+System.currentTimeMillis() + "\" name=\"" + pName + "\"");
		out.append(" class=\"Wdate\" style=\"width:  "+property.getWidth()+"px;\"");
		out.append("onfocus=\"WdatePicker({dateFmt:'"+property.getTypeInfo().getFormat()+"'})\"" + " value=\"" );
		if(value instanceof String){
			out.append(TimeUtil.format(value.toString(),  property.getTypeInfo().getFormat()) + "\" ");
		}else{
			out.append(TimeUtil.format((Date)value, property.getTypeInfo().getFormat()) + "\" ");
		}
		out.append("/>");

		return out.toString();
	}

	private static String imageParser(InfoProperty property, Object value) {

		if (value == null) {
			value = "";
		}
		String fileName = property.getFieldName();
		String pName = property.getFieldName()+System.currentTimeMillis() + String.valueOf(value);
		//String style = "style='max-width:"+(property.getWidth()-10)+"px; max-height:"+(property.getHeight()-10)+"px;'";
		int proportion = property.getWidth()/property.getHeight();
		String style = "";
		String outStyle = "";
		if(proportion == 2){
			style = "style='width:200px; height:100px;'";
			outStyle = "width:210px; height:110px;";
		}else{
			style = "style='width:100px; height:100px;'";
			outStyle = "width:110px; height:110px;";
		}
		
		//String style = "style='width:100px; height:100px;'";
		
		if(pName.equals("zfz")){
			style=" width='277px' height='176px'>";
		}
		String type="photo";
		if (Type.IMAGE.equals(property.getFieldType())) {
			type="image";
		}
		
		String src="";
		if(ActionContext.getContext()!=null){
			src=ServletActionContext.getRequest().getContextPath()+"/file/file_"+type+".html?fileGuid="+value.toString();
		}
		String filetypename = value == "" ? "未上传文件" : "重新上传文件";
		String out =
			"<div class='img_"+pName+"' name='"+type+"'>                                                                                                                                   "+
			"    <table class='img_content_"+pName+"' style='table-layout: fixed;text-align: center; "+outStyle+"' ><tr><td style='padding:0;"+outStyle+"'>             "+
			"     <img id='image_"+pName+"' src=\""+src+"\" "+style+"  /></td></tr></table>                                                                                            		   "+
			"    <div id='p_out_"+pName+"' style=' width:100px; height:6px; margin:10px 0 0 0; padding:0px; font-size:6px; border:solid #6b8e23 0px;'>                  				   "+
			"     <div id='p_in_"+pName+"' style=' width:0%;  height:100%;  background-color:#6b8e23;  margin:0; padding:0; '></div>                                    				   "+
			"    </div>                                                                                                                                         						   "+
			"<div  style=\"overflow:hidden;text-overflow:ellipsis;white-space:nowrap;height:60px;\"> <a href='#' class='hrea'>选择文件"+
			"<input id='imageCommitBtn_"+pName+"' name='imageCommit_"+pName+"' type='file' value='浏览' class='image'"+
			"onchange=\"getImage(this,'"+pName+"','"+property.getSize()+"','"+proportion+"')\"/>"+
			"</a>"+
			"<font name='imagetypename' color='green' >" + filetypename + "</font>"+
			//+"<span >（文件大小不要超过"+property.getSize()+"KB， 格式为[jpg, gif, png, bmp]）</span>"+
			"</div>"+
			//"    <input id='imageCommitBtn_"+pName+"' name='imageCommit_"+pName+"' type='file' style='width:200px;'" +
			//"		onchange=\"getImage(this,'"+pName+"','"+property.getSize()+"','"+property.getWidth()+"')\"/>                              						"+
			"<span style='color:red;display:none;' class='errorspan' id='imageError'>错误：</span>" +
			"  <div style='display:none;'><iframe name='uploadimage_iframe_"+pName+"' frameborder='0' style='border:none; width:0; height:0;' src='about:blank' ></iframe> </div>										   "+   
			"</div>                                                                                                                                                 					   "+
			"<input type=\"hidden\" id=\"" + pName + "\" name=\"" + fileName + "\"" + "value=\""+value.toString()+"\" />";
		return out;
	}
	
	private static String getImageHost() {
		String url = BaseHolder.getPropertiesValue("suploadPath");
		if (url == null) {
			return "/";
		}
        url = url.replace("\\", "/");
        if (!url.endsWith("/")) {
        	url += "/";
        }
		return url;
	}
	
	/**
	 * 附件下载
	 * @param value 值
	 * @param value2 
	 * @return
	 */
	private static String fileParser(InfoProperty property, Object value) {
		String fileName = property.getFieldName();
		String pName = property.getFieldName()+System.currentTimeMillis();
		String dataValue = "";
		String dName = "";
		String out = "当前文件：";
		String filetypename = "重新上传文件";
		if(value==null){
			filetypename = "未上传文件";
			out += "<div id='fileHref'> 无  </div>";
		}else{
			dataValue=value.toString();
			Attachement attachement=AttachementUtil.getAttachementByGuid(value.toString());
			if(attachement!=null){
				dName=attachement.getName();
			}
		
			String src="";
			if(ActionContext.getContext()!=null){
				src=ServletActionContext.getRequest().getContextPath()
					+"/file/attachement_download.html?model.guId="+value;
			}
		 	out += "<div id='fileHref'> <a href='"+src+"' id='fileHref'>"+dName+"</a> </div>";
		}
		String path = getImageHost();
		out += 
			
			"<div class='file_"+pName+"'>                                                                                                                    "+
			"        <div id='p_out_"+pName+"' style=' width:160px; height:6px; margin:10px 0 0 0; padding:0px; font-size:6px; border:solid #6b8e23 0px;'>   "+
			"           <div id='p_in_"+pName+"' style=' width:0%;  height:100%;  background-color:#6b8e23;  margin:0; padding:0; '></div>                   "+
			"        </div>                                                                                                                                  "+
			
			"<div  style=\"overflow:hidden;text-overflow:ellipsis;white-space:nowrap;height:60px;\"> <a href='#' class='hrea'>选择文件"+
			"<input id='fileCommitBtn_"+pName+"' name='fileCommit_"+pName+"' type='file' value='浏览' class='image'"+
			"onchange=\"getFile(this,'"+pName+"','"+property.getSize()+"','" + path +"','"+property.getFileType()+"')\"/>"+
			"</a><font name='filetypename' color='green' >" + filetypename + "</font></div>"+
			//"        <input name='fileCommit_"+pName+"' type='file' id='fileCommitBtn_"+pName+"' style='width: "+property.getWidth()+"px;' 				 "+
			//"		 onchange=\"getFile(this,'"+pName+"','"+property.getSize()+"','" + path +"')\"/>  					 "+
			"<span style='color:red;display: none;' id=\"fileError\">错误：</span> "+
			"   <div style='display:none;'><iframe frameborder='0' border:none; width:0; height:0;' name='uploadfile_iframe_"+pName+"' src='about:blank' ></iframe></div> "+   
			"</div>                                                                                                                                          "+
			"<input type=\"hidden\" id=\"" + pName + "\" name=\"" + fileName + "\"" + "value=\""+dataValue+"\" />"+
			"<span >（文件大小不要超过"+property.getSize()+"MB ，格式为["+property.getFileType()+"]）</span>";
		return out;
	}
	
	/**
	 * 代码解析
	 * 
	 * @param value
	 *            值
	 * @param codeTable
	 *            代码库
	 * @return
	 */
	private static String codeParser(InfoProperty property, Object value){
		if(CodeUtil.getCatalog(property.getCodeId())==null){
			throw new RuntimeException();
		}
		if (value==null||"".equals(value.toString().trim())) {
			value=property.getDefaultValue();
		}
		String viewStyle = property.getViewStyle();
		if(ViewType.SELECT.getKey().equals(viewStyle)){
			return codeSelectParser(property, value);
		}
		if(ViewType.CHOOSE.getKey().equals(viewStyle)){
			return codeSigleParser(property, value);
		}
		else{
			return codeDefaultParser(property, value);
		}
	}
	/**
	 *  代码解析 - 默认展示
	 * @param property
	 * @param value
	 * @return
	 */
	private static String codeDefaultParser(InfoProperty property, Object value) {
		if(StringUtils.isEmpty(property.getCodeId())){
			return "";
		}
		String itemValue = "";
		if (value == null || value.toString().equals("")) {
			value = "";
		}else{
			itemValue = CodeUtil.getItemValue(property.getCodeId(), value.toString());
		}

		int inputWidth = property.getWidth();
		StringBuffer out = new StringBuffer(1024);
		out.append("<input type=\"hidden\" id=\"" + property.getFieldName() + "\" name=\""
				+ property.getFieldName() + "\" value=\"" + value.toString() + "\" />");
		out.append("<input type=\"text\"  class=\"text_nor text_sel\" value=\"" + StringUtils.defaultString(itemValue)  + "\"");
		out.append(" style=\"width: " + inputWidth + "px;\" ");
		out.append(" readonly=\"readonly\" onclick=\"codePicker(this, '" + property.getCodeId() + "');\" />");
		return out.toString();
	}
	/**
	 *  代码解析 - 下拉菜单
	 * @param property
	 * @param value
	 * @return
	 */
	private static String codeSelectParser(InfoProperty property, Object value) {
		List<Item> items = CodeUtil.getChildren(property.getCodeId(), null);
		StringBuilder sb = new StringBuilder();
		sb.append("<select name='"+property.getFieldName()+"' style=\"width: " + property.getWidth()
				+ "px;\" ><option value=''>--请选择--</option>");
		for (Item item : items) {
			if (0!=item.getDumped()) {
				continue;
			}
			sb.append("<option value='"+item.getGuid()+"'");
			if (item.getGuid().equals(value)) {
				sb.append(" selected='selected'");
			}
			sb.append(">");
			sb.append(item.getDescription());
			sb.append("</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
	/**
	 *  代码解析 - 单选框展示
	 * @param property
	 * @param value
	 * @return
	 */
	private static String codeSigleParser(InfoProperty property, Object value) {
		List<Item> items = CodeUtil.getChildren(property.getCodeId(), null);
		StringBuilder sb = new StringBuilder();
		for (Item item : items) {
			if (0!=item.getDumped()) {
				continue;
			}
			sb.append("<input type=\"radio\" ");
			sb.append(" id=\"" + property.getFieldName()+"\"");
			sb.append(" name=\"" + property.getFieldName() + "\"");
			sb.append(" value=\""+item.getGuid()+"\"");
			if (item.getGuid().equals(value)) {
				sb.append(" checked ");
			}
			sb.append(" />"+item.getDescription());
		}
		return sb.toString();
	}

	/**
	 * 是/否
	 * 
	 * @param property
	 * @param value
	 * @return
	 */
	private static String sigleParser(InfoProperty property, Object value) {
		String checkY = "";
		String checkN = "";

		if (value==null||"".equals(value.toString().trim())) {
			value=property.getDefaultValue();
		}
		
		if (value != null && value.equals("1")) {
			checkY = "checked";
		} else {
			checkN = "checked";
		}

		String out = "<input type=\"radio\" id=\"" + property.getFieldName() + "\""
				+ " name=\"" + property.getFieldName() + "\" value=\"1\" " + checkY
				+ " />是" + "<input type=\"radio\" id=\"" + property.getFieldName()
				+ "\"" + " name=\"" + property.getFieldName() + "\" value=\"0\" "
				+ checkN + " />否";

		return out;
	}
}
