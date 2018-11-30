package com.zfsoft.hrm.normal.resume.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.finfo.util.FormInfoUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.infoclass.query.CatalogQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.ICatalogService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.normal.resume.html.StaffResumeHtml;
import com.zfsoft.hrm.normal.resume.html.StaffResumeHtmlForAll;
import com.zfsoft.hrm.normal.resume.html.StaffSingleHtml;
import com.zfsoft.hrm.normal.resume.service.svcinterface.IStaffResumeService;
import com.zfsoft.util.base.StringUtil;

/** 
 * @ClassName: StaffResumeAction 
 * @author jinjj
 * @date 2012-6-21 上午09:55:50 
 *  
 */
public class StaffResumeAction extends HrmAction {

	private static final long serialVersionUID = -7456865450705845352L;
	private IStaffResumeService staffResumeService;
	private IDynaBeanService dynaBeanService;
	private ICatalogService catalogService;
	private List<InfoClass> clazzes;
	private List<Catalog> catalogs;
	private DynaBean bean;
	private List<DynaBean> list;
	
	private String classId;		//信息类编号
	private String globalid;
	private String catalogId;
	private boolean history;	//是否显示历史记录
	private Map<String,Object> params = new HashMap<String,Object>();
	private String returnUrl="";

	/**
	 * 页面类目录列表
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception{
		CatalogQuery  query =new CatalogQuery();
		clazzes = (List<InfoClass>)InfoClassCache.getInfoClasses();
		//去除多余工号
		for (InfoClass clazz : clazzes) {
			if(clazz.getIdentityName().equals("JBXXB")){
				continue;
			}
			for (InfoProperty infoProperty : clazz.getViewables()) {
				if(infoProperty.getFieldName().equals("gh")){
					clazz.getViewables().remove(infoProperty);
					break;
				}
			}
		}
		query.setType( IConstants.INFO_CATALOG_TYPE_DEFAULT );
		
		Catalog c = new Catalog();
		List<InfoClass> list = new ArrayList<InfoClass>();
		InfoClass overall = InfoClassCache.getOverallInfoClass().clone();
		overall.setType("SINGLE");
		list.add(overall);
		c.setClasses(list);
		c.setName("个人综合信息");
		c.setGuid("overall_catalog");
		catalogs = new ArrayList<Catalog>();
		catalogs.add(c);
		catalogs.addAll(catalogService.getFullList(query));
		catalogId = catalogs.get(0).getGuid();
		DynaBean dyBean = new DynaBean(InfoClassCache.getOverallInfoClass());
		dyBean.setValue("globalid", globalid);
		bean = dynaBeanService.findById(dyBean);
		if(StringUtils.isEmpty(returnUrl)){
			returnUrl = getRequest().getHeader("referer");
		}
		
		getValueStack().set("editable", getRequest().getParameter("editable"));
		return "list";
	}
	
	/**
	 * 页面类目录列表
	 * @return
	 * @throws Exception
	 */
	public String listByGh() throws Exception{
		CatalogQuery  query =new CatalogQuery();
		clazzes = (List<InfoClass>)InfoClassCache.getInfoClasses();
		//去除多余工号
		for (InfoClass clazz : clazzes) {
			if(clazz.getIdentityName().equals("JBXXB")){
				continue;
			}
			for (InfoProperty infoProperty : clazz.getViewables()) {
				if(infoProperty.getFieldName().equals("gh")){
					clazz.getViewables().remove(infoProperty);
					break;
				}
			}
		}
		query.setType( IConstants.INFO_CATALOG_TYPE_DEFAULT );
		
		Catalog c = new Catalog();
		List<InfoClass> list = new ArrayList<InfoClass>();
		InfoClass overall = InfoClassCache.getOverallInfoClass().clone();
		overall.setType("SINGLE");
		list.add(overall);
		c.setClasses(list);
		c.setName("个人综合信息");
		c.setGuid("overall_catalog");
		catalogs = new ArrayList<Catalog>();
		catalogs.add(c);
		catalogs.addAll(catalogService.getFullList(query));
		catalogId = catalogs.get(0).getGuid();
		bean = dynaBeanService.findUniqueByParam("gh", getRequest().getParameter("gh"));
		if(StringUtils.isEmpty(returnUrl)){
			returnUrl = getRequest().getHeader("referer");
		}
		
		getValueStack().set("editable", getRequest().getParameter("editable"));
		return "list";
	}
	

	public String listAll() throws Exception{
		CatalogQuery  query =new CatalogQuery();
		if(StringUtils.isEmpty(globalid)){
			User user = getUser();
			if(user != null){
				globalid = user.getYhm();
			}			
		}
		query.setType( IConstants.INFO_CATALOG_TYPE_DEFAULT );
		catalogs = catalogService.getFullList(query);
		return "listAll";
	}
	
