package com.zfsoft.hrm.normal.overall.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.util.InfoPropertyViewCacheUtil;
import com.zfsoft.hrm.baseinfo.search.entities.Condition;
import com.zfsoft.hrm.baseinfo.search.query.ConditionQuery;
import com.zfsoft.hrm.baseinfo.search.service.svcinterface.IConditionService;
import com.zfsoft.hrm.baseinfo.search.util.SearchUtil;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.util.CookieUtil;
import com.zfsoft.hrm.normal.info.entity.OverallInfo;
import com.zfsoft.hrm.normal.info.service.svcinterface.IOverallInfoService;
import com.zfsoft.util.base.StringUtil;

/** 
 * @ClassName: OverallAction 
 * @author jinjj
 * @date 2012-6-20 上午10:08:50 
 *  
 */
public class OverallAction extends HrmAction {

	private static final long serialVersionUID = -8681221338728219682L;

	private IDynaBeanService dynaBeanService;
	private IConditionService conditionService;
	private IOverallInfoService overallInfoService;
	private PageList pageList;
	private String conditionId;
	private DynaBeanQuery query = new DynaBeanQuery( null );
	private Condition con;
	private String classId;
	private String term;
	private String sortFieldName=null;
	private String asc="up";
	
	private final static String COOKIE_NAME = IConstants.VIEW_STYLE;
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public String list()throws Exception{
		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		query.setClazz(clazz);
		classId = clazz.getGuid();
		if(checkChanged()){
			clazz.setProperties(InfoPropertyViewCacheUtil.getViewList(classId,getUser().getYhm()));
			String pageSize = getRequest().getParameter("query.perPageSize");
			if(StringUtils.isEmpty(pageSize))
				query.setPerPageSize(15);
		}
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( "gh" );
		}
		//query.setOrderStr( "gh" );
		storeConditionId();
		con = conditionService.getById(fetchConditionId());
		ConditionQuery cq = new ConditionQuery();
		cq.setParentId(fetchConditionId());
		con.setChildren(conditionService.getFullList(cq));
		
//		query.setExpress(con.getExpress()+multiMatchExpress()+" and "+SearchConstants.OverallFilterExpression);
		String express = con.getCondition() + multiMatchExpress();
		express = SearchUtil.siftByInService( express );
		query.setExpress( express );
		
		pageList = dynaBeanService.findPagingInfoList(query);
		con.getResult().setProperty("count", Integer.toString(pageList.getPaginator().getItems()));
		if(checkChanged()){
			
			return "list_style2";
		}else{
			return "list";
		}
	}
	
	private void storeConditionId(){
		if(!StringUtils.isEmpty(conditionId)){
			SessionFactory.getSession().setAttribute(IConstants.CONDITION_ID, conditionId);
		}
		String returnUrl = (getRequest().getHeader("referer"));
		if(!StringUtils.isEmpty(getRequest().getHeader("referer"))){
			if(returnUrl.indexOf("primary")>0){
				SessionFactory.getSession().setAttribute("returnUrl", returnUrl);
			}
		}
		getValueStack().set("returnUrl", SessionFactory.getSession().getAttribute("returnUrl"));
	}
	
	private String fetchConditionId(){
		if(!StringUtils.isEmpty(conditionId)){
			return conditionId;
		}
		Object obj = SessionFactory.getSession().getAttribute(IConstants.CONDITION_ID);
		if(obj != null){
			return (String)obj;
		}
		//log.error("");
		return null;
	}
	
	private String multiMatchExpress(){
		StringBuffer sb = new StringBuffer();
		if(!StringUtils.isBlank(query.getFuzzyValue())){
			sb.append( " and (gh like '%' || #{fuzzyValue} || '%'");
			sb.append( " or xm like '%' || #{fuzzyValue} || '%'");
			sb.append( " or xmpy like '%' || #{fuzzyValue} || '%' )");
		}
		return sb.toString();
	}
	
	public String change() throws Exception{
		if(checkChanged()){
			CookieUtil.removeCookie(getResponse(), COOKIE_NAME);//清除
		}else{
			CookieUtil.setCookie(getResponse(), COOKIE_NAME, "2");//设置转换
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	private boolean checkChanged(){
		String cookie = CookieUtil.getCookie(getRequest(), COOKIE_NAME);
		if(StringUtils.isEmpty(cookie))
			return false;//未转换
		else
			return true;//已转换
	}

	public String userListThink() throws UnsupportedEncodingException{
		term=new String(getRequest().getParameter("term").getBytes(getRequest().getCharacterEncoding()),"utf-8");
		List<OverallInfo> overallInfos=overallInfoService.userListThink(term,"rownum<=10");
//		List<String> list=new ArrayList<String>();
//		for (OverallInfo overallInfo : overallInfos) {
//			list.add(overallInfo.toString());
//		}
		this.getValueStack().set(DATA, overallInfos);
		return DATA;
	}
	public String userListScopeThink() throws UnsupportedEncodingException{
		term=new String(getRequest().getParameter("term").getBytes(getRequest().getCharacterEncoding()),"utf-8");
		String dept =getRequest().getParameter("dept");
		int num =10;
		try {
			num=Integer.valueOf(getRequest().getParameter("maxItemSize"));
		} catch (Exception e) {
		}
		String depSql="OFF".equals(dept)?"":" and "+DeptFilterUtil.getCondition("orl", "dwm");
		List<OverallInfo> overallInfos=overallInfoService.userListThink(term," rownum<="+num+depSql);
		this.getValueStack().set(DATA, overallInfos);
		return DATA;
	}

	public String getConditionId() {
		return conditionId;
	}

	public void setConditionId(String conditionId) {
		this.conditionId = conditionId;
	}

	public void setConditionService(IConditionService conditionService) {
		this.conditionService = conditionService;
	}

	public DynaBeanQuery getQuery() {
		return query;
	}

	public void setQuery(DynaBeanQuery query) {
		this.query = query;
	}

	public Condition getCon() {
		return con;
	}

	public PageList getPageList() {
		return pageList;
	}

	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
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

	public String getClassId() {
		return classId;
	}

	public void setOverallInfoService(IOverallInfoService overallInfoService) {
		this.overallInfoService = overallInfoService;
	}
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
}
