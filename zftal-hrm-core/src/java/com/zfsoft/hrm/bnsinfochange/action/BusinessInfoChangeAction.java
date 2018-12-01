package com.zfsoft.hrm.bnsinfochange.action;

import java.util.HashMap;
import java.util.Map;


import com.zfsoft.common.system.SubSystemHolder;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.dyna.business.bizinterface.IDynaBeanBusiness;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.bnsinfochange.entity.BusinessInfoChange;
import com.zfsoft.hrm.bnsinfochange.entity.BusinessInfoChangeAudit;
import com.zfsoft.hrm.bnsinfochange.query.BusinessInfoQuery;
import com.zfsoft.hrm.bnsinfochange.service.IBusinessInfoChangeService;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.workflow.model.SpBusiness;

/**
 * 
 * @author ChenMinming
 * @date 2014-6-17
 * @version V1.0.0
 */
public class BusinessInfoChangeAction extends HrmAction {

	private static final long serialVersionUID = 201406171059946L;

	private String classId;
	
	private InfoClass clazz;
	
	private String initTagId;
	
	private SpBusiness spBusiness;
	
	private PageList<BusinessInfoChange> pageList = new PageList<BusinessInfoChange>();
	
	private IBusinessInfoChangeService businessInfoChangeService;
	
	private BusinessInfoQuery query = new BusinessInfoQuery();
	
	private DynaBeanQuery dyQuery = new DynaBeanQuery();
	
	private BusinessInfoChange businessInfoChange;
	
	private IDynaBeanBusiness dynaBeanBusiness;
	
	private String gh;
	
	public String list() throws Exception{
		SpBusiness  spb = businessInfoChangeService.findSpBusiness(classId, null);
		getValueStack().set("hasBusiness",(spb!=null)&&(spb.getProcedure()!=null));
		clazz = InfoClassCache.getInfoClass(classId);
		return "page";
	}

	public String declarePage() throws Exception{
		clazz = InfoClassCache.getInfoClass(classId);
		query.setUserId(getUser().getYhm());
		query.setClassId(classId);
		query.setExpress2("and op_type = 'add'");
		query.setOrderStr("create_date desc");
		pageList = businessInfoChangeService.getPagedList(query);
		pageList.setPaginator(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		getValueStack().set("beginIndex",beginIndex);
		getValueStack().set("hasBusiness",true);
		return "declarelist";
	}
	
	public String bnsinfoList() throws Exception{
		SpBusiness  spb = businessInfoChangeService.findSpBusiness(classId, null);
		getValueStack().set("hasBusiness",(spb!=null)&&(spb.getProcedure()!=null));
		
		clazz = InfoClassCache.getInfoClass(classId);
		dyQuery.setClazz(clazz);
		dyQuery.setExpress("gh = #{params.gh}");
		dyQuery.setParam("gh", getUser().getYhm());
		PageList<DynaBean> dyBeans = dynaBeanBusiness.findPagingInfoList(dyQuery);
		int beginIndex = dyBeans.getPaginator().getBeginIndex();
		
		getValueStack().set("dyBeans",dyBeans);
		getValueStack().set("beginIndex",beginIndex);
		return "infolist";
	}
	
	public String commit() throws Exception{
		businessInfoChangeService.doCommit(query.getClassId(),businessInfoChange.getId());
		setSuccessMessage("提交成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	public String detail() throws Exception{
		spBusiness=businessInfoChangeService.findSpBusiness(classId,businessInfoChange.getId());
		businessInfoChange = businessInfoChangeService.getInfoChangeById(classId,businessInfoChange.getId());
		getValueStack().set("privilegeExpression",spBusiness.getBillClassesPrivilegeString());
		getValueStack().set("billConfigId",spBusiness.getBillId());
		return "detail";
	}
	
	public String view() throws Exception{
		spBusiness=businessInfoChangeService.findSpBusiness(classId,businessInfoChange.getId());
		businessInfoChange = businessInfoChangeService.getInfoChangeById(classId,businessInfoChange.getId());
		BusinessInfoChangeAudit audit = businessInfoChangeService.getAudit(classId,businessInfoChange.getId());
		getValueStack().set("privilegeExpression",ModeType.SEARCH+"["+spBusiness.getClassesPrivilege()+"]");
		if(audit==null){
			return "view";
		}
		getValueStack().set("excutedList",audit.getExcutedList());
		getValueStack().set("logList",audit.getLogList());
		return "view";
	}
	
	public String modify() throws Exception{
		spBusiness=businessInfoChangeService.findSpBusiness(query.getClassId(),businessInfoChange.getId());
		businessInfoChange = businessInfoChangeService.getInfoChangeById(query.getClassId(),businessInfoChange.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("businessInfoChange", businessInfoChange);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String delete() throws Exception{
		businessInfoChangeService.doCancel(classId, businessInfoChange.getId());
		setSuccessMessage("删除成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String cancel() throws Exception{
		businessInfoChangeService.doCancelDeclare(classId,businessInfoChange.getId());
		setSuccessMessage("取消成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String recommit() throws Exception{
		businessInfoChange=businessInfoChangeService.doReCommit(classId,businessInfoChange.getId());
		spBusiness=businessInfoChangeService.findSpBusiness(classId,businessInfoChange.getId());
		getValueStack().set("privilegeExpression",spBusiness.getBillClassesPrivilegeString());
		getValueStack().set("billConfigId",spBusiness.getBillId());
		return "detail";
	}
	
	public String create() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			spBusiness=businessInfoChangeService.findSpBusiness(classId,null);
		}catch(Exception e){
			map.put("success", false);
			map.put("text", "流程未配置");
			getValueStack().set(DATA, map);
			return DATA;
		}
		
		businessInfoChange=businessInfoChangeService.getNewInfoChange(classId,getUser().getYhm(),"add");
		map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("businessInfoChange", businessInfoChange);
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
	public PageList<BusinessInfoChange> getPageList() {
		return pageList;
	}

	/**
	 * 设置
	 * @param pageList 
	 */
	public void setPageList(PageList<BusinessInfoChange> pageList) {
		this.pageList = pageList;
	}

	/**
	 * 返回
	 */
	public BusinessInfoQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(BusinessInfoQuery query) {
		this.query = query;
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
	public BusinessInfoChange getBusinessInfoChange() {
		return businessInfoChange;
	}

	/**
	 * 设置
	 * @param businessInfoChange 
	 */
	public void setBusinessInfoChange(BusinessInfoChange businessInfoChange) {
		this.businessInfoChange = businessInfoChange;
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

	/**
	 * 返回
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * 设置
	 * @param businessInfoChangeService 
	 */
	public void setBusinessInfoChangeService(
			IBusinessInfoChangeService businessInfoChangeService) {
		this.businessInfoChangeService = businessInfoChangeService;
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
	public String getInitTagId() {
		return initTagId;
	}

	/**
	 * 设置
	 * @param initTagId 
	 */
	public void setInitTagId(String initTagId) {
		this.initTagId = initTagId;
	}
}
