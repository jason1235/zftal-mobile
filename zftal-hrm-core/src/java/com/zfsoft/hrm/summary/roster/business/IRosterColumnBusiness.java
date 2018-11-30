package com.zfsoft.hrm.summary.roster.business;

import java.util.List;

import com.zfsoft.hrm.summary.roster.entity.RosterColumn;

/** 
 * 花名册字段business
 * @author jinjj
 * @date 2012-9-10 下午05:14:09 
 *  
 */
public interface IRosterColumnBusiness {

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
	 */
	public void updateOrder(RosterColumn column);
	
	/**
	 * 更新排序
	 * @param column
	 */
	public void updateSort(RosterColumn column);
	
//	public RosterColumn getById(RosterColumn column);
	
	/**
	 * 获取集合
	 */
	public List<RosterColumn> getList(String rosterId);
}
