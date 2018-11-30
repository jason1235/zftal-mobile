package com.zfsoft.hrm.staffturn.leaveschool.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.staffturn.leaveschool.business.ILeaveProcessBusiness;
import com.zfsoft.hrm.staffturn.leaveschool.business.ILeaveStepBusiness;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveProcess;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveStep;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveProcessQuery;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveStepQuery;
import com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface.ILeaveStepService;

/** 
 * 离校步骤service实现
 * @author jinjj
 * @date 2012-8-1 上午01:39:17 
 *  
 */
public class LeaveStepServiceImpl implements ILeaveStepService {

	private ILeaveStepBusiness leaveStepBusiness;
	private ILeaveProcessBusiness leaveProcessBusiness;
	
	@Override
	public void save(LeaveStep step) {
		leaveStepBusiness.save(step);
	}

	@Override
	public void update(LeaveStep step) {
		LeaveStep old = leaveStepBusiness.getById(step.getGuid());
		leaveStepBusiness.update(step);
		if(step.getHandler().equals(old.getHandler())){
			return;
		}
		LeaveProcessQuery query = new LeaveProcessQuery();
		query.setStatus("0");
		query.setProcessDept(step.getDeptId());
		List<LeaveProcess> list = leaveProcessBusiness.getList(query);
		for(LeaveProcess p : list){
			p.setOperator(step.getHandler());
			leaveProcessBusiness.updateOperator(p);
		}
	}

	@Override
	public void remove(String guid) {
		LeaveStep old = leaveStepBusiness.getById(guid);
		leaveStepBusiness.remove(guid);
		LeaveProcessQuery query = new LeaveProcessQuery();
		query.setStatus("0");
		query.setProcessDept(old.getDeptId());
		List<LeaveProcess> list = leaveProcessBusiness.getList(query);
		for(LeaveProcess p : list){
			leaveProcessBusiness.delete(p);
		}
	}

	@Override
	public LeaveStep getById(String guid) {
		return leaveStepBusiness.getById(guid);
	}

	@Override
	public List<LeaveStep> getList(LeaveStepQuery query) {
		return leaveStepBusiness.getList(new LeaveStepQuery());
	}
	
	@Override
	public PageList<LeaveStep> getPagingList(LeaveStepQuery query) {
		return leaveStepBusiness.getPagingList(query);
	}

	public void setLeaveStepBusiness(ILeaveStepBusiness leaveStepBusiness) {
		this.leaveStepBusiness = leaveStepBusiness;
	}

	public void setLeaveProcessBusiness(ILeaveProcessBusiness leaveProcessBusiness) {
		this.leaveProcessBusiness = leaveProcessBusiness;
	}

}
