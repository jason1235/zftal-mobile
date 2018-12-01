package com.zfsoft.hrm.expertvote.expertmanage.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBean;
import com.zfsoft.hrm.baseinfo.dyna.util.DynaBeanUtil;
import com.zfsoft.hrm.dybill.entity.SpBillInstance;
import com.zfsoft.hrm.dybill.service.ISpBillInstanceService;
import com.zfsoft.hrm.expertvote.expertmanage.dao.IExpertDeclareDao;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertDeclare;
import com.zfsoft.hrm.expertvote.expertmanage.query.ExpertAuditQuery;
import com.zfsoft.hrm.expertvote.expertmanage.service.IExpertDeclareService;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.workflow.enumobject.BusinessEnum;
import com.zfsoft.workflow.enumobject.WorkNodeStatusEnum;
import com.zfsoft.workflow.exception.WorkFlowException;
import com.zfsoft.workflow.model.SpBusiness;
import com.zfsoft.workflow.service.ISpBusinessService;
import com.zfsoft.workflow.service.ISpWorkFlowService;

public class ExpertDeclareServiceImpl implements IExpertDeclareService {
	private IExpertDeclareDao expertDeclareDao;
	private ISpWorkFlowService spWorkFlowService;
	private ISpBusinessService spBusinessService;
	private ISpBillInstanceService spBillInstanceService;

