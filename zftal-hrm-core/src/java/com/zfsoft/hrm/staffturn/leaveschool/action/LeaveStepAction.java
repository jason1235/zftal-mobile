package com.zfsoft.hrm.staffturn.leaveschool.action;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveStep;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveStepQuery;
import com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface.ILeaveStepService;

/** 
 * 离校环节action
 * @author jinjj
 * @date 2012-8-1 上午03:51:53 
 *  
 */
public class LeaveStepAction extends HrmAction {

	private static final long serialVersionUID = 1320700007399537687L;
	
	private ILeaveStepService leaveStepService;
	
	private PageList<LeaveStep> list;
	private LeaveStep step;
	private LeaveStepQuery query = new LeaveStepQuery();
	
	public String list() throws Exception{
		list = leaveStepService.getPagingList(query);
		return "list";
	}
	
	public String input() throws Exception{
		return "input";
	}

	public String save() throws Exception{
		leaveStepService.save(step);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String edit() throws Exception{
		step = leaveStepService.getById(step.getGuid());
		return "input";
	}
	
	public String update() throws Exception{
		leaveStepService.update(step);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public String remove() throws Exception{
		leaveStepService.remove(step.getGuid());
		getValueStack().set(DATA, getMessage());
		return DATA;
	}

	public LeaveStep getStep() {
		return step;
	}

	public void setStep(LeaveStep step) {
		this.step = step;
	}

	public List<LeaveStep> getList() {
		return list;
	}

	public void setLeaveStepService(ILeaveStepService leaveStepService) {
		this.leaveStepService = leaveStepService;
	}

	public LeaveStepQuery getQuery() {
		return query;
	}

	public void setQuery(LeaveStepQuery query) {
		this.query = query;
	}
	
}
