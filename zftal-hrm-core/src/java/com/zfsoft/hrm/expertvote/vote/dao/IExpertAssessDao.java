package com.zfsoft.hrm.expertvote.vote.dao;

import java.util.List;

import com.zfsoft.hrm.expertvote.vote.entity.ExpertAssessInstance;
import com.zfsoft.hrm.expertvote.vote.query.ExpertAssessQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-19
 * @version V1.0.0
 */
public interface IExpertAssessDao {
	/**
	 * 根据expertTaskDetail中的
	 * taskId,subject,groupId
	 * 来批量生成某学科由指定专家组进行该评审任务的实例对象
	 * @param expertTaskDetail
	 */
	void insertInstances(ExpertAssessInstance instance);
	/**
	 * 根据expertTaskDetail中的
	 * taskId,subject,
	 * 来批量删除某学科的实例对象以便之后重新生成实例
	 * @param expertTaskDetail
	 */
	void removeInstances(ExpertAssessInstance instance);
	
	List<ExpertAssessInstance> getInstanceList(ExpertAssessQuery query);
	
	List<ExpertAssessInstance> getInstancePageList(ExpertAssessQuery query);
	
	int getInstancePageCount(ExpertAssessQuery query);
	
	
	ExpertAssessInstance findById(String id);
	
	void modify(ExpertAssessInstance expertAssessInstance);
	
}
