package com.zfsoft.hrm.expertvote.vote.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.expertvote.vote.dao.IExpertAssessDao;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertAssessInstance;
import com.zfsoft.hrm.expertvote.vote.query.ExpertAssessQuery;
import com.zfsoft.hrm.expertvote.vote.service.IExpertAssessService;
import com.zfsoft.workflow.model.SpWorkTask;
import com.zfsoft.workflow.service.ISpWorkFlowService;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-19
 * @version V1.0.0
 */
public class ExpertAssessServiceImpl implements IExpertAssessService{

	private IExpertAssessDao expertAssessDao;
	private ISpWorkFlowService spWorkFlowService;
	@Override
	public void createInstanceList(ExpertAssessInstance instance) {
		expertAssessDao.insertInstances(instance);
	}

	@Override
	public void removeInstances(ExpertAssessInstance instance) {
		expertAssessDao.removeInstances(instance);
	}
	
	@Override
	public PageList<ExpertAssessInstance> getPageList(ExpertAssessQuery query) {
		PageList<ExpertAssessInstance> pageList = new PageList<ExpertAssessInstance>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(expertAssessDao.getInstancePageCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ExpertAssessInstance> list = expertAssessDao.getInstancePageList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	@Override
	public int doSaveResult(ExpertAssessQuery query){
		ExpertAssessQuery instanceQuery = new ExpertAssessQuery();
		query.setStatus("0");
		instanceQuery.setGroupId(query.getGroupId());
		instanceQuery.setTaskId(query.getTaskId());
		Date assessTime = new Date();
		List<ExpertAssessInstance> instances = expertAssessDao.getInstanceList(query);
		List<SpWorkTask> spWorkTaskList = new ArrayList<SpWorkTask>();
		for (ExpertAssessInstance expertAssessInstance : instances) {
			expertAssessInstance.setStatus("1");
			expertAssessInstance.setAssessTime(assessTime);
			expertAssessDao.modify(expertAssessInstance);
			instanceQuery.setResult(null);
			instanceQuery.setStatus(query.getStatus());
			instanceQuery.setWorkId(expertAssessInstance.getWorkId());
			if(findCount(instanceQuery)==0){
				instanceQuery.setStatus("1");
				int sum = findCount(instanceQuery);
				instanceQuery.setResult("1");
				int passNum = findCount(instanceQuery);
				
				SpWorkTask spWorkTask = new SpWorkTask();
				spWorkTask.setWid(expertAssessInstance.getWorkId());
				spWorkTask.setTaskId(expertAssessInstance.getTaskId());
				spWorkTask.setResult("参与评审的专家共"+sum+"人，其中 同意 "+passNum+"人");
				spWorkTaskList.add(spWorkTask);
			}
			
		}
		if(spWorkTaskList.size()>0){
			spWorkFlowService.doTaskRsult(spWorkTaskList);
		}
		return instances.size();
	};
	@Override
	public int findCount(ExpertAssessQuery query){
		return expertAssessDao.getInstancePageCount(query);
	}
	@Override
	public ExpertAssessInstance findById(String id) {
		return expertAssessDao.findById(id);
	}

	@Override
	public void modify(ExpertAssessInstance expertAssessInstance) {
		expertAssessDao.modify(expertAssessInstance);
	}

	public void setExpertAssessDao(IExpertAssessDao expertAssessDao) {
		this.expertAssessDao = expertAssessDao;
	}

	public void setSpWorkFlowService(ISpWorkFlowService spWorkFlowService) {
		this.spWorkFlowService = spWorkFlowService;
	}
	

}
