package com.zfsoft.hrm.normal.resume.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.finfo.service.svcinterface.IFormInfoMemberPropertyService;
import com.zfsoft.hrm.baseinfo.finfo.service.svcinterface.IFormInfoMemberService;
import com.zfsoft.hrm.baseinfo.finfo.util.FormInfoUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.infochange.service.IInfoChangeService;
import com.zfsoft.hrm.normal.resume.service.svcinterface.IStaffInfoService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.service.ISpBusinessService;

/** 
 * 人员信息管理
 * @author jinjj
 * @date 2012-8-21 下午03:17:40 
 *  
 */
public class StaffInfoAction extends HrmAction {

	private static final long serialVersionUID = 7508782995353592541L;
	private IStaffInfoService staffInfoService;
	private IFormInfoMemberService memberService;
	private IFormInfoMemberPropertyService memberPropertyService;
	private ISpBusinessService spBusinessService;
	private IDynaBeanService dynaBeanService;
	private IInfoChangeService infoChangeService;
	private DynaBeanQuery query=new DynaBeanQuery();
	private String asc;
	private String sortFieldName;
	private List<DynaBean> list;
	private String classId;
	private InfoClass clazz;
	private DynaBean bean;
	private String gh;
	private Boolean hasBusiness=false;
	
	private Map<String,Object> values = new HashMap<String,Object>();
	
	/**
	 * 加载信息数据
	 * @return
	 * @throws Exception
	 */
	public String list() throws Exception{
		clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
		try{
			SpBusiness sp = spBusinessService.findSpBusinessByRelDetail(clazz.getGuid(),null);
			hasBusiness=(sp.getProcedure()!=null);
		}catch(Exception e){
			hasBusiness=false;
		}
		if(hasBusiness){
			//删除指定用户、指定信息类的未提交的空信息变更
			infoChangeService.doDeleteBlankInitail(getUser().getYhm(), classId);
		}
		String gh = SessionFactory.getUser().getYhm();
		bean = staffInfoService.getStaffOverAllInfo(gh);
		if("LIST".equals(clazz.getScanStyle())){
			if(!StringUtil.isEmpty(sortFieldName)){
				if(asc.equals("up")){
					query.setOrderStr( "t."+sortFieldName );
				}else{
					query.setOrderStr( "t."+sortFieldName +" desc");
				}
			}
			query.setClazz(clazz);
			query.setExpress( " gh == #{params.gh} " );
			query.setParam("gh", gh);
			query.setHistory(true);//个人信息管理默认显示历史记录
			PageList<DynaBean> pageList = dynaBeanService.findPagingInfoList(query);
			getValueStack().set("pageList", pageList);
			getValueStack().set("properties", clazz.getProperties());
			this.getValueStack().set("heightOffset", (clazz.getEditables().size()/2)*30);
			
			return "listStyle";
		}
		return "list";
	}
	
