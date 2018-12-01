package com.zfsoft.hrm.summary.roster.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.summary.roster.entity.RosterConfig;
import com.zfsoft.hrm.summary.roster.query.RosterConfigQuery;

/** 
 * 花名册配置service
 * @author jinjj
 * @date 2012-8-30 上午11:44:54 
 *  
 */
public interface IRosterConfigService {

	/**
	 * 新增
	 * @param roster
	 */
	public void save(RosterConfig config);
	
	/**
	 * 更新
	 * @param roster
	 */
	public void update(RosterConfig config);
	
	/**
	 * 查询花名册配置实体
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
	 * 分页查询
	 * @param query
	 * @return
	 */
	public PageList<RosterConfig> getPagingList(RosterConfigQuery query);

	/**
	 * 查询字段集合
	 * @param query
	 * @return
	 */
	public List<RosterConfig> getList(RosterConfigQuery query);
	
	/**
	 * 获取各个信息类中的配置数量
	 * @return
	 */
	public Map<String,Object> getAllCounts();
}