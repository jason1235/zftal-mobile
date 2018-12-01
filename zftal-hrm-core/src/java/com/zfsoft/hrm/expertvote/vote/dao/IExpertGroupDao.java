package com.zfsoft.hrm.expertvote.vote.dao;

import java.util.List;

import com.zfsoft.hrm.expertvote.vote.entity.ExpertGroup;
import com.zfsoft.hrm.expertvote.vote.query.ExpertGroupQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-11
 * @version V1.0.0
 */
public interface IExpertGroupDao {

	public void insert(ExpertGroup expertGroup);

	public void update(ExpertGroup declare);

	public ExpertGroup getById(String id);

	public void delete(String id);

	public List<ExpertGroup> getPagingList(ExpertGroupQuery query);
	
	public int getPagingCount(ExpertGroupQuery query);

	public List<ExpertGroup> getList(ExpertGroupQuery query);
}
