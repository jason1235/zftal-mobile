package com.zfsoft.hrm.baseinfo.audit.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.config.IOperationConfig;


/** 
 * 审核记录html工具
 * @author jinjj
 * @date 2012-10-17 上午10:17:23 
 *  
 */
public class AuditRecordHtmlGenerator {

	private DynaBean logBean;
	private DynaBean oldBean;
	private Map<String,String> logMap = new HashMap<String,String>();
	private Map<String,String> oldMap = new HashMap<String,String>();
	/**
	 * 解析新增HTML
	 * @param log
	 * @return
	 */
	public String parseSaveHtml(DynaBean log){
		logBean = log;
		if(log!=null){
			logMap = logBean.getViewHtml();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(headHtml());
		sb.append(bodyHtml());
		return sb.toString();
	}
	
	/**
	 * 解析更新HTML
	 * @param log
	 * @param old
	 * @return
	 */
	public String parseUpdateHtml(DynaBean log,DynaBean old){
		logBean = log;
//		logBean = old;
		if(log!=null){
			logMap = logBean.getViewHtml();
		}
		oldBean = old;
//		oldBean = log;
		if(old!=null){
			oldMap = oldBean.getViewHtml();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(headHtml());
		sb.append(bodyHtml());
		return sb.toString();
	}
	
	/**
	 * 解析删除HTML
	 * @param log
	 * @return
	 */
	public String parseDeleteHtml(DynaBean log){
		logBean = log;
		if(log!=null){
			logMap = logBean.getViewHtml();
		}
		StringBuilder sb = new StringBuilder();
		sb.append(headHtml());
		sb.append(bodyHtml());
		return sb.toString();
	}
	
	private String headHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='title_xxxx'>");
		sb.append("<span class='people_xx'>"+DynaBeanUtil.getPersonName(logBean.getValue("gh").toString())+"（工号："+logBean.getValue("gh")+"）</span>");
		sb.append("</div>");
		return sb.toString();
	}
	
	private String bodyHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div class='demo_xxxx'>");
		sb.append(titleHtml());
		sb.append(contentHtml());
		sb.append("</div>");
		return sb.toString();
	}
	
	private String titleHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<h3 style='cursor: pointer;' class='college_title'>");
//		sb.append("<span class='title_name'>"+oldBean.getClazz().getName()+" ("+operationTip(oldBean.getValue("operation_").toString())+")</span>");
		sb.append("<span class='title_name'>"+logBean.getClazz().getName()+" ("+operationTip(logBean.getValue("operation_").toString())+")</span>");
		sb.append("</h3>");
		return sb.toString();
	}
	
	private String operationTip(String operate){
		if(IOperationConfig.ADD.equals(operate)){
			return "操作类型：新增";
		}
		if(IOperationConfig.MODIFY.equals(operate)){
			if(oldBean == null)
				return "操作类型：修改";
			else
				return "操作类型：修改，<font color='red'>红色</font>字体代表数据有改动，鼠标悬停可以显示改动 前数据";
		}
		if(IOperationConfig.REMOVE.equals(operate)){
			return "操作类型：删除";
		}
		return "操作类型：未知操作";
	}
	
	private String contentHtml(){
		StringBuilder sb = new StringBuilder();
		sb.append("<div>");
		sb.append("<table cellspacing='0' cellpadding='0' border='0' width='100%' class='formlist'><tbody>");
		sb.append(contentCreator());
		sb.append("</tbody></table>");
		sb.append("</div>");
		return sb.toString();
	}
	
	private String contentCreator(){
		StringBuilder sb = new StringBuilder();
		InfoClass clazz = logBean.getClazz();
		List<InfoProperty> pList = clazz.getViewables();
		int columnSize = 2;
		if(clazz.isMoreThanOne()){//多条数据的信息类，单列字段显示
			columnSize = 1;
		}
		int cnt = 0;
		for(InfoProperty p : pList){
			if(p.getFieldName().equals("zp")){//照片跳过
				continue;
			}
			if(cnt%columnSize==0){
				sb.append("<tr>");
//				if(cnt == 0){
//					sb.append("#zp");//预留替换标志
//				}
			}
			sb.append("<th width='12.5%'><span name='p'>");
			sb.append(p.getName());
			sb.append("</span></th>");
			sb.append("<td width='30%' name='view'>");
			//sb.append(logMap.get(p.getFieldName()));
			sb.append(valueCompare(p.getFieldName()));
			sb.append("</td>");
			cnt++;
			if(cnt%columnSize==0){
				if(cnt==columnSize){//警告：数据字段属性少于columnSize时，将无法显示照片
					sb.append("#zp");//预留替换标志
				}
				sb.append("</tr>");
			}
		}
		
		if(cnt%columnSize !=0){//填充不足的td元素
			int appendNum = columnSize - cnt%columnSize;
			for(int i=0;i<appendNum;i++){
				sb.append("<th width='15%'></th><td width='35%'></td>");
			}
			sb.append("</tr>");
		}
		
		String zp = "";
		if(clazz.getPropertyByName("zp")!=null){
			int rowSize = (pList.size()-1)/columnSize;
			if((pList.size()-1)%columnSize>0){
				rowSize++;
			}
			StringBuilder zpStr = new StringBuilder();
			zpStr.append("<td width='15%' name='view' style=\"vertical-align:top;\" rowspan='"+rowSize+"'>");
			zpStr.append(picCompare());
			zpStr.append("</td>");
			zp = zpStr.toString();
		}
		//照片标记处理
		return StringUtils.replace(sb.toString(), "#zp", zp);
	}
	
	/**
	 * 字段值显示，包括值比较
	 * @param fieldName
	 * @return
	 */
	private String valueCompare(String fieldName){
		String logValue = logMap.get(fieldName);
		String oldValue = oldMap.get(fieldName);
		StringBuilder sb = new StringBuilder();
		if(oldBean==null){
			sb.append(logValue);
		}else{
			if(logValue == null){
				logValue = "";
			}
			if(oldValue == null){
				oldValue = "";
			}
			if(!logValue.equals(oldValue)){
				sb.append("<span style='color:red;' name='changed' title='"+oldValue+"'>"+(StringUtils.isEmpty(logValue)?"(无)":logValue)+"</span>");
			}else{
				sb.append(logValue);
			}
		}
		return sb.toString();
	}
	
	/**
	 * 照片字段值显示，包括值比较
	 * @param fieldName
	 * @return
	 */
	private String picCompare(){
		String logView = logMap.get("zp");
		String oldView = oldMap.get("zp");
		StringBuilder sb = new StringBuilder();
		if(oldBean==null){
			sb.append(logView);
		}else{
			if(logView == null){
				logView = "";
			}
			if(oldView == null){
				oldView = "";
			}
			sb.append(logView);
			String oldValue;
			String logValue;
			if(oldBean!=null){
				oldValue=oldBean.getValueString("zp");
			}else{
				oldValue="";
			}
			if(logBean!=null){
				logValue=logBean.getValueString("zp");
			}else{
				logValue="";
			}
			if(!oldValue.equals(logValue)){
				sb.append("<br/><span name='changed' id='picChanged' style='color:red;' title=\""+oldView+"\">照片更新</span>");
			}
		}
		return sb.toString();
	}
}
