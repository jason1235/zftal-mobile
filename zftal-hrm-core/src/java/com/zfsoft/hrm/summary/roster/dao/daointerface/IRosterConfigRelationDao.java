package com.zfsoft.hrm.summary.roster.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.summary.roster.entity.RosterConfigRelation;
import com.zfsoft.hrm.summary.roster.query.RosterConfigRelationQuery;

/** 
 * 条件关联DAO
 * @author jinjj
 * @date 2012-9-6 上午11:45:11 
 *  
 */
public interface IRosterConfigRelationDao {

	/**
	 * 插入
	 * @param relation
	 */
	public void insert(RosterConfigRelation relation);
	
	/**
	 * 查询
	 * @param relation
	 * @return
	 */
	public RosterConfigRelation getById(RosterConfigRelation relation);
	
	/**
	 * 删除
	 * @param relation
	 */
	public void delete(RosterConfigRelation relation);
	
	/**
	 * 读取列表
	 * @param query
	 * @return
	 */
	public List<RosterConfigRelation> getList(RosterConfigRelationQuery query);
}
