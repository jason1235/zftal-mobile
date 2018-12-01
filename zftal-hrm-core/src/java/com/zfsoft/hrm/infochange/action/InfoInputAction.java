package com.zfsoft.hrm.infochange.action;

import java.util.HashMap;
import java.util.Map;

import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.hrm.infochange.entity.InfoChange;
import com.zfsoft.hrm.infochange.entity.InfoChangeAudit;
import com.zfsoft.hrm.infochange.query.InfoChangeQuery;
import com.zfsoft.hrm.infochange.service.IInfoChangeService;
import com.zfsoft.hrm.infochange.service.IInfoInputService;
import com.zfsoft.workflow.model.SpBusiness;

/**
 * 
 * @author ChenMinming
 * @date 2013-8-6
 * @version V1.0.0
 */
public class InfoInputAction extends HrmAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 35186789798L;

	private String classId;
	
	private InfoClass clazz;
	
	private SpBusiness spBusiness;
	
	private PageList<InfoChange> pageList = new PageList<InfoChange>();
	
	private IInfoInputService infoInputService;
	
	private IInfoChangeService infoChangeService;
	
	private InfoChangeQuery query = new InfoChangeQuery();
	
	private DynaBeanQuery dyQuery = new DynaBeanQuery();
	
	private InfoChange infoChange;
	
	private IDynaBeanBusiness dynaBeanBusiness;
	
	private String gh;

	public String list() throws Exception{
		clazz = InfoClassCache.getInfoClass(classId);
		
//		if(query.getClassId()==null){
//			getValueStack().set("statusArray", WorkNodeStatusEnum.values());
//			getValueStack().set("infoClasses", infoClasses);
//			return "page";
//		}
		//删除指定用户、指定信息类的未提交的空信息变更
		infoInputService.doDeleteBlankInitail(getUser().getYhm(), classId);
		
		query.setUserId(getUser().getYhm());
		query.setClassId(classId);
		query.setExpress2("op_type = 'add'");
		pageList = infoInputService.getPagedList(query);
		infoInputService.fillInstance2InfoChange(pageList,classId);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		pageList.setPaginator(query);
		getValueStack().set("beginIndex",beginIndex);
		return "page";
	}
	
	public String beanlist() throws Exception{
		clazz = InfoClassCache.getInfoClass(classId);
		
//		if(query.getClassId()==null){
//			getValueStack().set("statusArray", WorkNodeStatusEnum.values());
//			getValueStack().set("infoClasses", infoClasses);
//			return "page";
//		}
		
		dyQuery.setClazz(clazz);
		dyQuery.setExpress("gh = #{params.gh}");
		dyQuery.setParam("gh", gh);
		PageList<DynaBean> dyBeans = dynaBeanBusiness.findPagingInfoList(dyQuery);
		int beginIndex = dyBeans.getPaginator().getBeginIndex();
		getValueStack().set("dyBeans",dyBeans);
		getValueStack().set("beginIndex",beginIndex);
		return "beanlist";
	}
	
	public String kylist() throws Exception{
		clazz = InfoClassCache.getInfoClass(classId);
		
//		if(query.getClassId()==null){
//			getValueStack().set("statusArray", WorkNodeStatusEnum.values());
//			getValueStack().set("infoClasses", infoClasses);
//			return "page";
//		}
		dyQuery.setClazz(clazz);
		dyQuery.setExpress("gh = #{params.gh}");
		dyQuery.setParam("gh", getUser().getYhm());
		PageList<DynaBean> dyBeans = dynaBeanBusiness.findPagingInfoList(dyQuery);
		int beginIndex = dyBeans.getPaginator().getBeginIndex();
		getValueStack().set("dyBeans",dyBeans);
		getValueStack().set("beginIndex",beginIndex);
		return "kypage";
	}
	
	public String commit() throws Exception{
		Boolean modified=infoInputService.doCheckModify(query.getClassId(), infoChange.getId());
		if(modified){
			infoInputService.doCommit(query.getClassId(),infoChange.getId());
			setSuccessMessage("提交成功");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}else{
			setErrorMessage("信息类没有进行修改，不能提交");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		
	}
	public String detail() throws Exception{
		spBusiness=infoInputService.findSpBusinessByRelDetail(classId,infoChange.getId());
		infoChange = infoInputService.getInfoChangeById(classId,infoChange.getId());
		getValueStack().set("privilegeExpression",spBusiness.getBillClassesPrivilegeString());
		return "detail";
	}
	
	public String view() throws Exception{
		spBusiness=infoInputService.findSpBusinessByRelDetail(classId,infoChange.getId());
		infoChange = infoInputService.getInfoChangeById(classId,infoChange.getId());
		InfoChangeAudit audit = infoInputService.getAudit(classId,infoChange.getId());
		getValueStack().set("privilegeExpression",ModeType.SEARCH+"["+spBusiness.getClassesPrivilege()+"]");
		if(audit==null){
			return "view";
		}
		getValueStack().set("excutedList",audit.getExcutedList());
		getValueStack().set("logList",audit.getLogList());
		return "view";
	}
	
	public String modify() throws Exception{
		spBusiness=infoInputService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
		infoChange = infoInputService.getInfoChangeById(query.getClassId(),infoChange.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("infoChange", infoChange);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String check() throws Exception{
		//验证申报开关
		Boolean modified=infoInputService.doCheckModify(query.getClassId(), query.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("modified", modified);
		map.put("message", "验证成功");
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String delete() throws Exception{
		infoInputService.doCancel(classId, infoChange.getId());
		setSuccessMessage("删除成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String cancel() throws Exception{
		infoInputService.doCancelDeclare(classId,infoChange.getId());
		setSuccessMessage("取消成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String recommit() throws Exception{
		infoChange=infoInputService.doReCommit(classId,infoChange.getId());
		spBusiness=infoInputService.findSpBusinessByRelDetail(classId,infoChange.getId());
		getValueStack().set("privilegeExpression",spBusiness.getBillClassesPrivilegeString());
		return "detail";
	}
	
	public String create() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			spBusiness=infoInputService.findSpBusinessByRelDetail(classId,null);
		}catch(Exception e){
			map.put("success", false);
			map.put("text", "流程未配置");
			getValueStack().set(DATA, map);
			return DATA;
		}
//		query.setClassId(classId);
//		query.setStatus(WorkNodeStatusEnum.INITAIL);
//		query.setUserId(getUser().getYhm());
//		if(infoChangeService.getInfoChangeList(query).size()>0){
//			map = new HashMap<String,Object>();
//			map.put("success", false);
//			map.put("text", "已存在未提交记录，请先处理");
//			getValueStack().set(DATA, map);
//			return DATA;
//		}
		
		infoChange=infoInputService.getNewInfoChange(classId,getUser().getYhm(),"add",null);
		map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("infoChange", infoChange);
		getValueStack().set(DATA, map);
		return DATA;
	}

	public static String getPropertiesValue(String key){
		try{
		    return SubSystemHolder.getPropertiesValue(key);
		}catch(Exception e){
			return "";
		}
	}
	/**
	 * 返回
	 */
	public String getClassId() {
		return classId;
	}

	/**
	 * 设置
	 * @param classId 
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}

	/**
	 * 返回
	 */
	public InfoClass getClazz() {
		return clazz;
	}

	/**
	 * 设置
	 * @param clazz 
	 */
	public void setClazz(InfoClass clazz) {
		this.clazz = clazz;
	}

	/**
	 * 返回
	 */
	public PageList<InfoChange> getPageList() {
		return pageList;
	}

	/**
	 * 设置
	 * @param pageList 
	 */
	public void setPageList(PageList<InfoChange> pageList) {
		this.pageList = pageList;
	}

	/**
	 * 设置
	 * @param infoChangeService 
	 */
	public void setInfoInputService(IInfoInputService infoInputService) {
		this.infoInputService = infoInputService;
	}

	public IInfoChangeService getInfoChangeService() {
		return infoChangeService;
	}

	public void setInfoChangeService(IInfoChangeService infoChangeService) {
		this.infoChangeService = infoChangeService;
	}

	/**
	 * 返回
	 */
	public InfoChange getInfoChange() {
		return infoChange;
	}

	/**
	 * 设置
	 * @param infoChange 
	 */
	public void setInfoChange(InfoChange infoChange) {
		this.infoChange = infoChange;
	}

	/**
	 * 返回
	 */
	public SpBusiness getSpBusiness() {
		return spBusiness;
	}

	/**
	 * 设置
	 * @param spBusiness 
	 */
	public void setSpBusiness(SpBusiness spBusiness) {
		this.spBusiness = spBusiness;
	}

	/**
	 * 返回
	 */
	public InfoChangeQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(InfoChangeQuery query) {
		this.query = query;
	}

	/**
	 * 设置
	 * @param dynaBeanBusiness 
	 */
	public void setDynaBeanBusiness(IDynaBeanBusiness dynaBeanBusiness) {
		this.dynaBeanBusiness = dynaBeanBusiness;
	}

	/**
	 * 返回
	 */
	public DynaBeanQuery getDyQuery() {
		return dyQuery;
	}

	/**
	 * 设置
	 * @param dyQuery 
	 */
	public void setDyQuery(DynaBeanQuery dyQuery) {
		this.dyQuery = dyQuery;
	}

	/**
	 * 返回
	 */
	public String getGh() {
		return gh;
	}

	/**
	 * 设置
	 * @param gh 
	 */
	public void setGh(String gh) {
		this.gh = gh;
	}
	
}