	public String query() throws Exception{
		clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
//		DynaBeanQuery query = new DynaBeanQuery(clazz);
		String gh = SessionFactory.getUser().getYhm();
		query.setClazz(clazz);
		query.setExpress( " gh == #{params.gh} " );
		query.setParam("gh", gh);
		query.setHistory(true);//个人信息管理默认显示历史记录
		list = staffInfoService.queryBeans(query);
		if(list.size()==0){//当无数据时，传一个空的DynaBean，用于展现新增表单的结构
			DynaBean bean = new DynaBean(query.getClazz());
			bean.setValues(null);
			list.add(bean);
		}
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("result", list);
		Object buttons = getValueStack().findValue("ancdModelList");
		map.put("permission", buttons);
		map.put("properties", clazz.getProperties());
		map.put("scanStyle", clazz.getScanStyle());
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String insert() throws Exception{
		InfoClass clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
		bean = new DynaBean(clazz);
		
//		FormInfoMember member = memberService.getMember(IConstants.FINFO_NAME_FELLOWS, classId,false);
//		FormInfoMemberProperty[] properties = memberPropertyService.getEditMemberProperties(member);
//		bean = FormInfoUtil.createBean(member, properties);
		
		for(InfoProperty prop : bean.getEditables()){
			values.put(prop.getFieldName(), getRequest().getParameter(prop.getFieldName()));
		}
		values.put("gh", getRequest().getParameter("gh"));
		bean.setValues(values);
//		staffInfoService.save(bean);
//		checkAudit();
		dynaBeanService.addRecord(bean);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String update() throws Exception{
		InfoClass clazz = FormInfoUtil.reFillPropertyByRole(getUser().getJsdms(), classId);
		//bean = new DynaBean(clazz);
//		FormInfoMember member = memberService.getMember(IConstants.FINFO_NAME_FELLOWS, classId,false);
//		FormInfoMemberProperty[] properties = memberPropertyService.getEditMemberProperties(member);
//		bean = FormInfoUtil.createBean(member, properties);
//		List<InfoProperty> list = bean.getEditables();
		
		//查询原对象，填充未修改的字段值
		values.put("globalid",getRequest().getParameter("globalid"));
		values.put("gh", getRequest().getParameter("gh"));
		DynaBean oldBean = new DynaBean(clazz);
		oldBean.setValues(values);
		oldBean = staffInfoService.getById(oldBean);
		for(InfoProperty prop : clazz.getEditables()){
			oldBean.setValue(prop.getFieldName(), getRequest().getParameter(prop.getFieldName()));
		}
		
//		staffInfoService.update(oldBean);
//		checkAudit();
		dynaBeanService.modifyRecord(oldBean);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String delete() throws Exception{
		InfoClass clazz = InfoClassCache.getInfoClass(classId);
		bean = new DynaBean(clazz);
		values.put("globalid",getRequest().getParameter("globalid"));
		values.put("gh", getRequest().getParameter("gh"));
		bean.setValues(values);
		dynaBeanService.removeRecord(bean);
//		checkAudit();
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
//	/**
//	 * 校验审核，并修改返回提示
//	 */
//	private void checkAudit(){
//		List<AuditDefine> list = AuditDefineCacheUtil.getDefine(classId);
//		if(list != null && list.size()>0){
//			setSuccessMessage("已提交审核，请在信息管理-信息审核-审核查看中浏览");
//		}
//	}

	public void setStaffInfoService(IStaffInfoService staffInfoService) {
		this.staffInfoService = staffInfoService;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public String getGh() {
		return gh;
	}

	public void setGh(String gh) {
		this.gh = gh;
	}

	public List<DynaBean> getList() {
		return list;
	}

	public InfoClass getClazz() {
		return clazz;
	}

	public DynaBean getBean() {
		return bean;
	}

	public void setMemberService(IFormInfoMemberService memberService) {
		this.memberService = memberService;
	}

	public void setMemberPropertyService(
			IFormInfoMemberPropertyService memberPropertyService) {
		this.memberPropertyService = memberPropertyService;
	}

	public void setSpBusinessService(ISpBusinessService spBusinessService) {
		this.spBusinessService = spBusinessService;
	}
	
	public IInfoChangeService getInfoChangeService() {
		return infoChangeService;
	}

	public void setInfoChangeService(IInfoChangeService infoChangeService) {
		this.infoChangeService = infoChangeService;
	}

	public Boolean getHasBusiness() {
		return hasBusiness;
	}

	public void setHasBusiness(Boolean hasBusiness) {
		this.hasBusiness = hasBusiness;
	}

	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}

	public DynaBeanQuery getQuery() {
		return query;
	}

	public void setQuery(DynaBeanQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 */
	public String getAsc() {
		return asc;
	}

	/**
	 * 设置
	 * @param asc 
	 */
	public void setAsc(String asc) {
		this.asc = asc;
	}

	/**
	 * 返回
	 */
	public String getSortFieldName() {
		return sortFieldName;
	}

	/**
	 * 设置
	 * @param sortFieldName 
	 */
	public void setSortFieldName(String sortFieldName) {
		this.sortFieldName = sortFieldName;
	}

	
}
