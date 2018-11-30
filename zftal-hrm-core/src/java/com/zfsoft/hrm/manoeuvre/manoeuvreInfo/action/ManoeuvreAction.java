package com.zfsoft.hrm.manoeuvre.manoeuvreInfo.action;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dataprivilege.util.DeptFilterUtil;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.core.util.DownloadFilenameUtil;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfiguration;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditStatus;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditStatusQuery;
import com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface.IAuditConfigurationService;
import com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface.IAuditStatusService;
import com.zfsoft.hrm.manoeuvre.exception.ManoeuvreException;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.entities.ManoeuvreInfo;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.query.ManoeuvreQuery;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.service.svcinterface.IManoeuvreService;
import com.zfsoft.hrm.overall.base.entity.OverAll;
import com.zfsoft.hrm.overall.base.service.IOverAllService;
import com.zfsoft.hrm.util.WordExportUtil;
import com.zfsoft.util.base.StringUtil;

public class ManoeuvreAction extends HrmAction implements ModelDriven<ManoeuvreInfo> {

	private static final long serialVersionUID = 1545241107064651841L;

	private ManoeuvreInfo model = new ManoeuvreInfo();
	
	private AuditStatus auditStatus;
	
	private ManoeuvreQuery query = new ManoeuvreQuery();
	
	private PageList<ManoeuvreInfo> pageList;
	
	private String listType;
	
	private String editType;
	
	private IManoeuvreService service;
	
	private IAuditStatusService auditStatusService;
	
	private IAuditConfigurationService auditConfigurationService;
	
	private IOverAllService overAllService;
	
	private static final String AUDIT_PAGE = "audit";
	
	private static final String SHOW_PAGE = "show";
	
	private static final String SHOWWINERROR = "showWinError";
	
	private static final String PASS_LIST = "passlist";

	private static final String PASS_EDIT = "passedit";
	
	// 20140506 add start
	private static final String EXPORT_LIST = "exportList";
	// 20140506 add end
	
	private String  changeDateEndString; //变更时间下限
	
	private String changeDateStartSting; //变更时间上限
	
	private String sortFieldName=null;
	private String asc="up";
	
	private String staffId;
	
	private String category;
	
	//----------------------------------------------------------------------------------------
	/**
	 * 待审核记录
	 */
	public String auditingList(){
		String userid = SessionFactory.getUser().getYhm();

		query.setUseFinishAudit(true);
		query.setFinishAudit(false);
		query.setUseBeenDeclared(true);
		query.setBeenDeclared(true);
		query.setWideStaffid(true);
		DynaBean selfInfo = DynaBeanUtil.getPerson(userid);
		if( selfInfo == null ) {
			selfInfo = new DynaBean( InfoClassCache.getOverallInfoClass() );
		}
		query.setSelfOrg((String)selfInfo.getValue("dwm"));
		queryList();
		getValueStack().set("path", "auditingList");
		return "viewlist";
	}
	
	/**
	 * 已审核记录
	 * @param: 
	 * @return:
	 */
	public String auditedList(){
		String userid = SessionFactory.getUser().getYhm();	
//		query.setUseFinishAudit(true);
//		query.setFinishAudit(false);
		query.setUseBeenDeclared(true);   //使用"是否已提交"
		query.setBeenDeclared(true);      //是否已提交
//		query.setWideStaffid(true);       //是否将申请人条件作为模糊查询
		DynaBean selfInfo = DynaBeanUtil.getPerson(userid);
		if( selfInfo == null ) {
			selfInfo = new DynaBean( InfoClassCache.getOverallInfoClass() );
		}
		query.setSelfOrg((String)selfInfo.getValue("dwm"));
		queryList();
		getValueStack().set("op", "N");    //设置操作权限
		getValueStack().set("path", "auditedList");
		return "viewlist";
	}
	
