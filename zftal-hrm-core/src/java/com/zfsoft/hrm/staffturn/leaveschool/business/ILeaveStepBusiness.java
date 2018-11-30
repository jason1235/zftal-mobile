package com.zfsoft.hrm.staffturn.leaveschool.business;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveStep;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveStepQuery;

/** 
 * 离校步骤business
 * @author jinjj
 * @date 2012-8-1 上午12:56:18 
 *  
 */
public interface ILeaveStepBusiness {

	/**
	 * 新增步骤
	 * @param step
	 */
	public void save(LeaveStep step);
	
	/**
	 * 更新步骤
	 * @param step
	 */
	public void update(LeaveStep step);
	
	/**
	 * 删除步骤
	 * @param guid
	 */
	public void remove(String guid);
	
	/**
	 * 根据ID获取步骤
	 * @param guid
	 * @return
	 */
	public LeaveStep getById(String guid);
	
	/**
	 * 获取步骤集合
	 * @param query
	 * @return
	 */
	public List<LeaveStep> getList(LeaveStepQuery query);
	
	/**
	 * 获取分页数据
	 * @param query
	 * @return
	 */
	public PageList<LeaveStep> getPagingList(LeaveStepQuery query);
}
