package com.zfsoft.hrm.summary.roster.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.summary.roster.entity.Roster;
import com.zfsoft.hrm.summary.roster.query.RosterQuery;

/** 
 * 花名册DAO
 * @author jinjj
 * @date 2012-8-30 上午10:56:22 
 *  
 */
public interface IRosterDao {

	/**
	 * 插入花名册
	 * @param roster
	 */
	public void insert(Roster roster);
	
	/**
	 * 更新花名册
	 * @param roster
	 */
	public void update(Roster roster);
	
	/**
	 * 以ID查询花名册
	 * @param guid
	 * @return
	 */
	public Roster getById(String guid);
	
	/**
	 * 删除花名册
	 * @param guid
	 */
	public void delete(String guid);
	
	/**
	 * 获取花名册分页数组
	 * @param query
	 * @return
	 */
	public List<Roster> getPagingList(RosterQuery query);
	
	/**
	 * 花名册分页计数
	 * @param query
	 * @return
	 */
	public int getPagingCount(RosterQuery query);
}