	public String list() throws ParseException{					
		if (getInt("toPage") != -1) {
			query.setToPage(this.getInt("toPage"));
        }
		String userid = SessionFactory.getUser().getYhm();
		if(userid == null || "".equals(userid)){
			throw new ManoeuvreException("当前用户session有误！");
		}
		if(!"commited".equals(listType) && !"pass".equals(listType)){
			query.setUseCreatedByHR(true);
			query.setCreatedByHR(false);
		}
		if("declare".equals(listType)){
			query.setStaffid(userid);
		}
		
		else if("audit".equals(listType)){
			return "view";
		}
		
		else if("commited".equals(listType)){
			query.setUseFinishAudit(true);
			query.setFinishAudit(true);
			query.setWideStaffid(true);
		}
		else if("pass".equals(listType)){
			query.setAuditResult(AuditStatus.FINALRESULT_PASS);
			
			// 判断变更时间查询条件是否为空，不为空填充时间  by陈成豪
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(changeDateEndString!=null&&!changeDateEndString.equals("")){
				query.setChangeDateEnd(sdf.parse(changeDateEndString));
			}
			if(changeDateStartSting!=null&&!changeDateStartSting.equals("")){
				query.setChangeDateStart(sdf.parse(changeDateStartSting));
			}
		}
		queryList();
		if("pass".equals(listType)){
			return PASS_LIST;
		}
		return LIST_PAGE;
	}
	//查询审核的结果集
	private void queryList(){
		String userid = SessionFactory.getUser().getYhm();
		List<AuditConfiguration> auditConfigurationList = new ArrayList<AuditConfiguration>();
		if(!"declare".equals(listType) && !"pass".equals(listType)){
			auditConfigurationList = service.getAuditConfigurationList(userid);
		}
		query.setAuditConfigurationList(auditConfigurationList);
		if(!StringUtil.isEmpty(sortFieldName)){
			if(asc.equals("up")){
				query.setOrderStr( sortFieldName );
			}else{
				query.setOrderStr( sortFieldName +" desc");
			}
		}else{
			query.setOrderStr( "gh" );
		}
		
		if ("pass".equals(listType)) {
			String express = " and (" + DeptFilterUtil.getCondition("", "ybm") + "or "+ DeptFilterUtil.getCondition("", "drbm") + ")";
			query.setExpress(express);
		}
		
		if("audit".equals(listType) && (auditConfigurationList == null || auditConfigurationList.size() == 0)){
			pageList = new PageList<ManoeuvreInfo>();
			pageList.setPaginator(query.toPaginator());
		}
		else{
			pageList = service.getPageList(query);
		}
		this.setInActionContext("paginator", pageList.getPaginator());
	}

	// 20140506 add start
	public String exportList() throws ParseException {
		query.setPerPageSize(Integer.MAX_VALUE);
		list();
		return EXPORT_LIST;
	}
	// 20140506 add end
	
	public String edit(){
		try{
			if(model.getGuid() == null && !model.isCreatedByHR()){
				String userid = SessionFactory.getUser().getYhm();
				Assert.isTrue(!StringUtil.isEmpty(userid), "当前用户session有误！");
				model.setStaffid(userid);
			}
			else if(model.getGuid() != null){
				model = service.getById(model.getGuid());
			}
			if("pass".equals(listType)){
				return PASS_EDIT;
			}
			//如果model变更时间为空给予默认的当前时间   by陈成豪
			if(model.getChangeTime()==null){
				model.setChangeTime(new Date());
				model.getPersonInfo();
			}
			//如果model的oldFormationType（前编制类型）为空的话并且由createByHR为false（表示为当前用户），查询填充，by陈成豪
			if(!model.isCreatedByHR()&&(model.getOldFormationType()==null||model.getOldFormationType().equals(""))){
				OverAll	overAll = overAllService.getByGh( SessionFactory.getUser().getYhm());
				if(overAll==null){
					throw new Exception("找不到该用户的基本信息，无法进行调动申请！");
				}
				model.setOldFormationType(overAll.getBzlbm());
			}
			return EDIT_PAGE;
		}catch(Exception e){
			getValueStack().set("message", e.getMessage());
			return SHOWWINERROR;
		}
	}
	
