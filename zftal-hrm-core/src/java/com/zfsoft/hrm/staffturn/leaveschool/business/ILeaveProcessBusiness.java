package com.zfsoft.hrm.staffturn.leaveschool.business;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveProcess;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveProcessQuery;

/** 
 * @author jinjj
 * @date 2012-8-2 上午09:51:36 
 *  
 */
public interface ILeaveProcessBusiness {

	/**
	 * 新增离校处理
	 * @param process
	 */
	public void save(LeaveProcess process);
	
	/**
	 * 更新离校处理
	 * @param process
	 */
	public void updateStatus(LeaveProcess process);
	
	/**
	 * 获取离校处理列表
	 * @param query
	 * @return
	 */
	public List<LeaveProcess> getList(LeaveProcessQuery query);
	
	/**
	 * 分页信息
	 * @param query
	 * @return
	 */
	public PageList<LeaveProcess> getPagingList(LeaveProcessQuery query);
	
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
	/**
	 * 根据guid查找离校处理信息
	 */
	public LeaveProcess getLeaveProcess(String guid);
}