	@Override
	public PageList<ExpertDeclare> getPagedList(ExpertDeclare zjkDeclare) {
		PageList<ExpertDeclare> pageList = new PageList<ExpertDeclare>();
		pageList.addAll(expertDeclareDao.getPagedList(zjkDeclare));
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(zjkDeclare.getShowCount());
		paginator.setPage(zjkDeclare.getCurrentPage());
		paginator.setItems(zjkDeclare.getTotalResult());
		pageList.setPaginator(paginator);
		return pageList;
	}
	@Override
	public PageList<ExpertDeclare> getPageList(ExpertAuditQuery query){
		PageList<ExpertDeclare> pageList = new PageList<ExpertDeclare>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(expertDeclareDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ExpertDeclare> list = expertDeclareDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insert(ExpertDeclare zjkDeclare) {
		expertDeclareDao.insert(zjkDeclare);
	}

	@Override
	public ExpertDeclare getById(String id) {
		return expertDeclareDao.getById(id);
	}

	@Override
	public void doInput(ExpertDeclare zjkDeclare, String workNum) {
		try {
			checkExists(zjkDeclare,workNum,false);
		} catch (RuntimeException e) {
			throw new WorkFlowException(e.getMessage());
		}
	}
	
	private void checkExists(ExpertDeclare zjkDeclare, String workNum, boolean bol){
		if(bol){
			fillInfo(zjkDeclare);
		}
		zjkDeclare.setSbz(workNum);
	}
	
	private void fillInfo(ExpertDeclare zjkDeclare){
		if(StringUtil.isEmpty(zjkDeclare.getInstance_id())){
			SpBillInstance spBillInstance = spBillInstanceService.getNewSpBillInstance(zjkDeclare.getConfig_id(), zjkDeclare.getTjrgh()); 
			zjkDeclare.setInstance_id(spBillInstance.getId());
			expertDeclareDao.updateDeclare(zjkDeclare);
		}
	}

	@Override
	public String doAdd(ExpertDeclare zjkDeclare, String workNum)
			throws RuntimeException {
		String privileges = "";
		try {
			
			if(StringUtil.isNotEmpty(zjkDeclare.getConfig_id())){
				ExpertDeclare expertDeclare = expertDeclareDao.getById(zjkDeclare.getId());
				zjkDeclare.setInstance_id(expertDeclare.getInstance_id());
				if(StringUtil.isEmpty(expertDeclare.getInstance_id())){
					SpBillInstance spBillInstance = spBillInstanceService.getNewSpBillInstance(zjkDeclare.getConfig_id(), zjkDeclare.getTjrgh());
					zjkDeclare.setInstance_id(spBillInstance.getId());
					expertDeclareDao.updateDeclare(zjkDeclare);
				}
				SpBusiness spb = spBusinessService.findSpBusinessByBcode(BusinessEnum.ZJ_TJSH.getKey(),null);
				privileges = spb.getBillClassesPrivilegeString();
				
			}else{
				throw new WorkFlowException("未配置自定义表单！");
			}
		} catch (RuntimeException e) {
			throw new WorkFlowException(e.getMessage());
		}
		
		return privileges;
	}

	@Override
	public void doPush(ExpertDeclare zjkDeclare, String workNum)
			throws RuntimeException {
		try {
			if(StringUtil.isEmpty(zjkDeclare.getId())){
				checkExists(zjkDeclare,workNum,true);
				zjkDeclare.setStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
				insert(zjkDeclare);
			}else{
				fillInfo(zjkDeclare);
			}
			ExpertDeclare zjkDeclare2=expertDeclareDao.getById(zjkDeclare.getId());
			zjkDeclare.setSbz(zjkDeclare2.getSbz());
			zjkDeclare.setTjrgh(zjkDeclare2.getTjrgh());
			zjkDeclare.setType(zjkDeclare2.getType());
			zjkDeclare.setStatus(zjkDeclare2.getStatus());
			//进行上报，开启流程
			HashMap<String,String> map = new HashMap<String,String>();
			map.put("businessCode", BusinessEnum.ZJ_TJSH.getKey());
			map.put("workId", zjkDeclare.getId());
			map.put("userId", zjkDeclare.getSbz());
			DynaBean selfInfo = DynaBeanUtil.getPerson(zjkDeclare.getSbz());
			if(selfInfo!=null){
				map.put("departmentId", (String)selfInfo.getValue("dwm"));
			}
			spWorkFlowService.addSpWorkFlow(map);
			//回填流程状态
			zjkDeclare.setStatus(WorkNodeStatusEnum.WAIT_AUDITING.getKey());
			zjkDeclare.setDedate(new Date());
			update(zjkDeclare,true);
		} catch (Exception e) {
			throw new WorkFlowException(e.getMessage());
		}
	}
	
	@Override
	public void update(ExpertDeclare zjkDeclare,boolean isNeed) throws RuntimeException {
		if(isNeed){
			fillInfo(zjkDeclare);
			ExpertDeclare updateZjkDeclare = getById(zjkDeclare.getId());
			if(updateZjkDeclare == null){
				throw new WorkFlowException("不存在专家推荐申请信息！");
			}/*else if(!updateZjkDeclare.getStatus().equals(WorkNodeStatusEnum.WAIT_AUDITING.getKey())){
				throw new WorkFlowException("专家推荐申请信息已经申请，不能修改申请信息！");
			}*/
		}
		expertDeclareDao.updateDeclare(zjkDeclare);
	}
	
	@Override
	public void delete(String id) throws RuntimeException {
		ExpertDeclare zjkDeclare = expertDeclareDao.getById(id);
		if(zjkDeclare != null){
			spWorkFlowService.doCancelDeclare(zjkDeclare.getId());
			if(!StringUtil.isEmpty(zjkDeclare.getConfig_id()) && !StringUtil.isEmpty(zjkDeclare.getInstance_id()))
			spBillInstanceService.removeSpBillInstance(zjkDeclare.getConfig_id(),zjkDeclare.getInstance_id());
		}
		expertDeclareDao.delete(id);
	}

	@Override
	public void doCancel(String id) throws RuntimeException {
		ExpertDeclare zjkDeclare = expertDeclareDao.getById(id);
		spWorkFlowService.doCancelDeclare(zjkDeclare.getId());
		zjkDeclare.setStatus(WorkNodeStatusEnum.INITAIL.getKey());
		expertDeclareDao.updateDeclare(zjkDeclare);
	}
	
	public void setExpertDeclareDao(IExpertDeclareDao expertDeclareDao) {
		this.expertDeclareDao = expertDeclareDao;
	}
	public void setSpWorkFlowService(ISpWorkFlowService spWorkFlowService) {
		this.spWorkFlowService = spWorkFlowService;
	}
	public void setSpBusinessService(ISpBusinessService spBusinessService) {
		this.spBusinessService = spBusinessService;
	}
	public void setSpBillInstanceService(
			ISpBillInstanceService spBillInstanceService) {
		this.spBillInstanceService = spBillInstanceService;
	}
}
