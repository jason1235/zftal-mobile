package com.zfsoft.hrm.expertvote.vote.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertGroup;
import com.zfsoft.hrm.expertvote.vote.query.ExpertGroupQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-11
 * @version V1.0.0
 */
public interface IExpertGroupService {
	
	public PageList<ExpertGroup> getPageList(ExpertGroupQuery query);
	
	public List<ExpertGroup> getList(ExpertGroupQuery query);
	
	public ExpertGroup getById(String id);
	
	public void saveExpertGroup(ExpertGroup expertGroup);
	
	public void modifyExpertGroup(ExpertGroup expertGroup);
	
	public void delete(String id);
	
}
