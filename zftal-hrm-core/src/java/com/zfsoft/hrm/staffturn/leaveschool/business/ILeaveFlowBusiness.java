package com.zfsoft.hrm.staffturn.leaveschool.business;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveFlowInfo;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveFlowInfoQuery;

/** 
 * 离校管理business
 * @author jinjj
 * @date 2012-8-2 上午07:27:38 
 *  
 */
public interface ILeaveFlowBusiness {

	/**
	 * 新增离校人员
	 * @param info
	 */
	public void save(LeaveFlowInfo info);
	
	/**
	 * 更新离校状态
	 * @param info
	 */
	public void update(LeaveFlowInfo info);
	
	/**
	 * 集合列表
	 * @param query
	 * @return
	 */
	public List<LeaveFlowInfo> getList(LeaveFlowInfoQuery query);
	
	/**
	 * 获取离校对象
	 * @param accountId
	 * @return
	 */
	public LeaveFlowInfo getById(String accountId);
	
	/**
	 * 分页信息
	 * @param query
	 * @return
	 */
	public PageList<LeaveFlowInfo> getPagingList(LeaveFlowInfoQuery query);
}
