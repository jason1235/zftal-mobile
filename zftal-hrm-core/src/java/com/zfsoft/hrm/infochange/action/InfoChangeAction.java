package com.zfsoft.hrm.infochange.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.dybill.enums.ModeType;
import com.zfsoft.hrm.dybill.enums.PrivilegeType;
import com.zfsoft.hrm.infochange.entity.InfoChange;
import com.zfsoft.hrm.infochange.entity.InfoChangeAudit;
import com.zfsoft.hrm.infochange.query.InfoChangeQuery;
import com.zfsoft.hrm.infochange.service.IInfoChangeService;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.service.ISpBusinessService;

/** 
 * @author Patrick Shen
 * @date 2013-6-9 下午02:22:08 
 */
public class InfoChangeAction extends HrmAction {

	private static final long serialVersionUID = 6469394187549730522L;

	private IInfoChangeService infoChangeService;
	private ISpBusinessService spBusinessService;
	private SpBusiness spBusiness;
	private PageList<InfoChange> pageList;
	private InfoChange infoChange = new InfoChange();
	private InfoChangeQuery query = new InfoChangeQuery();
	private List<String> years = new ArrayList<String>();
	private String guid;
	
	public String page() throws Exception{
		List<InfoClass> infoClasses=new ArrayList<InfoClass>();
		
		for (InfoClass infoClass : InfoClassCache.getInfoClasses()) {
			try{
				spBusinessService.findSpBusinessByRelDetail(infoClass.getGuid(),null);
				infoClasses.add(infoClass);
			}catch(Exception e){
			}
		}
		if(query.getClassId()==null&&infoClasses.size()>0){
			//query.setClassId(infoClasses.get(0).getGuid());
		}
		WorkNodeStatusEnum ostatus=query.getStatus();
		if(ostatus==null){
			query.setStatus(WorkNodeStatusEnum.INITAIL);
		}
//		if(query.getClassId()==null){
//			getValueStack().set("statusArray", WorkNodeStatusEnum.values());
//			getValueStack().set("infoClasses", infoClasses);
//			return "page";
//		}
		query.setUserId(getUser().getYhm());
		pageList = infoChangeService.getPagedList(query);
		int beginIndex = pageList.getPaginator().getBeginIndex();
		pageList.setPaginator(query);
		query.setStatus(ostatus);
		getValueStack().set("statusArray", WorkNodeStatusEnum.values());
		getValueStack().set("beginIndex",beginIndex);
		getValueStack().set("infoClasses", infoClasses);
		return "page";
	}
	public String getChangeString(){
		String message=infoChangeService.getChangeString(query.getClassId(), query.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("message", message);
		getValueStack().set(DATA, map);
		return DATA; 
	}
	public String check() throws Exception{
		//验证申报开关
		Boolean modified=infoChangeService.doCheckModify(query.getClassId(), query.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("modified", modified);
		map.put("message", "验证成功");
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	
	public String create() throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		try{
			spBusiness=spBusinessService.findSpBusinessByRelDetail(query.getClassId(),null);
		}catch(Exception e){
			map.put("success", false);
			map.put("message", "流程未配置");
			getValueStack().set(DATA, map);
			return DATA;
		}
		if(StringUtils.isEmpty(guid)){
			infoChange.setOpType("add");
		}
		query.setUserId(getUser().getYhm());
		// 只能存在一条未上报记录
		query.setStatus(WorkNodeStatusEnum.INITAIL);		
		if(infoChangeService.getInfoChangeList(query).size()>0){
			map = new HashMap<String,Object>();
			map.put("success", false);
			map.put("message", "已存在未提交记录，请先处理！");
			getValueStack().set(DATA, map);
			return DATA;
		}
		if("modify".equals(infoChange.getOpType())){
			// 只能存在一条待审核记录
			query.setExpress2(" and op_type = 'modify' and class_id = '"
					+query.getClassId()+"' and globalid = '"+guid
					+"' and status in ('"+WorkNodeStatusEnum.WAIT_AUDITING.getKey()+"','"+WorkNodeStatusEnum.IN_AUDITING.getKey()+"')");
			query.setStatus(null);
			if(infoChangeService.getInfoChangeList(query).size()>0){
				map = new HashMap<String,Object>();
				map.put("success", false);
				map.put("message", "该信息已经有变更记录处于审核过程中，请等待审核结束后再提交！");
				getValueStack().set(DATA, map);
				return DATA;
			}
		}
		
		infoChange=infoChangeService.getNewInfoChange(query.getClassId(),getUser().getYhm(),infoChange.getOpType(),guid);
		map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("infoChange", infoChange);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String commitBatch() throws Exception{
		String ids = getRequest().getParameter("id");
		String result = "";
		if(!StringUtils.isEmpty(ids)){
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				InfoChange info = infoChangeService.getInfoChangeById(null, id);
				try{
					Boolean modified=infoChangeService.doCheckModify(info.getClassId(), info.getId());
					if(modified){
						infoChangeService.doCommit(info.getClassId(),info.getId());
					}else{
						result+=info.getClassName()+"信息类没有进行修改，不能提交<br/>";
					}
				}catch (Exception e) {
					e.printStackTrace();
					result+=info.getClassName()+"信息类提交失败<br/>";
				}
			}
		}
		if(!StringUtils.isEmpty(ids)){
			setErrorMessage(result);
		}else{
			setSuccessMessage("提交成功");
		}
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String detail() throws Exception{
		infoChange = infoChangeService.getInfoChangeById(query.getClassId(),infoChange.getId());
//		String billConfigId=infoChange.getBillConfigId();
//		if(billConfigId==null){
		spBusiness=spBusinessService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
		String billConfigId=spBusiness.getBillId();
		String privilegeExpression = spBusiness.getBillClassesPrivilegeString();
		if(!"add".equals(infoChange.getOpType())){
			privilegeExpression = privilegeExpression
			.replaceAll(PrivilegeType.SEARCH_ADD_DELETE_EDIT.toString(), PrivilegeType.SEARCH_EDIT.toString())
			.replaceAll(PrivilegeType.SEARCH_ADD_DELETE.toString(), PrivilegeType.SEARCH.toString());
		}
		getValueStack().set("billConfigId",billConfigId);
		getValueStack().set("privilegeExpression",privilegeExpression);
//		}else{
//			getValueStack().set("billConfigId",billConfigId);
//			getValueStack().set("privilegeExpression","ALL_SEARCH");
//		}
	
		return "detail";
	}
	
	public String view() throws Exception{
		spBusiness=spBusinessService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
		infoChange = infoChangeService.getInfoChangeById(query.getClassId(),infoChange.getId());
		InfoChangeAudit audit = infoChangeService.getAudit(query.getClassId(),infoChange.getId());
		getValueStack().set("privilegeExpression",ModeType.SEARCH+"["+spBusiness.getClassesPrivilege()+"]");
		if(audit==null){
			return "view";
		}
		getValueStack().set("excutedList",audit.getExcutedList());
		getValueStack().set("logList",audit.getLogList());
		return "view";
	}
	
	public String modify() throws Exception{
		spBusiness=spBusinessService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
		infoChange = infoChangeService.getInfoChangeById(query.getClassId(),infoChange.getId());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		map.put("infoChange", infoChange);
		getValueStack().set(DATA, map);
		return DATA;
	}
	
	public String commit() throws Exception{
		Boolean modified=infoChangeService.doCheckModify(query.getClassId(), infoChange.getId());
		if(modified){
			infoChangeService.doCommit(query.getClassId(),infoChange.getId());
			setSuccessMessage("提交成功");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}else{
			setErrorMessage("信息类没有进行修改，不能提交");
			getValueStack().set(DATA, getMessage());
			return DATA;
		}
		
	}
	/**
	 * 取消
	 * @param: 
	 * @return:
	 */
	public String cancel() throws Exception{
		infoChangeService.doCancel(query.getClassId(),infoChange.getId());
		setSuccessMessage("取消成功");
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String recommit() throws Exception{
		infoChange=infoChangeService.doReCommit(query.getClassId(),infoChange.getId());
		spBusiness=spBusinessService.findSpBusinessByRelDetail(query.getClassId(),infoChange.getId());
		String billConfigId=spBusiness.getBillId();
		String privilegeExpression = spBusiness.getBillClassesPrivilegeString();
		if(!"add".equals(infoChange.getOpType())){
			privilegeExpression = privilegeExpression
			.replaceAll(PrivilegeType.SEARCH_ADD_DELETE_EDIT.toString(), PrivilegeType.SEARCH_EDIT.toString())
			.replaceAll(PrivilegeType.SEARCH_ADD_DELETE.toString(), PrivilegeType.SEARCH.toString());
		}
		getValueStack().set("billConfigId",billConfigId);
		getValueStack().set("privilegeExpression",privilegeExpression);
		return "detail";
	}
	
	public InfoChangeQuery getQuery() {
		return query;
	}

	public void setQuery(InfoChangeQuery query) {
		this.query = query;
	}

	public PageList<InfoChange> getPageList() {
		return pageList;
	}

	public void setInfoChangeService(IInfoChangeService infoChangeService) {
		this.infoChangeService = infoChangeService;
	}

	public InfoChange getInfoChange() {
		return infoChange;
	}

	public void setInfoChange(InfoChange infoChange) {
		this.infoChange = infoChange;
	}


	public List<String> getYears() {
		return years;
	}

	public void setSpBusinessService(ISpBusinessService spBusinessService) {
		this.spBusinessService = spBusinessService;
	}

	public SpBusiness getSpBusiness() {
		return spBusiness;
	}

	public void setSpBusiness(SpBusiness spBusiness) {
		this.spBusiness = spBusiness;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}
	
	
}
