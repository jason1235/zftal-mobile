package com.zfsoft.hrm.expertvote.vote.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertTask;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertTaskDetail;
import com.zfsoft.hrm.expertvote.vote.query.ExpertTaskQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-18
 * @version V1.0.0
 */
public interface IExpertTaskService {
	
	public PageList<ExpertTask> getPageList(ExpertTaskQuery query);
	
	public ExpertTask getById(String id);
	
	public void saveExpertGroup(ExpertTask expertTask);
	
	public void modifyExpertGroup(ExpertTask expertTask);
	
	public void delete(String id);

	public void getTaskCount(ExpertTaskDetail expertTaskDetail);
	
	public List<ExpertTaskDetail> getBsnsClassIds(ExpertTaskDetail expertTaskDetail);
	
	public List<String> getGroupIdByBsnsClassId(ExpertTaskDetail expertTaskDetail);
	
	public List<ExpertTask> getList(ExpertTaskQuery query);
}
