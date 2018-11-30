package com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveProcess;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveProcessQuery;

/** 
 * 离校处理service
 * @author jinjj
 * @date 2012-8-3 上午02:31:47 
 *  
 */
public interface ILeaveProcessService {

	
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
	 * 获取分页
	 * @param query
	 * @return
	 */
	public PageList<LeaveProcess> getPagingList(LeaveProcessQuery query);
}
