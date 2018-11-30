package com.zfsoft.hrm.staffturn.leaveschool.dao.daointerface;

import java.util.List;

import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveProcess;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveProcessQuery;

/** 
 * 离校处理DAO
 * @author jinjj
 * @date 2012-8-2 上午09:25:16 
 *  
 */
public interface ILeaveProcessDao {

	/**
	 * 新增离校处理
	 * @param process
	 */
	public void insert(LeaveProcess process);
	
	/**
	 * 更新离校处理
	 * @param process
	 */
	public void updateStatus(LeaveProcess process);
	
	/**
	 * 离校处理列表
	 * @param query
	 * @return
	 */
	public List<LeaveProcess> getList(LeaveProcessQuery query);
	
	/**
	 * 分页信息
	 * @param query
	 * @return
	 */
	public List<LeaveProcess> getPagingList(LeaveProcessQuery query);
	
	public LeaveProcess getLeaveProcess(String guid);
	/**
	 * 分页计数
	 * @param query
	 * @return
	 */
	public int getPagingCount(LeaveProcessQuery query);
	
	/**
	 * 更新离校处理人
	 * @param process
	 */
	public void updateOperator(LeaveProcess process);
	
	/**
	 * 删除
	 * @param process
	 */
	public void delete(LeaveProcess process);
}