	/**
	 * 保存
	 * @param: 
	 * @return:
	 */
	public String save(){
		Assert.notNull(model, "人员调配申报信息不能为空！");
		
		String userid = SessionFactory.getUser().getYhm();
		Assert.isTrue(!StringUtil.isEmpty(userid), "当前用户session有误！");
		
		if(model.isCreatedByHR()){
			model.setBeenDeclared(true);
			model.setApplyTime(new Date());
			model.setFinishAudit(true);
			model.setAuditResult(AuditStatus.FINALRESULT_PASS);
		}
		else{
			model.setStaffid(userid);
		}
		model.setCurrentOrg((String)model.getPersonInfo().getValue("dwm"));
		model.setCurrentPost((String)model.getPersonInfo().getValue("rzgwm"));
		model.setOldFormationType((String)model.getPersonInfo().getValue("bzlbm"));
		model.setCurrentPostType((String)model.getPersonInfo().getValue("gwlb"));

		if(service.save(model)){
			setSuccessMessage("新增申报信息成功！");
		}
		else{
			setErrorMessage("新增申报信息失败！");
		}		
		//PendingAffairUtil.save("人员调配信息修改", null, "/manoeuvre/manoeuvre_edit.html?guid="+model.getGuid(), null, null);
		getValueStack().set("data", this.getMessage()); 
		return DATA;
	}
	/**
	 * 保存并执行
	 * @param: 
	 * @return:
	 */
	public String saveAndExecute(){
		Assert.notNull(model, "人员调配申报信息不能为空！");
		
		if(StringUtil.isEmpty(model.getGuid())){
			String userid = SessionFactory.getUser().getYhm();
			Assert.isTrue(!StringUtil.isEmpty(userid), "当前用户session有误！");
			
			if(model.isCreatedByHR()){
				model.setBeenDeclared(true);
				model.setApplyTime(new Date());
				model.setFinishAudit(true);
				model.setAuditResult(AuditStatus.FINALRESULT_PASS);
			}
			else{
				model.setStaffid(userid);
			}
			model.setCurrentOrg((String)model.getPersonInfo().getValue("dwm"));
			model.setCurrentPost((String)model.getPersonInfo().getValue("rzgwm"));
			model.setOldFormationType((String)model.getPersonInfo().getValue("bzlbm"));
			model.setCurrentPostType((String)model.getPersonInfo().getValue("gwlb"));
			model.setExcuteTime(model.getApplyTime());

			if(service.add(model)){
				setSuccessMessage("新增申报信息成功！");
			}
			else{
				setErrorMessage("新增申报信息失败！");
			}
		}else{
			if(service.modify(model)){
				setSuccessMessage("修改申报信息成功！");
			}
			else{
				setErrorMessage("修改申报信息失败！");
			}
		}
		
		//PendingAffairUtil.save("人员调配信息修改", null, "/manoeuvre/manoeuvre_edit.html?guid="+model.getGuid(), null, null);
		getValueStack().set("data", this.getMessage()); 
		return DATA;
	}
	
