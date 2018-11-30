package com.zfsoft.hrm.manoeuvre.configInfo.service.impl;

import java.util.List;

import org.springframework.util.Assert;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.IAuditConfigOrgBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.IAuditConfigurationBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.ITaskNodeBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfiguration;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.TaskNode;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigurationQuery;
import com.zfsoft.hrm.manoeuvre.configInfo.query.TaskNodeQuery;
import com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface.ITaskNodeService;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.business.businessinterface.IManoeuvreBusiness;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.common.spring.SpringHolder;

public class TaskNodeServiceImpl implements ITaskNodeService {
	
	private ITaskNodeBusiness taskNodeBusiness;
	
	private IAuditConfigurationBusiness auditConfigurationBusiness;
	
	private IAuditConfigOrgBusiness auditConfigOrgBusiness;
	
	public void setAuditConfigurationBusiness(
			IAuditConfigurationBusiness auditConfigurationBusiness) {
		this.auditConfigurationBusiness = auditConfigurationBusiness;
	}
	
	public void setTaskNodeBusiness(ITaskNodeBusiness taskNodeBusiness) {
		this.taskNodeBusiness = taskNodeBusiness;
	}
	
	public void setAuditConfigOrgBusiness(
			IAuditConfigOrgBusiness auditConfigOrgBusiness) {
		this.auditConfigOrgBusiness = auditConfigOrgBusiness;
	}

	@Override
	public boolean add(TaskNode info) {
		TaskNode lastNode = taskNodeBusiness.getLastNode();
		if(lastNode == null || StringUtil.isEmpty(lastNode.getOrder())){
			info.setOrder("1");
		}
		else{
			info.setOrder(String.valueOf(Long.parseLong(lastNode.getOrder()) + 1));
		}
		return taskNodeBusiness.add(info);
	}
	
	@Override
	public boolean modify(TaskNode info) {
		return taskNodeBusiness.modify(info);
	}

	@Override
	public void modifyOrder(TaskNode info) {
		TaskNode node = taskNodeBusiness.getById(info.getNid());
		if(node.getOrder().equals(info.getOrder())){
			return;
		}
		TaskNode lastNode = taskNodeBusiness.getLastNode();
		if(Long.parseLong(info.getOrder()) > Long.parseLong(lastNode.getOrder())){
			info.setOrder(lastNode.getOrder());
		}
		if(orderExist(info)){
			if(Long.parseLong(node.getOrder()) > Long.parseLong(info.getOrder())){
				Assert.isTrue(taskNodeBusiness.plusOne(info.getOrder(), node.getOrder()), "修改失败，顺序码未能成功插入");
			}
			else{
				Assert.isTrue(taskNodeBusiness.reduceOne(node.getOrder(), info.getOrder()), "修改失败，顺序码未能成功插入");
			}
		}
		else{
			if(Long.parseLong(node.getOrder()) > Long.parseLong(info.getOrder())){
				Assert.isTrue(taskNodeBusiness.reduceOne(node.getOrder(), null), "修改失败，顺序码未能成功插入");
			}
			else{
				Assert.isTrue(taskNodeBusiness.reduceOne(node.getOrder(), info.getOrder()), "修改失败，顺序码未能成功插入");
				info.setOrder(String.valueOf(Long.parseLong(info.getOrder()) - 1));
			}
		}
		Assert.isTrue(taskNodeBusiness.modify(info), "修改环节失败");
	}

	@Override
	public void remove(String nid) {
		Assert.isTrue(!nodeBeUsed(nid), "该环节存在待审核信息,不可删除");
		TaskNode node = taskNodeBusiness.getById(nid);
		Assert.notNull(node, "未找到指定环节节点新信息");
		Assert.isTrue(taskNodeBusiness.reduceOne(node.getOrder(), null), "修改失败，顺序码未能成功变更");
		removeChildrenSettings(node);
		taskNodeBusiness.remove(nid);
	}

	@Override
	public void exchange(String nid1, String nid2) {
		TaskNode node1 = taskNodeBusiness.getById(nid1);
		TaskNode node2 = taskNodeBusiness.getById(nid2);
		Assert.isTrue(node1 != null && node2 != null, "未找到指定节点信息");
		String temp = node1.getOrder();
		node1.setOrder(node2.getOrder());
		node2.setOrder(temp);
		taskNodeBusiness.modify(node1);
		taskNodeBusiness.modify(node2);
	}

	@Override
	public TaskNode getById(String nid) {
		return taskNodeBusiness.getById(nid);
	}

	@Override
	public List<TaskNode> getList(TaskNodeQuery query) {
		return taskNodeBusiness.getList(query);
	}

	@Override
	public PageList<TaskNode> getPage(TaskNodeQuery query) {
		return taskNodeBusiness.getPage(query);
	}
	
	
	/**
	 * 判断信息中的顺序码是否已被使用
	 * @param info
	 * @return
	 */
	private boolean orderExist(TaskNode info){
		TaskNodeQuery query = new TaskNodeQuery();
		query.setOrder(info.getOrder());
		List<TaskNode> list = taskNodeBusiness.getList(query);
		if(list == null || list.size() == 0){
			return false;
		}
		return true;
	}
	
	/**
	 * 判断指定节点是否被信息引用
	 * @param nid
	 * @return
	 */
	private boolean nodeBeUsed(String nid){
		IManoeuvreBusiness business = (IManoeuvreBusiness)SpringHolder.getBean("manoeuvreBusiness");
		if(business.existByNode(nid)){
			return true;
		}
		return false;
	}
	
	/**
	 * 删除一个节点时同时删除其下的子设置
	 * @param node
	 */
	private void removeChildrenSettings(TaskNode node){
		AuditConfigurationQuery aciQuery = new AuditConfigurationQuery();
		aciQuery.setTaskNode(node);
		List<AuditConfiguration> auditConfigInfoList = auditConfigurationBusiness.getList(aciQuery);
		if(auditConfigInfoList != null && auditConfigInfoList.size() > 0){
			for (AuditConfiguration auditConfigInfo : auditConfigInfoList) {
				auditConfigOrgBusiness.removeByAid(auditConfigInfo.getAid());
			}
			auditConfigurationBusiness.removeWithTaskNode(node.getNid());
		}
	}
	
}
