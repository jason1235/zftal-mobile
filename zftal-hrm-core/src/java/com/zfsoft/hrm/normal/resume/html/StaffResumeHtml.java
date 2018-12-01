package com.zfsoft.hrm.normal.resume.html;

import java.util.ArrayList;
import java.util.List;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

/** 
 * @author jinjj
 * @date 2013-2-21 下午01:34:20 
 *  
 */
public class StaffResumeHtml {

	private List<DynaBean> infos = new ArrayList<DynaBean>();
	private InfoClass clazz;
	
	public StaffResumeHtml(List<DynaBean> infos){
		this.infos = infos;
		this.clazz = infos.get(0).getClazz();
	}
	
	public String generateHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div id='"+clazz.getGuid()+"' class='demo_xxxx'>");
		sb.append(createTitle());
		sb.append("<div name='clazz'>");
		if(!clazz.getIdentityName().equals("JBXXB")){
			for (InfoProperty infoProperty : clazz.getViewables()) {
				if(infoProperty.getFieldName().equals("gh")){
					clazz.getViewables().remove(infoProperty);
					break;
				}
			}
		}
		if("LIST".equals(clazz.getScanStyle())){
			sb.append("<div name='record'>");
			generateListHtml(sb);
			sb.append("</div>");
		}else{
			generateTileHtml(sb);
		}
		sb.append(new StaffSingleHtml(infos.get(0)).generateInputButton());
		sb.append("</div>");
		sb.append("</div>");
		return sb.toString();
	}
	
	private void generateTileHtml(StringBuilder sb){
		for(DynaBean info : infos){
			//去除多余工号
			if(info.getValues()==null){
				continue;
			}
			sb.append("<div name='record'>");
			sb.append(new StaffSingleHtml(info).generateViewHtml());
			sb.append("</div>");
		}
		
	}
	
	private void generateListHtml(StringBuilder sbd){
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='con_overlfow'>");
		sb.append("<table width='100%' class='dateline tablenowrap' summary=''><thead><tr>");
		for (InfoProperty infoProperty : clazz.getViewables()) {
			if(Type.IMAGE.equalsIgnoreCase(infoProperty.getTypeInfo().getName())||
					Type.PHOTO.equalsIgnoreCase(infoProperty.getTypeInfo().getName())){
				continue;
			}
			sb.append("<th>"+infoProperty.getName()+"</th>");
		}
		sb.append("</tr></thead><tbody>");
		int trLength = 0;
		for(DynaBean info : infos){
			if(info.getValues()==null)
				continue;
			sb.append("<tr name='infoclass' globalid='"+info.getValue("globalid")+"'>");
			for (InfoProperty infoProperty : clazz.getViewables()) {
				if(Type.IMAGE.equalsIgnoreCase(infoProperty.getTypeInfo().getName())||
						Type.PHOTO.equalsIgnoreCase(infoProperty.getTypeInfo().getName())){
					continue;
				}
				sb.append("<td>"+info.getViewHtml().get(infoProperty.getFieldName())+"</td>");
			}
			sb.append("</tr>");
			trLength++;
		}
		sb.append("</tbody></table></div>");
		if(trLength>0){
			sbd.append(sb);
		}
		
	}
	
	/**
	 * 生成信息类标题
	 * @return
	 */
	private String createTitle(){
		StringBuilder sb = new StringBuilder();
		sb.append("<h3 style=\"cursor: pointer;\" class=\"college_title\">");
		sb.append("<span class=\"title_name\">"+clazz.getName()+"</span>");
		sb.append("<a style=\"cursor:pointer;\" class=\"up\" href='#'>收起</a>");
		sb.append("</h3>");
		return sb.toString();
	}
	
}