	/**
	 * 执行
	 */
	public String execute(){
		Assert.notNull(model, "人员调配申报信息不能为空！");
		//判断model是否有值，如果没有则新增，如果有则修改
		if(StringUtil.isEmpty(model.getGuid())){
			String userid = SessionFactory.getUser().getYhm();
			Assert.isTrue(!StringUtil.isEmpty(userid), "当前用户session有误！");
			//是否人事处创建
			if(model.isCreatedByHR()){
				model.setBeenDeclared(true);
				model.setApplyTime(new Date());
				model.setFinishAudit(true);
				model.setAuditResult(AuditStatus.FINALRESULT_PASS);
			}
			else{
				model.setStaffid(userid);
			}
			model.setCurrentOrg((String)model.getPersonInfo().getValue("dwm"));
			model.setCurrentPost((String)model.getPersonInfo().getValue("rzgwm"));
			model.setOldFormationType((String)model.getPersonInfo().getValue("bzlbm"));
			model.setCurrentPostType((String)model.getPersonInfo().getValue("gwlb"));
			if(service.add(model)){
				setSuccessMessage("新增申报信息成功！");
			}
			else{
				setErrorMessage("新增申报信息失败！");
			}
		}else{
			if(service.modify(model)){
				setSuccessMessage("修改申报信息成功！");
			}
			else{
				setErrorMessage("修改申报信息失败！");
			}
		}
		
		//PendingAffairUtil.save("人员调配信息修改", null, "/manoeuvre/manoeuvre_edit.html?guid="+model.getGuid(), null, null);
		getValueStack().set("data", this.getMessage()); 
		return DATA;
	}
	
	public String remove(){
		Assert.notNull(model, "人员调配申报信息不能为空！");
		Assert.notNull(model.getGuid(), "未选定任何信息");
		service.remove(model.getGuid());
		setSuccessMessage("删除申报信息成功！");
		getValueStack().set("data", this.getMessage());
		return DATA;
	}
	
	public String commit(){
		Assert.notNull(model, "人员调配申报信息不能为空！");
		model = service.getById(model.getGuid());
		model.setBeenDeclared(true);
		model.setApplyTime(new Date());
		if(service.modify(model) && service.modifyCurrentTask(model)){
			setSuccessMessage("上报成功！");
		}
		else{
			setErrorMessage("上报失败");
		}
		getValueStack().set("data", this.getMessage());
		return DATA;
	}
	
	public String show(){
		if(query == null || StringUtil.isEmpty(query.getGuid())){
			throw new ManoeuvreException("未选定任何记录");
		}
		model = service.getById(query.getGuid());
		Assert.notNull(model, "指定记录已不存在");
		AuditStatusQuery auditStatusQuery = new AuditStatusQuery();
		auditStatusQuery.setManoeuvreInfo(model);
		List<AuditStatus> auditStatusList = auditStatusService.getList(auditStatusQuery);
		getValueStack().set("auditStatusList", auditStatusList);
		return SHOW_PAGE;
	}
	
	public String export(){
		if(query == null || StringUtil.isEmpty(query.getGuid())){
			throw new ManoeuvreException("未选定任何记录");
		}
		model = service.getById(query.getGuid());
		Assert.notNull(model, "指定记录已不存在");
		AuditStatusQuery auditStatusQuery = new AuditStatusQuery();
		auditStatusQuery.setManoeuvreInfo(model);
		List<AuditStatus> auditStatusList = auditStatusService.getList(auditStatusQuery);
		getValueStack().set("auditStatusList", auditStatusList);
		return "export";
	}
	
