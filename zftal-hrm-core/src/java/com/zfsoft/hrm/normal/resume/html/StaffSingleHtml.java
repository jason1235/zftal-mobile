package com.zfsoft.hrm.normal.resume.html;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.entities.AncdModel;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.service.svcinterface.IAncdService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.base.Struts2Utils;

/** 
 * @author jinjj
 * @date 2013-2-26 上午10:48:05 
 *  
 */
public class StaffSingleHtml {

	private int photoNum = 5;//照片所占列
	private DynaBean info;
	private InfoClass clazz;
	
	public StaffSingleHtml(DynaBean info){
		this.info = info;
		this.clazz = info.getClazz();
	}
	
	public String generateViewHtml(){
		return createViewInfo();
	}
	
	public String generateEditHtml(){
		return createEditInfo();
	}
	
	public String generateInputButton(){
		return createInputButton();
	}
	
	/**
	 * 生成信息内容
	 * @param info
	 * @return
	 */
	private String createViewInfo(){
		StringBuilder sb = new StringBuilder();
		if(info.getValues()!=null){
			sb.append(createViewOperationRow());
			sb.append(createViewContent());
		}
		return sb.toString();
	}
	
	private String createEditInfo(){
		StringBuilder sb = new StringBuilder();
		if(info.getValues()!=null){
			sb.append(createEditOperationRow());
			sb.append(createEditContent());
		}
		return sb.toString();
	}
	
	/**
	 * 生成操作按钮行
	 * @param info
	 * @return
	 */
	public String createViewOperationRow(){
		
		StringBuilder sb = new StringBuilder();
		sb.append("<ul class=\"btn_xxxx\">");
		sb.append("<input type=\"hidden\" name='globalid' value=\""+defaultString(info.getValue("globalid"))+"\">");
		sb.append("<input type=\"hidden\" name='gh' value=\""+defaultString(info.getValue("gh"))+"\">");
		if(findPow("sc")&&info.getClazz().getTypeInfo().isEditable())
		sb.append(createOperateButton("btn_xxxx_sc", "删除"));
		if(findPow("xg")&&info.getClazz().getTypeInfo().isEditable())
		sb.append(createOperateButton("btn_xxxx_bj", "编辑"));
		sb.append("</ul>");
		return sb.toString();
	}
	
	static boolean findPow(String powName){
		String powString = Struts2Utils.getRequest().getParameter("editable");
		boolean flag = "true".equals(powString);
		if(!flag){
			if(!StringUtil.isEmpty(powString)){
				if(powString.indexOf("_"+powName+"_")!=-1)
					return true;
				return false;
			}
			IAncdService service = (IAncdService) ServiceFactory.getService("ancdService");
			List<AncdModel> list = service.cxAncd((User)SessionFactory.getSession().getAttribute("user"), "/baseinfo/infogroupsearch_list.html");
			for (AncdModel ancdModel : list) {
				if(ancdModel.getCzdm().equals(powName)){
					return true;
				}
			}
			return false;
		}else{
			return true;
		}
	}
	
	private String createEditOperationRow(){
		StringBuilder sb = new StringBuilder();
		sb.append("<ul class=\"btn_xxxx\">");
		sb.append("<input type=\"hidden\" name='globalid' value=\""+defaultString(info.getValue("globalid"))+"\">");
		sb.append("<input type=\"hidden\" name='gh' value=\""+defaultString(info.getValue("gh"))+"\">");
		sb.append(createOperateButton("btn_xxxx_cx", "撤销"));
		sb.append(createOperateButton("btn_xxxx_bc", "保存"));
		sb.append("</ul>");
		return sb.toString();
	}
	
