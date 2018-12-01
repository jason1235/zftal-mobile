package com.zfsoft.hrm.summary.roster.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.summary.roster.entity.RosterConfig;
import com.zfsoft.hrm.summary.roster.entity.RosterConfigRelation;
import com.zfsoft.hrm.summary.roster.query.RosterConfigRelationQuery;

/** 
 * 花名册条件关系service
 * @author jinjj
 * @date 2012-9-6 下午02:00:48 
 *  
 */
public interface IRosterConfigRelationService {

	/**
	 * 新增
	 * @param relation
	 */
	public void save(RosterConfigRelation relation);
	
	/**
	 * 删除
	 * @param relation
	 */
	public void delete(RosterConfigRelation relation);
	
	/**
	 * 获取列表
	 * @param query
	 * @return
	 */
	public List<RosterConfig> getList(RosterConfigRelationQuery query);
}
