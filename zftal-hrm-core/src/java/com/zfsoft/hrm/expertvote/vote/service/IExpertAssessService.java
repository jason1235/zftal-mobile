package com.zfsoft.hrm.expertvote.vote.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertAssessInstance;
import com.zfsoft.hrm.expertvote.vote.query.ExpertAssessQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-19
 * @version V1.0.0
 */
public interface IExpertAssessService {
	/**
	 * 创建
	 * @param instance
	 */
	void createInstanceList(ExpertAssessInstance instance);
	/**
	 * 清除
	 * @param instance
	 */
	void removeInstances(ExpertAssessInstance instance);
	/**
	 * 查询
	 * @return
	 */
	PageList<ExpertAssessInstance> getPageList(ExpertAssessQuery query);
	
	ExpertAssessInstance findById(String id);
	
	void modify(ExpertAssessInstance expertAssessInstance);
	
	int findCount(ExpertAssessQuery query);
	
	int doSaveResult(ExpertAssessQuery query);

}
