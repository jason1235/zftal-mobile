package com.zfsoft.hrm.expertvote.vote.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.expertvote.vote.dao.IDeclareInstanceDao;
import com.zfsoft.hrm.expertvote.vote.dao.IExpertTaskDao;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertTask;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertTaskDetail;
import com.zfsoft.hrm.expertvote.vote.query.ExpertTaskQuery;
import com.zfsoft.hrm.expertvote.vote.service.IExpertTaskService;
import com.zfsoft.workflow.enumobject.TaskCategoryEnum;
import com.zfsoft.workflow.enumobject.TaskNameEnum;
import com.zfsoft.workflow.model.SpTask;
import com.zfsoft.workflow.service.ISpTaskService;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-18
 * @version V1.0.0
 */
public class ExpertTaskServiceImpl implements IExpertTaskService {

	public IExpertTaskDao expertTaskDao;
	public ISpTaskService spTaskService;
	public IDeclareInstanceDao declareInstanceDao;

	@Override
	public void delete(String id) {
		if (expertTaskDao.getUseCount(id) > 0) {
			throw new RuleException("任务已经被使用 不允许删除！");
		}
		spTaskService.delete(id);
		expertTaskDao.delete(id);
	}
	@Override
	public List<String> getGroupIdByBsnsClassId(ExpertTaskDetail expertTaskDetail) {
		return expertTaskDao.findGroupByBsnsClassId(expertTaskDetail);
	}
	@Override
	public ExpertTask getById(String id) {
		return expertTaskDao.getById(id);
	}
	
	@Override
	public List<ExpertTask> getList(ExpertTaskQuery query) {
		return expertTaskDao.getList(query);
	}

	@Override
	public PageList<ExpertTask> getPageList(ExpertTaskQuery query) {
		PageList<ExpertTask> pageList = new PageList<ExpertTask>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer) query.getToPage());

			paginator.setItems(expertTaskDao.getPagingCount(query));
			pageList.setPaginator(paginator);

			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ExpertTask> list = expertTaskDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void modifyExpertGroup(ExpertTask expertTask) {
		// TODO Auto-generated method stub
		SpTask task = new SpTask();
		task.setBelongToSys("N25");
		task.setTaskId(expertTask.getId());
		task.setTaskName(expertTask.getName());
		task.setTaskDesc(expertTask.getName());
		task.setTaskStatus("1");
		task.setTaskCode(TaskNameEnum.EXPERT_BALLOT.getKey());
		task.setTaskType(TaskCategoryEnum.PERSON_OPERATE.getKey());
		spTaskService.update(task);
		expertTaskDao.update(expertTask);
	}

	@Override
	public void saveExpertGroup(ExpertTask expertTask) {
		SpTask task = new SpTask();
		task.setBelongToSys(expertTask.getBelongToSys());
		task.setTaskName(expertTask.getName());
		task.setTaskDesc(expertTask.getName());
		task.setTaskStatus("1");
		task.setTaskCode(TaskNameEnum.EXPERT_BALLOT.getKey());
		task.setTaskType(TaskCategoryEnum.PERSON_OPERATE.getKey());
		spTaskService.insert(task);
		expertTask.setId(task.getTaskId());
		expertTaskDao.insert(expertTask);
	}
	
	private int obj2Int(Object o){
		if(o==null){return 0;}
		try{
		return Integer.valueOf(o.toString().trim());
		}catch(NumberFormatException e){
			return 0;
		}
	}

	@Override
	public List<ExpertTaskDetail> getBsnsClassIds(ExpertTaskDetail expertTaskDetail){
		List<String> BsnsClassIds = expertTaskDao.getBsnsClassIdOfTask(expertTaskDetail);
		List<ExpertTaskDetail> list = new ArrayList<ExpertTaskDetail>();
		for (String bsnsClassId : BsnsClassIds) {
			ExpertTaskDetail detail = new ExpertTaskDetail();
			detail.setId(expertTaskDetail.getId());
			detail.setBusinessClassId(bsnsClassId);
			getTaskCount(detail);
			list.add(detail);
		}
		return list;
		
	}
	
	@Override
	public void getTaskCount(ExpertTaskDetail expertTaskDetail) {
		List<Map<String, Object>> data = expertTaskDao.getTaskCount(expertTaskDetail);
		for (Map<String, Object> map : data) {
			//节点未执行
			if ("WAIT_AUDITING".equals(map.get("STATUS"))) {
				if("WAIT_EXECUTE".equals(map.get("E_STATUS"))){
					expertTaskDetail.setBeforeNumber(obj2Int(map.get("NUM")));
				}
			}
			else if ("PASS_AUDITING".equals(map.get("STATUS"))) {
				if("WAIT_EXECUTE".equals(map.get("E_STATUS"))){
					expertTaskDetail.setWaitNumber(obj2Int(map.get("NUM")));
				}else if("ALREADY_EXECUTE".equals(map.get("E_STATUS"))){
					expertTaskDetail.setPassNumber(obj2Int(map.get("NUM")));
				}
			}
		}
	}

	/**
	 * 返回
	 */
	public IExpertTaskDao getExpertTaskDao() {
		return expertTaskDao;
	}

	/**
	 * 设置
	 * 
	 * @param expertTaskDao
	 */
	public void setExpertTaskDao(IExpertTaskDao expertTaskDao) {
		this.expertTaskDao = expertTaskDao;
	}

	/**
	 * 返回
	 */
	public ISpTaskService getSpTaskService() {
		return spTaskService;
	}

	/**
	 * 设置
	 * 
	 * @param spTaskService
	 */
	public void setSpTaskService(ISpTaskService spTaskService) {
		this.spTaskService = spTaskService;
	}
	/**
	 * 设置
	 * @param declareInstanceDao 
	 */
	public void setDeclareInstanceDao(IDeclareInstanceDao declareInstanceDao) {
		this.declareInstanceDao = declareInstanceDao;
	}

}
