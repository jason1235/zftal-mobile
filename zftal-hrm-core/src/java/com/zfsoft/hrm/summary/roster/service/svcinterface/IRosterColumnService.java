package com.zfsoft.hrm.summary.roster.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.summary.roster.entity.RosterColumn;

/** 
 * 花名册字段service
 * @author jinjj
 * @date 2012-9-10 下午06:12:47 
 *  
 */
public interface IRosterColumnService {

	/**
	 * 保存
	 * @param column
	 */
	public void save(RosterColumn column);
	
	/**
	 * 删除
	 * @param column
	 */
	public void delete(RosterColumn column);
	
	/**
	 * 更新顺序
	 * @param column
	 * @param type TODO
	 */
	public void updateOrder(RosterColumn column, String type);
	
	/**
	 * 更新排序
	 * @param column
	 */
	public void updateSort(RosterColumn column);
	
	/**
	 * 获取集合
	 * @param rosterId
	 * @return
	 */
	public List<RosterColumn> getList(String rosterId);
}
