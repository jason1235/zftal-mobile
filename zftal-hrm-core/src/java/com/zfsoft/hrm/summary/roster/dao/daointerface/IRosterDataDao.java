package com.zfsoft.hrm.summary.roster.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.summary.roster.query.RosterDataQuery;

/** 
 * 花名册
 * @author jinjj
 * @date 2012-9-7 上午10:16:17 
 *  
 */
public interface IRosterDataDao {
	
	/**
	 * 获取全部列表数据
	 * @param query
	 * @return
	 */
	public List<Map<String,Object>> getList(RosterDataQuery query);
	
	/**
	 * 获取分页列表数据
	 * @param query
	 * @return
	 */
	public List<Map<String,Object>> getPagingList(RosterDataQuery query);
	
	/**
	 * 获取分页计数
	 * @param query
	 * @return
	 */
	public int getPagingCount(RosterDataQuery query);
}