	/**
	 * 生成添加按钮
	 * @param info
	 * @return
	 */
	private String createInputButton(){
		StringBuilder sb = new StringBuilder();
		if(findPow("zj"))
		if(clazz.isMoreThanOne()){
			sb.append("<div class=\"demo_add_02\"><a style='cursor:pointer;'>添 加</a></div>");
		}else{
			if(info.getValues()==null){
				sb.append("<div class=\"demo_add_02\"><a style='cursor:pointer;'>添 加</a></div>");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 生成操作按钮
	 * @param cssStyle
	 * @param displayName
	 * @return
	 */
	private String createOperateButton(String cssStyle, String displayName){
		StringBuilder sb = new StringBuilder();
		sb.append("<li class=\""+cssStyle+"\"><a style=\"cursor: pointer;\">"+displayName+"</a></li>");
		return sb.toString();
	}
	
	private String createPropertyName(InfoProperty p,boolean bol){
		StringBuilder sb = new StringBuilder();
		if(p.getNeed() && bol){
			sb.append("<span class='red'>*</span>");
		}
		sb.append(p.getName());
		return sb.toString();
	}
	
	private String createViewContent(){
		StringBuilder sb = new StringBuilder();
		sb.append("<table width=\"100%\"  border=\"0\" class=\"formlist\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("<tbody>");
		int columnNum = clazz.getDisplayNum();
		int thWidthPercent = 100/columnNum*4/11;
		int tdWidthPercent = 100/columnNum*7/11;
		int cnt = 0;//计数器
		List<InfoProperty> imageList = new ArrayList<InfoProperty>();
		List<InfoProperty> photoList = new ArrayList<InfoProperty>();
		List<InfoProperty> textList = new ArrayList<InfoProperty>();
		//分拣属性对象
		for(InfoProperty p:clazz.getViewables()){
			if(p.getFieldType().equals(Type.PHOTO)){
				photoList.add(p);
			}else if(p.getFieldType().equals(Type.IMAGE)){
				imageList.add(p);
			}else{
				textList.add(p);
			}
		}
//		if(photoList.size()>0){
//			for(InfoProperty p : photoList){
//				sb.append("<tr>");
//				sb.append("<th width='"+thWidthPercent+"%'>"+createPropertyName(p,false)+"</th>");
//				sb.append("<td width='"+tdWidthPercent+"%' colspan='"+(columnNum*2-1)+"'>"+defaultString(info.getViewHtml().get(p.getFieldName()))+"</td>");
//				sb.append("</tr>");
//			}
//		}
		for(InfoProperty p:textList){
			//如果为一行的开始（计数器所记格数可以被每列格数整数）
			//则在前方加上tr标签开始端
			if(cnt%columnNum==0){
				sb.append("<tr>");
			}
			//填入非图片非照片的普通标签 计数器相应增加记录格子数
			sb.append("<th width='"+thWidthPercent+"%'>"+createPropertyName(p,false)+"</th>");
			sb.append("<td width='"+tdWidthPercent+"%'>"+defaultString(info.getViewHtml().get(p.getFieldName()))+"</td>");
			cnt++;
			//cnt%columnNum==columnNum-1即已拼接到改列倒数第二行
			//且
			//如果行数（cnt/columnNum）小于照片占用总行数（photoList.size()*photoNum）
			//即此行在照片影响范围内
			if((cnt%columnNum==columnNum-1)&&(cnt/columnNum<photoList.size()*photoNum)){
				//将被照片占用的格子记录到计数器
				cnt++;
				//此行（cnt/columnNum）为照片占用首行时 拼接相应照片
				if(cnt/columnNum%photoNum == 1){
					InfoProperty photo = photoList.get(cnt/columnNum -1);
					sb.append("<th width='"+thWidthPercent+"%' rowspan='"+(photoNum)+"'>"+createPropertyName(photo,false)+"</th>");
					sb.append("<td width='"+tdWidthPercent+"%' rowspan='"+(photoNum)+"'>"+defaultString(info.getViewHtml().get(photo.getFieldName()))+"</td>");
				}
			}
			//到达行列末尾时 拼接tr结束符
			if(cnt%columnNum==0){
				sb.append("</tr>");
			}
		}
		if(cnt%columnNum!=0){
			for(int i=0;i<(columnNum-cnt%columnNum);i++){
				sb.append("<th>&nbsp;</th>");
				sb.append("<td>&nbsp;</td>");
			}
			sb.append("</tr>");
		}
		if(imageList.size()>0){
			for(InfoProperty p : imageList){
				sb.append("<tr>");
				sb.append("<th width='"+thWidthPercent+"%'>"+createPropertyName(p,false)+"</th>");
				sb.append("<td width='"+tdWidthPercent+"%' colspan='"+(columnNum*2-1)+"'>"+defaultString(info.getViewHtml().get(p.getFieldName()))+"</td>");
				sb.append("</tr>");
			}
		}
		sb.append("</tbody>");
		sb.append("</table>");
		return sb.toString();
	}
	
	private String createEditContent(){
		StringBuilder sb = new StringBuilder();
		sb.append("<table width=\"100%\"  border=\"0\" class=\"formlist\" cellpadding=\"0\" cellspacing=\"0\">");
		sb.append("<tbody>");
		int columnNum = clazz.getDisplayNum();
		int thWidthPercent = 100/columnNum*4/11;
		int tdWidthPercent = 100/columnNum*7/11;
		int cnt = 0;
		
		List<InfoProperty> imageList = new ArrayList<InfoProperty>();
		List<InfoProperty> photoList = new ArrayList<InfoProperty>();
		List<InfoProperty> textList = new ArrayList<InfoProperty>();
		for(InfoProperty p:clazz.getEditables()){
			if(p.getFieldType().equals(Type.PHOTO)){
				photoList.add(p);
			}else if(p.getFieldType().equals(Type.IMAGE)){
				imageList.add(p);
			}else{
				textList.add(p);
			}
		}
//		if(photoList.size()>0){
//			for(InfoProperty p : photoList){
//				sb.append("<tr>");
//				sb.append("<th width='"+thWidthPercent+"%'>"+createPropertyName(p,true)+"</th>");
//				sb.append("<td width='"+tdWidthPercent+"%' colspan='"+(columnNum*2-1)+"'>"+defaultString(info.getEditHtml().get(p.getFieldName()))+"</td>");
//				sb.append("</tr>");
//			}
//		}
		for(InfoProperty p:textList){
			//如果为一行的开始（计数器所记格数可以被每列格数整数）
			//则在前方加上tr标签开始端
			if(cnt%columnNum==0){
				sb.append("<tr>");
			}
			//填入非图片非照片的普通标签 计数器相应增加记录格子数
			sb.append("<th width='"+thWidthPercent+"%'>"+createPropertyName(p,true)+"</th>");
			sb.append("<td width='"+tdWidthPercent+"%'>"+defaultString(info.getEditHtml().get(p.getFieldName()))+"</td>");
			cnt++;
			//cnt%columnNum==columnNum-1即已拼接到改列倒数第二行
			//且
			//如果行数（cnt/columnNum）小于照片占用总行数（photoList.size()*photoNum）
			//即此行在照片影响范围内
			if((cnt%columnNum==columnNum-1)&&(cnt/columnNum<photoList.size()*photoNum)){
				//将被照片占用的格子记录到计数器
				cnt++;
				//此行（cnt/columnNum）为照片占用首行时 拼接相应照片
				if(cnt/columnNum%photoNum == 1){
					InfoProperty photo = photoList.get(cnt/columnNum -1);
					sb.append("<th width='"+thWidthPercent+"%' rowspan='"+(photoNum)+"'>"+createPropertyName(photo,true)+"</th>");
					sb.append("<td width='"+tdWidthPercent+"%' rowspan='"+(photoNum)+"'>"+defaultString(info.getEditHtml().get(photo.getFieldName()))+"</td>");
				}
			}
			//到达行列末尾时 拼接tr结束符
			if(cnt%columnNum==0){
				sb.append("</tr>");
			}
		}
		if(cnt%columnNum!=0){
			for(int i=0;i<(columnNum-cnt%columnNum);i++){
				sb.append("<th>&nbsp;</th>");
				sb.append("<td>&nbsp;</td>");
			}
			sb.append("</tr>");
		}
		if(imageList.size()>0){
			for(InfoProperty p : imageList){
				sb.append("<tr>");
				sb.append("<th width='"+thWidthPercent+"%'>"+createPropertyName(p,true)+"</th>");
				sb.append("<td width='"+tdWidthPercent+"%' colspan='"+(columnNum*2-1)+"'>"+defaultString(info.getEditHtml().get(p.getFieldName()))+"</td>");
				sb.append("</tr>");
			}
		}
		sb.append("</tbody>");
		sb.append("</table>");
		return sb.toString();
	}
	
	private String defaultString(Object o){
		return StringUtils.defaultString((String)o);
	}
}