	/**
	 * 根据类目录加载信息
	 * @return
	 * @throws Exception
	 */
	public String loadInfoByCatalog() throws Exception{
		if(catalogId==null){
			catalogId = InfoClassCache.getInfoClasses().get(0).getCatalog().getGuid();
		}
		DynaBeanQuery query = new DynaBeanQuery( null );
		query.setExpress( " gh == #{params.gh} " );
		params.put("gh", getRequest().getParameter("gh"));
		query.setParams(params);
		query.setHistory(history);
		List<List<DynaBean>> list = null;
		if("overall_catalog".equals(catalogId)){
			list = new ArrayList<List<DynaBean>>();
			query.setClazz(InfoClassCache.getOverallInfoClass());
			list.add(dynaBeanService.findList(query));
		}else{
			list = staffResumeService.getInfoByCatalog(catalogId, query);
		}
		String clzid = getRequest().getParameter("clzid");
		StringBuilder sb = new StringBuilder();
		for(List<DynaBean> infos : list){
			if (StringUtil.isEmpty(clzid)||clzid.equals(infos.get(0).getClazz().getGuid())) {
				sb.append(new StaffResumeHtml(infos).generateHtml());
			}
		}
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("html", sb.toString());
		getValueStack().set(DATA, map);
		return DATA;
	}
	/**
	 * 通过信息类目录加载信息类
	 * @return
	 * @throws Exception
	 */
	public String loadInfoByCatalogForAll() throws Exception{
		if(catalogId==null){
			catalogId = InfoClassCache.getInfoClasses().get(0).getCatalog().getGuid();
		}
		DynaBeanQuery query = new DynaBeanQuery( null );
		query.setExpress( " gh == #{params.gh} " );
		params.put("gh", getRequest().getParameter("gh"));
		query.setParams(params);
		query.setHistory(history);
		List<List<DynaBean>> list = staffResumeService.getInfoByCatalog(catalogId, query);
		String clzid = getRequest().getParameter("clzid");
		StringBuilder sb = new StringBuilder();
		for(List<DynaBean> infos : list){
			if(infos==null||infos.size()==0){
				continue;
			}
			if(infos.get(0).getValues()==null||infos.get(0).getValues().size()==0){
				continue;
			}
			if (StringUtil.isEmpty(clzid)||clzid.equals(infos.get(0).getClazz().getGuid())) {
				sb.append(new StaffResumeHtmlForAll(infos,Boolean.valueOf(getRequest().getParameter("editable"))).generateHtml());
			}
		}
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("html", sb.toString());
		getValueStack().set(DATA, map);
		return DATA;
	}

	/**
	 * 新增
	 * @return
	 * @throws Exception
	 */
	public String insert() throws Exception{
		InfoClass clazz = FormInfoUtil.reFillPropertyByRole(SessionFactory.getUser().getJsdms(), classId);
		bean = new DynaBean(clazz);
		for(InfoProperty prop : bean.getEditables()){
			params.put(prop.getFieldName(), getRequest().getParameter(prop.getFieldName()));
		}
		params.put("gh", getRequest().getParameter("gh"));
		bean.setValues(params);
		staffResumeService.insert(bean);
		
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String loadInfoById() throws Exception{
		InfoClass clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
		bean = new DynaBean(clazz);
		bean.setValue("globalid", globalid);
		bean = staffResumeService.getById(bean);
		
		StringBuilder sb = new StringBuilder();
		sb.append(new StaffSingleHtml(bean).generateViewHtml());
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("html", sb.toString());
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String loadInfoByClass() throws Exception{
		InfoClass clazz = FormInfoUtil.reFillPropertyByRole(SessionFactory.getUser().getJsdms(), classId);
		DynaBeanQuery query = new DynaBeanQuery( clazz );
		query.setExpress( " gh == #{params.gh} " );
		params.put("gh", getRequest().getParameter("gh"));
		query.setParams(params);
		query.setHistory(history);
		list = staffResumeService.getInfoByClass(query);
		StringBuilder sb = new StringBuilder();
		sb.append(new StaffResumeHtml(list).generateHtml());
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("html", sb.toString());
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String editInfoById() throws Exception{
		InfoClass clazz = FormInfoUtil.reFillPropertyByRole(SessionFactory.getUser().getJsdms(), classId);
		bean = new DynaBean(clazz);
		bean.setValue("globalid", globalid);
		bean = staffResumeService.getById(bean);
		
		StringBuilder sb = new StringBuilder();
		sb.append(new StaffSingleHtml(bean).generateEditHtml());
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("html", sb.toString());
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String input() throws Exception{
		InfoClass clazz = FormInfoUtil.reFillPropertyByRole(SessionFactory.getUser().getJsdms(), classId);
		bean = new DynaBean(clazz);
		
		StringBuilder sb = new StringBuilder();
		sb.append(new StaffSingleHtml(bean).generateEditHtml());
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("html", sb.toString());
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String delete() throws Exception{
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		bean = new DynaBean(clazz);
		params.put("globalid",getRequest().getParameter("globalid"));
		bean.setValues(params);
		staffResumeService.delete(bean);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String update() throws Exception{
		InfoClass clazz = FormInfoUtil.reFillPropertyByRole(SessionFactory.getUser().getJsdms(), classId);
		bean = new DynaBean(clazz);
		for(InfoProperty prop : bean.getEditables()){
			params.put(prop.getFieldName(), getRequest().getParameter(prop.getFieldName()));
		}
		params.put("globalid",getRequest().getParameter("globalid"));
		params.put("gh", getRequest().getParameter("gh"));
		bean.setValues(params);
		staffResumeService.update(bean);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public void setStaffResumeService(IStaffResumeService staffResumeService) {
		this.staffResumeService = staffResumeService;
	}

	public List<InfoClass> getClazzes() {
		return clazzes;
	}

	public DynaBean getBean() {
		return bean;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public List<DynaBean> getList() {
		return list;
	}

	public void setCatalogService(ICatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public List<Catalog> getCatalogs() {
		return catalogs;
	}

	public boolean isHistory() {
		return history;
	}

	public void setHistory(boolean history) {
		this.history = history;
	}

	public String getGlobalid() {
		return globalid;
	}

	public void setGlobalid(String globalid) {
		this.globalid = globalid;
	}
	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	
}