	public String exportWord() {
		
		model = service.getById(query.getGuid());
		if (staffId == null) {
			staffId = SessionFactory.getUser().getYhm();
		}
		OverAll overAll = overAllService.getByGh(staffId);
		Map<String, Object> info = new HashMap<String, Object>();
		Map<String, Object> data = new HashMap<String, Object>();
		if (overAll != null) {
			info.put("jbxxb.xm", safeString(overAll.getXm()));
			info.put("jbxxb.xbm", safeString(CodeUtil.getItemValue("DM_GB_B_RDXBDM", overAll.getXbm())));
			info.put("jbxxb.csrq", new SimpleDateFormat("yyyy.M").format(overAll.getCsrq()));
			info.put("jbxxb.xzzw", safeString(CodeUtil.getItemValue("DM_GB_B_ZYJSZWDM", overAll.getZjzw())));
			info.put("jbxxb.zjzw",safeString(CodeUtil.getItemValue("DM_GB_B_ZYJSZWDM", overAll.getZjzw())));
		}
		if (model != null) {
			info.put("xndd.ybm", safeString(CodeUtil.getItemValue("DM_DEF_ORG", model.getCurrentOrg())));
			info.put("xndd.drbm", safeString(CodeUtil.getItemValue("DM_DEF_ORG", model.getPlanOrg())));
			info.put("xndd.dpzxsj", safeString(model.getChangeTimeText()));
			info.put("xndd.xgw", safeString(model.getPlanPostTypeText()));
			info.put("xndd.dpyy", safeString(model.getReason()));
		}
		
		data.put("info", info);
		getResponse().reset();
		
		getResponse().setCharacterEncoding("utf-8");
		getResponse().setContentType("application/msword");
		String useragent = getRequest().getHeader("user-agent");
		String disposition;
		String title = "校内流动审批表.doc";
		String template = "xndd_sp.xml";
		if (category != null) {
			title = safeString(overAll.getXm()) + "介绍信.doc";
			template = "rd_letter.xml";
		}
		try {
			disposition = DownloadFilenameUtil.fileDisposition(useragent,title);
			getResponse().setHeader("Content-Disposition", disposition);
			WordExportUtil.getInstance().exportTable(getResponse().getOutputStream(), template, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	private String safeString(String str) {
		if (str == null) {
			return "";
		} else {
			return str;
		}
	}
	
	public String toAudit(){
		try{
			if(model == null || StringUtil.isEmpty(model.getGuid())){
				throw new ManoeuvreException("未选定任何记录");
			}
			model = service.getById(model.getGuid());
			if(model == null){
				model = new ManoeuvreInfo();
			}
			return AUDIT_PAGE;
		}catch(Exception e){
			getValueStack().set("message", e.getMessage());
			return SHOWWINERROR;
		}
	}
	
	public String audit(){ 
		if(auditStatus == null || StringUtil.isEmpty(auditStatus.getResult())){
			throw new ManoeuvreException("审核结果信息不能为空");
		}
		if(model == null || StringUtil.isEmpty(model.getGuid())){
			throw new ManoeuvreException("审核信息不能为空");
		}
		model = service.getById(model.getGuid());
		Assert.notNull(model, "未找到指定信息");
		Assert.notNull(model.getCurrentNode(), "所选信息未提交，不可审核");
		String userid = SessionFactory.getUser().getYhm();
		auditStatus.setAssessor(userid);
		service.doAudit(model, auditStatus);
		setSuccessMessage("审核成功！");
		getValueStack().set("data", this.getMessage());
		return DATA;
	}
	
	/*private DynaBean getPersonInfo(String staffid){
		DynaBean result = DynaBeanUtil.getPerson(staffid);
		
		if( result == null ) {
			result = new DynaBean( InfoClassCache.getOverallInfoClass() );
		}
		
		return result;
	}*/
	
	/*private boolean couldAddStaffid(AuditConfiguration auditConfiguration, String planOrg, String currentOrg){
		String reg = getPersonInfo(auditConfiguration.getAssessor()).getValue("dwm") + "[a-zA-Z_0-9]{0,100}";
		if("1".equals(auditConfiguration.getExtension())){
			return true;
		}
		if("0".equals(auditConfiguration.getExtensionType()) && planOrg.matches(reg)){
			return true;
		}
		if("1".equals(auditConfiguration.getExtensionType()) && currentOrg.matches(reg)){
			return true;
		}
		return false;
	}*/
	
	//----------------------------------------------------------------------------------------
	
	/*private void addPendingAffair(String guid){
		ManoeuvreInfo info = service.getById(guid);
		if(info == null || info.getCurrentNode() == null ||  StringUtil.isEmpty(info.getCurrentNode().getNid())){
			return;
		}
		List<String> staffidList = new ArrayList<String>();
		AuditConfigurationQuery acQuery = new AuditConfigurationQuery();
		acQuery.setTaskNode(info.getCurrentNode());
		acQuery.setUndeleted(true);
		List<AuditConfiguration> aclist = auditConfigurationService.getList(acQuery);
		for (AuditConfiguration auditConfiguration : aclist) {
			if(couldAddStaffid(auditConfiguration, info.getPlanOrg(), info.getCurrentOrg())){
				staffidList.add(auditConfiguration.getAssessor());
			}
		}
		if(staffidList.size() == 0){
			return;
		}
		String[] staffids = new String[staffidList.size()];
		int staffIndex = 0;
		for (String staffid : staffidList) {
			staffids[staffIndex++] = staffid;
		}
		PendingAffairUtil.save(staffids, "待办事宜审核"+info.getCurrentNode().getNodeName(), null, "N060202", true);
		
	}*/
	
	
	//----------------------------------------------------------------------------------------
	
	/**
	 * 返回
	 * @return 
	 */
	public ManoeuvreInfo getModel() {
		return model;
	}

	/**
	 * 设置
	 * @param model 
	 */
	public void setModel(ManoeuvreInfo model) {
		this.model = model;
	}

	/**
	 * 返回
	 * @return 
	 */
	public AuditStatus getAuditStatus() {
		return auditStatus;
	}

	/**
	 * 设置
	 * @param auditStatus 
	 */
	public void setAuditStatus(AuditStatus auditStatus) {
		this.auditStatus = auditStatus;
	}

	/**
	 * 返回
	 * @return 
	 */
	public ManoeuvreQuery getQuery() {
		return query;
	}

	/**
	 * 设置
	 * @param query 
	 */
	public void setQuery(ManoeuvreQuery query) {
		this.query = query;
	}

	/**
	 * 返回
	 * @return 
	 */
	public PageList<ManoeuvreInfo> getPageList() {
		return pageList;
	}

	/**
	 * 设置
	 * @param pageList 
	 */
	public void setPageList(PageList<ManoeuvreInfo> pageList) {
		this.pageList = pageList;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getListType() {
		return listType;
	}

	/**
	 * 设置
	 * @param listType 
	 */
	public void setListType(String listType) {
		this.listType = listType;
	}

	/**
	 * 返回
	 * @return 
	 */
	public String getEditType() {
		return editType;
	}

	/**
	 * 设置
	 * @param editType 
	 */
	public void setEditType(String editType) {
		this.editType = editType;
	}

	/**
	 * 返回
	 * @return 
	 */
	public IManoeuvreService getService() {
		return service;
	}

	/**
	 * 设置
	 * @param service 
	 */
	public void setService(IManoeuvreService service) {
		this.service = service;
	}

	/**
	 * 返回
	 * @return 
	 */
	public IAuditStatusService getAuditStatusService() {
		return auditStatusService;
	}

	/**
	 * 设置
	 * @param auditStatusService 
	 */
	public void setAuditStatusService(IAuditStatusService auditStatusService) {
		this.auditStatusService = auditStatusService;
	}

	/**
	 * 返回
	 * @return 
	 */
	public IAuditConfigurationService getAuditConfigurationService() {
		return auditConfigurationService;
	}

	/**
	 * 设置
	 * @param auditConfigurationService 
	 */
	public void setAuditConfigurationService(
			IAuditConfigurationService auditConfigurationService) {
		this.auditConfigurationService = auditConfigurationService;
	}
    
	public IOverAllService getOverAllService() {
		return overAllService;
	}

	public void setOverAllService(IOverAllService overAllService) {
		this.overAllService = overAllService;
	}

	public String getChangeDateEndString() {
		return changeDateEndString;
	}

	public void setChangeDateEndString(String changeDateEndString) {
		this.changeDateEndString = changeDateEndString;
	}

	public String getChangeDateStartSting() {
		return changeDateStartSting;
	}

	public void setChangeDateStartSting(String changeDateStartSting) {
		this.changeDateStartSting = changeDateStartSting;
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

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}
	
	
}
