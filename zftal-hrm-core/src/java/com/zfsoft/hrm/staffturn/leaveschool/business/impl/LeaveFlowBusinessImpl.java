package com.zfsoft.hrm.staffturn.leaveschool.business.impl;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.staffturn.leaveschool.business.ILeaveFlowBusiness;
import com.zfsoft.hrm.staffturn.leaveschool.dao.daointerface.ILeaveFlowDao;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveFlowInfo;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveFlowInfoQuery;

/** 
 * 离校流程管理business
 * @author jinjj
 * @date 2012-8-2 上午07:35:20 
 *  
 */
public class LeaveFlowBusinessImpl implements ILeaveFlowBusiness {

	private ILeaveFlowDao leaveFlowDao;
	
	@Override
	public void save(LeaveFlowInfo info) {
		LeaveFlowInfo obj = leaveFlowDao.getById(info.getAccountId());
		if(obj != null){
			throw new RuleException("该人员已在离校流程中");
		}
		leaveFlowDao.insert(info);
	}

	@Override
	public void update(LeaveFlowInfo info) {
		leaveFlowDao.update(info);
	}

	@Override
	public List<LeaveFlowInfo> getList(LeaveFlowInfoQuery query) {
		return leaveFlowDao.getList(query);
	}

	@Override
	public LeaveFlowInfo getById(String accountId) {
		return leaveFlowDao.getById(accountId);
	}
	
	@Override
	public PageList<LeaveFlowInfo> getPagingList(LeaveFlowInfoQuery query) {
		PageList<LeaveFlowInfo> pageList = new PageList<LeaveFlowInfo>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(leaveFlowDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			System.out.println(query.getLeaveDateStart());
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(leaveFlowDao.getPagingList(query));
			}
		}
		return pageList;
	}

	public void setLeaveFlowDao(ILeaveFlowDao leaveFlowDao) {
		this.leaveFlowDao = leaveFlowDao;
	}

}
