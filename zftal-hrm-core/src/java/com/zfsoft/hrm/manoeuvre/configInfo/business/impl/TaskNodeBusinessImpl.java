package com.zfsoft.hrm.manoeuvre.configInfo.business.impl;

import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.ITaskNodeBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.dao.daointerface.ITaskNodeDao;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.TaskNode;
import com.zfsoft.hrm.manoeuvre.configInfo.query.TaskNodeQuery;
import com.zfsoft.util.base.StringUtil;

/**
 * 审核环节节点信息business实现类
 * @author yongjun.fang
 *
 */
public class TaskNodeBusinessImpl implements ITaskNodeBusiness {

	private ITaskNodeDao taskNodeDao;
	
	/**
	 * 设置dao
	 * @param taskNodeDao
	 */
	public void setTaskNodeDao(ITaskNodeDao taskNodeDao) {
		this.taskNodeDao = taskNodeDao;
	}

	@Override
	public boolean add(TaskNode info) throws RuntimeException {
		return taskNodeDao.insert(info) > 0 ? true : false;
	}
	
	@Override
	public boolean modify(TaskNode info) throws RuntimeException {
		return taskNodeDao.update(info) > 0 ? true : false;
	}
	
	@Override
	public void remove(String nid) throws RuntimeException {
		taskNodeDao.delete(nid);
	}
	
	@Override
	public List<TaskNode> getList(TaskNodeQuery query) throws RuntimeException {
		return taskNodeDao.findList(query);
	}
	
	@Override
	public PageList<TaskNode> getPage(TaskNodeQuery query)
			throws RuntimeException {
		PageList<TaskNode> pageList = new PageList<TaskNode>();
		if(query == null){
			return pageList;
		}
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(query.getPerPageSize());
		paginator.setPage((Integer)query.getToPage());
		paginator.setItems(taskNodeDao.findPageCount(query));
		pageList.setPaginator(paginator);
		if(paginator.getBeginIndex() <= paginator.getItems()){
			query.setStartRow(paginator.getBeginIndex());
			query.setEndRow(paginator.getEndIndex());
			pageList.addAll(taskNodeDao.findPage(query));
		}
		return pageList;
	}

	@Override
	public TaskNode getById(String nid) throws RuntimeException {
		return taskNodeDao.findById(nid);
	}

	@Override
	public TaskNode getFirstNode() throws RuntimeException {
		return taskNodeDao.findFirstNode();
	}

	@Override
	public TaskNode getLastNode() throws RuntimeException {
		return taskNodeDao.findLastNode();
	}

	@Override
	public TaskNode getNext(String nid) throws RuntimeException {
		List<TaskNode> list = taskNodeDao.findNext(nid);
		if(list == null || list.size() == 0){
			return null;
		}
		Assert.isTrue(list.size() == 1, "跳转下一环节出错,存在多个下一环节");
		return list.get(0);
	}

	@Override
	public TaskNode getPrev(String nid) throws RuntimeException {
		List<TaskNode> list = taskNodeDao.findPrev(nid);
		if(list == null || list.size() == 0){
			return null;
		}
		Assert.isTrue(list.size() == 1, "跳转上一环节出错,存在多个上一环节");
		return list.get(0);
	}

	@Override
	public boolean plusOne(String order1, String order2)
			throws RuntimeException {
		if(StringUtil.isEmpty(order2)){
			taskNodeDao.plusOne(Long.parseLong(order1));
			return true;
		}
		return taskNodeDao.partPlusOne(Long.parseLong(order1), Long.parseLong(order2)) > 0 ? true : false;
	}

	@Override
	public boolean reduceOne(String order1, String order2)
			throws RuntimeException {
		if(StringUtil.isEmpty(order2)){
			taskNodeDao.reduceOne(Long.parseLong(order1));
			return true;
		}
		return taskNodeDao.partReduceOne(Long.parseLong(order1), Long.parseLong(order2)) > 0 ? true : false;
	}
	
}