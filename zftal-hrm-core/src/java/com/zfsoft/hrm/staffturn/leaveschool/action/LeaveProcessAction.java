package com.zfsoft.hrm.staffturn.leaveschool.action;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.common.HrmAction;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveProcess;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveProcessQuery;
import com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface.ILeaveProcessService;

/** 
 * 离校环节action
 * @author jinjj
 * @date 2012-8-1 上午03:51:53 
 *  
 */
public class LeaveProcessAction extends HrmAction {

	private static final long serialVersionUID = 1320700007399537687L;
	
	private ILeaveProcessService leaveProcessService;
	
	private PageList<LeaveProcess> list;
	private LeaveProcess process;
	private LeaveProcessQuery query = new LeaveProcessQuery();
	
	public String list() throws Exception{
		// 现使用部门做数据空值，待权限对接后转为操作用户名
//		User user = SessionFactory.getUser();
//		query.setProcessDept(user.getBmdm_id());
//		query.setStatus("0");
		list = leaveProcessService.getPagingList(query);
		return "list";
	}
	
	public String update() throws Exception{
		leaveProcessService.updateStatus(process);
		getValueStack().set(DATA, getMessage());
		return DATA;
	}
	
	public LeaveProcess getProcess() {
		return process;
	}

	public void setProcess(LeaveProcess process) {
		this.process = process;
	}

	public List<LeaveProcess> getList() {
		return list;
	}

	public void setLeaveProcessService(ILeaveProcessService leaveProcessService) {
		this.leaveProcessService = leaveProcessService;
	}

	public LeaveProcessQuery getQuery() {
		return query;
	}

	public void setQuery(LeaveProcessQuery query) {
		this.query = query;
	}

}
