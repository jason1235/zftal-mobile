package com.zfsoft.hrm.baseinfo.snapshot.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.html.Type;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.util.InfoPropertyViewCacheUtil;
import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotLog;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotLogQuery;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotLogService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.util.date.DateTimeUtil;

/** 
 * 快照查询-人员列表
 * @author jinjj
 * @date 2012-11-5 上午11:07:57 
 *  
 */
public class SnapQueryAction extends HrmAction {

	private static final long serialVersionUID = -8287060131047064225L;
	private IDynaBeanService dynaBeanService;
	private ISnapshotLogService snapshotLogService;
	private PageList<DynaBean> pageList;
	private DynaBeanQuery query = initDynaBeanQuery();
	// 20140429 add start
	private String express;
	// 20140429 add end
	private String dwm;
	private String gh;
	private String xm;
	private Date snapTimeStart;
	private Date snapTimeEnd;
	private String classId;
	
	private String sortFieldName=null;
	private String asc="up";
	
	private int showMore=-1;
	
	private DynaBeanQuery initDynaBeanQuery(){
		DynaBeanQuery query = new DynaBeanQuery(null);
		query.setPerPageSize(15);
		return query;
	}
	
	public String page() throws Exception{
		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		
		List<InfoProperty> conditions=new ArrayList<InfoProperty>();
		Map<String,Object> valuesMap=new HashMap<String, Object>();
		StringBuilder sbMoreConds=new StringBuilder();
		
		for(InfoProperty infoProperty :clazz.getViewables()){
			infoProperty.setClasz(clazz);
			if("gh".equals(infoProperty.getFieldName())
					||"xm".equals(infoProperty.getFieldName())
					||"dwm".equals(infoProperty.getFieldName())){
				continue;
			}
			if(!Type.PHOTO.equals(infoProperty.getFieldType())&&
					!Type.IMAGE.equals(infoProperty.getFieldType())&&
					!Type.FILE.equals(infoProperty.getFieldType())){
				conditions.add(infoProperty);
				
				String fieldName="t."+infoProperty.getFieldName();
				if(infoProperty.getVirtual()){
					fieldName = infoProperty.getDisplayFormula("t");
				}
				
				if(Type.DATE.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
						||Type.MONTH.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
						||Type.YEAR.equalsIgnoreCase(infoProperty.getTypeInfo().getName())){
					String[] values=getRequest().getParameterValues(infoProperty.getFieldName());
					if(values==null){
						continue;
					}
					valuesMap.put(infoProperty.getFieldName(), values);
					if(StringUtils.isNotEmpty(values[0])){
						sbMoreConds.append(" and ");
						sbMoreConds.append(fieldName);
						sbMoreConds.append(">= to_date('");
						sbMoreConds.append(values[0]);
						sbMoreConds.append("','");
						sbMoreConds.append(infoProperty.getTypeInfo().getFormat());
						sbMoreConds.append("')");
					}
					if(StringUtils.isNotEmpty(values[1])){
						sbMoreConds.append(" and ");
						sbMoreConds.append(fieldName);
						sbMoreConds.append("<= to_date('");
						sbMoreConds.append(values[1]);
						sbMoreConds.append("','");
						sbMoreConds.append(infoProperty.getTypeInfo().getFormat());
						sbMoreConds.append("')");
					}
				}else{
					String value=getRequest().getParameter(infoProperty.getFieldName());
					if(StringUtils.isEmpty(value)){
						continue;
					}
					valuesMap.put(infoProperty.getFieldName(), value);
					if(Type.CODE.equalsIgnoreCase(infoProperty.getTypeInfo().getName())
							||Type.SIGLE_SEL.equalsIgnoreCase(infoProperty.getTypeInfo().getName())){
						sbMoreConds.append(" and ");
						sbMoreConds.append(fieldName);
						sbMoreConds.append("='");
						sbMoreConds.append(value);
						sbMoreConds.append("'");
					}else if(Type.NUMBER.equalsIgnoreCase(infoProperty.getTypeInfo().getName())){
						sbMoreConds.append(" and ");
						sbMoreConds.append(fieldName);
						sbMoreConds.append("=");
						sbMoreConds.append(value);
						sbMoreConds.append("");
					}else{
						sbMoreConds.append(" and ");
						sbMoreConds.append(fieldName);
						sbMoreConds.append(" like '%");
						sbMoreConds.append(value.replaceAll("'", "''"));
						sbMoreConds.append("%'");
					}
				}
			}
		}
		classId = clazz.getGuid();
		clazz.setProperties(InfoPropertyViewCacheUtil.getViewList(classId,getUser().getYhm()));
		String identityName = clazz.getIdentityName();
		clazz.setIdentityName(identityName + "_SNAP");
		query.setDeleted(null);
		query.setClazz(clazz);
		query.setParam("dwm", dwm);//overall中字段名称
		query.setParam("gh", gh);
		query.setParam("xm", xm);
		SnapshotLogQuery query2 = new SnapshotLogQuery();
		query2.setPerPageSize(Integer.MAX_VALUE);
		PageList<SnapshotLog> pageList2 = snapshotLogService.getPage(query2);
//		query.setSnapTime(snapTimeStart == null ? (pageList2.get(0)).getSnapTime() : snapTimeStart);
		query.setSnapTimeStart(snapTimeStart);
		query.setSnapTimeEnd(snapTimeEnd);
		query.setExpress(multiMatchExpress());
		if(sbMoreConds.length()>0){
			String express=query.getExpress()+sbMoreConds.toString();
			query.setExpress(express);
		}
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( " OPERATION_TIME_" );
		}
		pageList = dynaBeanService.findPagingInfoList(query);
		
