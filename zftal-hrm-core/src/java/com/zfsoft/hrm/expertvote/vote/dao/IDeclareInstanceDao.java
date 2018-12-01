package com.zfsoft.hrm.expertvote.vote.dao;

import java.util.List;

import com.zfsoft.hrm.expertvote.vote.entity.DeclareInstance;
import com.zfsoft.hrm.expertvote.vote.query.DeclareInstanceQuery;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-27
 * @version V1.0.0
 */
public interface IDeclareInstanceDao {
	int getInstancePageCount(DeclareInstanceQuery query);
	List<DeclareInstance> getInstancePageList(DeclareInstanceQuery query);
	DeclareInstance findById(String id);
}
