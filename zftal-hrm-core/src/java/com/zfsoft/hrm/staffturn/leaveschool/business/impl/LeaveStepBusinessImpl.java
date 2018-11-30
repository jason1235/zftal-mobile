package com.zfsoft.hrm.staffturn.leaveschool.business.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.staffturn.leaveschool.business.ILeaveStepBusiness;
import com.zfsoft.hrm.staffturn.leaveschool.dao.daointerface.ILeaveStepDao;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveStep;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveStepQuery;

/** 
 * 离校步骤business实现
 * @author jinjj
 * @date 2012-8-1 上午01:15:43 
 *  
 */
public class LeaveStepBusinessImpl implements ILeaveStepBusiness {

	private ILeaveStepDao leaveStepDao;
	
	@Override
	public void save(LeaveStep step) {
		LeaveStepQuery query = new LeaveStepQuery();
		List<LeaveStep> list = leaveStepDao.getList(query);
		for (LeaveStep leaveStep : list) {
			if(leaveStep.getDeptId().equals(step.getDeptId())){
				throw new RuleException("该部门环节已添加");
			}
			String[] gh=step.getHandlerList();
			String[] stepGh=leaveStep.getHandlerList();
			for (String g1 : gh) {
				for (String g2 : stepGh) {
					if(g1!=null&&g1.equals(g2)){
						throw new RuleException("工号为["+g1+"]的处理人员已存在");
					}
				}
			}
		}
		leaveStepDao.insert(step);
	}

	@Override
	public void update(LeaveStep step) {
		LeaveStepQuery query = new LeaveStepQuery();
		query.setGuid(step.getGuid());
		List<LeaveStep> list = leaveStepDao.getList(query);
		for (LeaveStep leaveStep : list) {
			String[] gh=step.getHandlerList();
			String[] stepGh=leaveStep.getHandlerList();
			for (String g1 : gh) {
				for (String g2 : stepGh) {
					if(g1!=null&&g1.equals(g2)){
						throw new RuleException("工号为["+g1+"]的处理人员已存在");
					}
				}
			}
		}
		leaveStepDao.update(step);
	}

	@Override
	public void remove(String guid) {
		leaveStepDao.remove(guid);
	}

	@Override
	public LeaveStep getById(String guid) {
		LeaveStep step = leaveStepDao.getById(guid);
		return step;
	}

	@Override
	public List<LeaveStep> getList(LeaveStepQuery query) {
		List<LeaveStep> list = leaveStepDao.getList(query);
		return list;
	}
	
	@Override
	public PageList<LeaveStep> getPagingList(LeaveStepQuery query){
		PageList<LeaveStep> pageList = new PageList<LeaveStep>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(leaveStepDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(leaveStepDao.getPagingList(query));
			}
		}
		return pageList;
	}

	public void setLeaveStepDao(ILeaveStepDao leaveStepDao) {
		this.leaveStepDao = leaveStepDao;
	}

}
