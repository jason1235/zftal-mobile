package com.zfsoft.hrm.summary.roster.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.summary.roster.entity.RosterConfig;
import com.zfsoft.hrm.summary.roster.query.RosterConfigQuery;

/** 
 * 花名册配置DAO
 * @author jinjj
 * @date 2012-8-31 上午09:05:56 
 *  
 */
public interface IRosterConfigDao {

	/**
	 * 插入花名册配置
	 * @param config
	 */
	public void insert(RosterConfig config);
	
	/**
	 * 更新花名册配置
	 * @param config
	 */
	public void update(RosterConfig config);
	
	/**
	 * 获取花名册配置
	 * @param guid
	 * @return
	 */
	public RosterConfig getById(String guid);
	
	/**
	 * 删除花名册配置
	 * @param guid
	 */
	public void delete(String guid);
	
	/**
	 * 获取花名册配置分页数组
	 * @param query
	 * @return
	 */
	public List<RosterConfig> getPagingList(RosterConfigQuery query);
	
	/**
	 * 花名册配置分页计数
	 * @param query
	 * @return
	 */
	public int getPagingCount(RosterConfigQuery query);
	
	/**
	 * 获取花名册配置集合
	 * @param query
	 * @return
	 */
	public List<RosterConfig> getList(RosterConfigQuery query);
	
	/**
	 * 根据classid分组计数已配置的字段数量
	 * @return
	 */
	public List<Map<String,Object>> getCountByClassid();
}
