package com.zfsoft.hrm.summary.roster.service.svcinterface;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.summary.roster.entity.Roster;
import com.zfsoft.hrm.summary.roster.entity.RosterParam;
import com.zfsoft.hrm.summary.roster.query.RosterQuery;

/** 
 * 花名册service
 * @author jinjj
 * @date 2012-8-30 上午11:44:54 
 *  
 */
public interface IRosterService {

	/**
	 * 新增
	 * @param roster
	 */
	public void save(Roster roster);
	
	/**
	 * 更新
	 * @param roster
	 */
	public void update(Roster roster);
	
	/**
	 * 查询花名册实体
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
	 * 分页查询
	 * @param query
	 * @return
	 */
	public PageList<Roster> getPagingList(RosterQuery query);

	/**
	 * 花名册另存为
	 * @param roster
	 */
	public void saveOther(Roster roster,RosterParam param);
}