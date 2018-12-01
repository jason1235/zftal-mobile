package com.zfsoft.hrm.summary.roster.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.summary.roster.entity.RosterColumn;

/** 
 * 花名册字段操作
 * @author jinjj
 * @date 2012-9-10 下午05:08:17 
 *  
 */
public interface IRosterColumnDao {

	/**
	 * 插入
	 * @param column
	 */
	public void insert(RosterColumn column);
	
	/**
	 * 删除
	 * @param column
	 */
	public void delete(RosterColumn column);
	
	/**
	 * 查询
	 * @param column
	 * @return
	 */
	public RosterColumn getById(RosterColumn column);
	
	/**
	 * 更新
	 * @param column
	 */
	public void update(RosterColumn column);
	
	/**
	 * 获取列表
	 * @param rosterId
	 * @return
	 */
	public List<RosterColumn> getList(String rosterId);
}
