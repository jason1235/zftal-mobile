package com.zfsoft.hrm.staffturn.leaveschool.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveFlowInfo;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveFlowInfoQuery;

/** 
 * 离校管理dao
 * @author jinjj
 * @date 2012-8-2 上午06:24:54 
 *  
 */
public interface ILeaveFlowDao {

	/**
	 * 新增离校流程管理
	 * @param info
	 */
	public void insert(LeaveFlowInfo info);
	
	/**
	 * 更新离校流程管理
	 * @param info
	 */
	public void update(LeaveFlowInfo info);
	
	/**
	 * 获取离校流程管理
	 * @param accountId
	 * @return
	 */
	public LeaveFlowInfo getById(String accountId);
	
	/**
	 * 获取离校分页集合
	 * @param query
	 * @return
	 */
	public List<LeaveFlowInfo> getList(LeaveFlowInfoQuery query);
	
	/**
	 * 分页信息
	 * @param query
	 * @return
	 */
	public List<LeaveFlowInfo> getPagingList(LeaveFlowInfoQuery query);
	
	/**
	 * 分页计数
	 * @param query
	 * @return
	 */
	public int getPagingCount(LeaveFlowInfoQuery query);
}
