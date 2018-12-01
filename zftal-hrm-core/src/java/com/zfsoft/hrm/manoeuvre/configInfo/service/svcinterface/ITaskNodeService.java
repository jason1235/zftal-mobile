package com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.TaskNode;
import com.zfsoft.hrm.manoeuvre.configInfo.query.TaskNodeQuery;

/**
 * 审核环节节点信息service接口
 * @author yongjun.fang
 *
 */
public interface ITaskNodeService {

	/**
	 * 新增审核环节节点信息
	 * @param info
	 * @return
	 */
	public boolean add(TaskNode info);
	
	/**
	 * 修改审核环节节点信息
	 * @param info
	 * @return
	 */
	public boolean modify(TaskNode info);
	
	/**
	 * 修改环节节点顺序
	 * @param info
	 */
	public void modifyOrder(TaskNode info);
	
	/**
	 * 删除审核环节节点信息
	 * @param nid
	 * @return
	 */
	public void remove(String nid);
	
	/**
	 * 查找审核环节节点信息列表
	 * @param query
	 * @return
	 */
	public List<TaskNode> getList(TaskNodeQuery query);
	
	/**
	 * 分页查找审核环节节点
	 * @param query
	 * @return
	 */
	public PageList<TaskNode> getPage(TaskNodeQuery query);
	
	/**
	 * 根据id查找审核环节节点信息
	 * @param nid
	 * @return
	 */
	public TaskNode getById(String nid);
	
	/**
	 * 交换编号为nid1和nid2两个节点的顺序码
	 * @param nid1
	 * @param nid2
	 */
	public void exchange(String nid1, String nid2);
	
}
