package com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveFlowInfo;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveFlowInfoQuery;

/** 
 * @author jinjj
 * @date 2012-8-2 上午07:46:47 
 *  
 */
public interface ILeaveFlowService {

	/**
	 * 新增离校人员，并添加处理流程
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
	 * 获取分页
	 * @param query
	 * @return
	 */
	public PageList<LeaveFlowInfo> getPagingList(LeaveFlowInfoQuery query);
	/**
	 * 给处理人添加消息提醒
	 * @param: 
	 * @return:
	 */
	public void sendMessage(String receiver);
	/**
	 * 根据处理部门找到相应的处理人
	 * @param: 
	 * @return:
	 */
	public void getGhBYDwm(StringBuilder sb);
}