		// 20140429 add start
		express = query.getExpress();
		// 20140429 add end
		
		getValueStack().set("snapTimes",pageList2);
		this.getValueStack().set("conditions", conditions);
		this.getValueStack().set("valuesMap", valuesMap);
		
		return "page";
	}
	
	public String export() throws Exception{
		query.setPerPageSize(Integer.MAX_VALUE);
		page();
		return "export";
	}
	
	private String multiMatchExpress(){
		StringBuffer sb = new StringBuffer(" 1=1 ");
		if(!StringUtils.isBlank(dwm)){
			if(sb.length()>0){
				sb.append(" and ");
			}
			sb.append( " dwm like '%' || #{params.dwm} || '%'");
		}
		if(!StringUtils.isBlank(gh)){
			if(sb.length()>0){
				sb.append(" and ");
			}
			sb.append( " gh like '%' || #{params.gh} || '%'");
		}
		if(!StringUtils.isBlank(xm)){
			if(sb.length()>0){
				sb.append(" and ");
			}
			sb.append( " xm like '%' || #{params.xm} || '%'");
		}
		return sb.toString();
	}

	public String getSnapTimeStartStr() {
		if(snapTimeStart==null){
			return null;
		}
		return DateTimeUtil.getFormatDate(snapTimeStart);
	}
	public String getSnapTimeEndStr() {
		if(snapTimeEnd==null){
			return null;
		}
		return DateTimeUtil.getFormatDate(snapTimeEnd);
	}
	
	public DynaBeanQuery getQuery() {
		return query;
	}

	public void setQuery(DynaBeanQuery query) {
		this.query = query;
	}

	public PageList<DynaBean> getPageList() {
		return pageList;
	}

	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public String getDwm() {
		return dwm;
	}

	public void setDwm(String dwm) {
		this.dwm = dwm;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getClassId() {
		return classId;
	}

	/**
	 * @return the snapshotLogService
	 */
	public ISnapshotLogService getSnapshotLogService() {
		return snapshotLogService;
	}

	/**
	 * @param snapshotLogService the snapshotLogService to set
	 */
	public void setSnapshotLogService(ISnapshotLogService snapshotLogService) {
		this.snapshotLogService = snapshotLogService;
	}

	public String getSortFieldName() {
		return sortFieldName;
	}

	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	public String getAsc() {
		return asc;
	}

	public void setAsc(String asc) {
		this.asc = asc;
	}
	
	public Date getSnapTimeStart() {
		return snapTimeStart;
	}

	public void setSnapTimeStart(Date snapTimeStart) {
		this.snapTimeStart = snapTimeStart;
	}

	public Date getSnapTimeEnd() {
		return snapTimeEnd;
	}

	public void setSnapTimeEnd(Date snapTimeEnd) {
		this.snapTimeEnd = snapTimeEnd;
	}

	public int getShowMore() {
		return showMore;
	}

	public void setShowMore(int showMore) {
		this.showMore = showMore;
	}
	// 20140429 add start
	/**
	 * @return the express
	 */
	public String getExpress() {
		return express;
	}

	/**
	 * @param express the express to set
	 */
	public void setExpress(String express) {
		this.express = express;
	}
	
	// 20140429 add end
}
