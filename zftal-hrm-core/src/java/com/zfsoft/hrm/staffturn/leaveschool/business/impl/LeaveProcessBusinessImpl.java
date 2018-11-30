package com.zfsoft.hrm.staffturn.leaveschool.business.impl;

import java.util.Date;
import java.util.List;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.hrm.message.entity.Message;
import com.zfsoft.hrm.message.service.svcinterface.IMessageService;
import com.zfsoft.hrm.pendingAffair.business.IPendAffairJob;
import com.zfsoft.hrm.staffturn.leaveschool.business.ILeaveProcessBusiness;
import com.zfsoft.hrm.staffturn.leaveschool.dao.daointerface.ILeaveProcessDao;
import com.zfsoft.hrm.staffturn.leaveschool.dao.daointerface.ILeaveStepDao;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveProcess;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveStep;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveProcessQuery;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveStepQuery;
import com.zfsoft.util.base.StringUtil;

/** 
 * 离校处理business
 * @author jinjj
 * @date 2012-8-2 上午09:57:09 
 *  
 */
public class LeaveProcessBusinessImpl implements ILeaveProcessBusiness, IPendAffairJob {

	private ILeaveProcessDao leaveProcessDao;
	private ILeaveStepDao leaveStepDao;
	private IMessageService messageService;
	
	@Override
	public void save(LeaveProcess process) {
		leaveProcessDao.insert(process);

	}

	@Override
	public void updateStatus(LeaveProcess process) {
		LeaveStepQuery query = new LeaveStepQuery();
		List<LeaveStep> list = leaveStepDao.getList(query);
		boolean auth = false;
		User user = SessionFactory.getUser();
		for(LeaveStep step : list){
			String[] ghs = step.getHandlerList();
			for (String gh : ghs) {
				if(gh.equals(user.getYhm())){
					auth = true;
					break;
				}
			}
		}
		if(!auth){
			throw new RuleException("您不是环节处理负责人");
		}
		//process.setDeptId(user.getBmdm_id());
		process.setStatus("1");
		process.setOperator(user.getYhm());
		process.setOperateDate(new Date());
		leaveProcessDao.updateStatus(process);
		LeaveProcessQuery query2 = new LeaveProcessQuery();
		LeaveProcess leaveProcess = leaveProcessDao.getLeaveProcess(process.getGuid());
		query2.setAccountId(leaveProcess.getAccountId());
		query2.setStatus("0");
		List<LeaveProcess> leaveProcesses = leaveProcessDao.getList(query2);
		if(leaveProcesses != null && leaveProcesses.size() > 0){
			LeaveProcess nextStep = leaveProcesses.get(0);
			sendMessage("离校处理",nextStep.getOperator(),"离校人员:" + leaveProcess.getAccountId() + "等待处理！");
			sendMessage("离校处理",leaveProcess.getAccountId(),"您正在等待:" + nextStep.getOperator() + "处理离校事宜！");
		}
	}

	@Override
	public List<LeaveProcess> getList(LeaveProcessQuery query) {
		return leaveProcessDao.getList(query);
	}
	
	@Override
	public PageList<LeaveProcess> getPagingList(LeaveProcessQuery query) {
		//确定用户浏览权限，在环节中的用户允许浏览
		LeaveStepQuery stepQuery = new LeaveStepQuery();
		List<LeaveStep> list = leaveStepDao.getList(stepQuery);
		LeaveStep step = new LeaveStep();
		step.setDeptId("default");//默认，无数据
		User user = SessionFactory.getUser();
		for(LeaveStep s : list){
			String[] ghs = s.getHandlerList();
			for (String gh : ghs) {
				if(StringUtil.isNotEmpty(gh)&&gh.equals(user.getYhm())){
					step = s;
				}
			}
		}
		query.setProcessDept(step.getDeptId());
		
		PageList<LeaveProcess> pageList = new PageList<LeaveProcess>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(leaveProcessDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(leaveProcessDao.getPagingList(query));
			}
		}
		return pageList;
	}
	
	@Override
	public int getPendingAffairCount(String username){
		//确定用户浏览权限，在环节中的用户允许浏览
		LeaveStepQuery stepQuery = new LeaveStepQuery();
		List<LeaveStep> list = leaveStepDao.getList(stepQuery);
		LeaveStep step = new LeaveStep();
		step.setDeptId("default");//默认，无数据
//		User user = SessionFactory.getUser();
		for(LeaveStep s : list){
			String[] ghs = s.getHandlerList();
			for (String gh : ghs) {
				if(StringUtil.isNotEmpty(gh)&&gh.equals(username)){
					step = s;
				}
			}
		}
		LeaveProcessQuery query = new LeaveProcessQuery();
		query.setProcessDept(step.getDeptId());
		return leaveProcessDao.getPagingCount(query);
	}
	
	private void sendMessage(String title, String receiver, String content){
		//发送通过邮件给申报人
		Message message = new Message();
		message.setTitle(title);
		message.setSender("system");
		message.setReceiver(receiver);
		message.setContent(content);
		messageService.save(message);
	}
	
	@Override
	public void updateOperator(LeaveProcess process) {
		leaveProcessDao.updateOperator(process);
	}
	
	@Override
	public void delete(LeaveProcess process) {
		leaveProcessDao.delete(process);
	}
	
	public void setLeaveProcessDao(ILeaveProcessDao leaveProcessDao) {
		this.leaveProcessDao = leaveProcessDao;
	}

	public void setLeaveStepDao(ILeaveStepDao leaveStepDao) {
		this.leaveStepDao = leaveStepDao;
	}

	/**
	 * @return the messageService
	 */
	public IMessageService getMessageService() {
		return messageService;
	}

	/**
	 * @param messageService the messageService to set
	 */
	public void setMessageService(IMessageService messageService) {
		this.messageService = messageService;
	}

	@Override
	public LeaveProcess getLeaveProcess(String guid) {
		return leaveProcessDao.getLeaveProcess(guid);
	}

}
