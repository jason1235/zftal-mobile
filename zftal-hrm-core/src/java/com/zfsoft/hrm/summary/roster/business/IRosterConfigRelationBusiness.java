package com.zfsoft.hrm.summary.roster.business;

import java.util.List;

import com.zfsoft.hrm.summary.roster.entity.RosterConfigRelation;
import com.zfsoft.hrm.summary.roster.query.RosterConfigRelationQuery;

/** 
 * 花名册条件关系business
 * @author jinjj
 * @date 2012-9-6 下午01:24:52 
 *  
 */
public interface IRosterConfigRelationBusiness {

	/**
	 * 保存，含唯一判断
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
	public List<RosterConfigRelation> getList(RosterConfigRelationQuery query);
	
}
