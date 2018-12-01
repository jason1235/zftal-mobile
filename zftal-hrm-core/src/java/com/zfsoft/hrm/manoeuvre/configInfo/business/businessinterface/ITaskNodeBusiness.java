package com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.TaskNode;
import com.zfsoft.hrm.manoeuvre.configInfo.query.TaskNodeQuery;

/**
 * 审核环节节点信息business接口
 * @author yongjun.fang
 *
 */
public interface ITaskNodeBusiness {
	/**
	 * 新增信息
	 * @param info
	 * @return
	 * @throws RuntimeException
	 * 
	 */
	public boolean add(TaskNode info) throws RuntimeException;
	
	/**
	 * 修改信息
	 * @param info
	 * @return
	 * @throws RuntimeException
	 */
	public boolean modify(TaskNode info) throws RuntimeException;
	
	/**
	 * 删除信息
	 * @param nid
	 * @throws RuntimeException
	 */
	public void remove(String nid) throws RuntimeException;
	
	/**
	 * 查询信息列表
	 * @param query
	 * @return
	 * @throws RuntimeException
	 */
	public List<TaskNode> getList(TaskNodeQuery query) throws RuntimeException;
	
	/**
	 * 分页查询信息
	 * @param query
	 * @return
	 * @throws RuntimeException
	 */
	public PageList<TaskNode> getPage(TaskNodeQuery query) throws RuntimeException;
	
	/**
	 * 根据id查询信息
	 * @param nid
	 * @return
	 * @throws RuntimeException
	 */
	public TaskNode getById(String nid) throws RuntimeException;
	
	/**
	 * 最终审核环节
	 * @return
	 * @throws RuntimeException
	 */
	public TaskNode getLastNode() throws RuntimeException;
	
	/**
	 * 最初审核环节
	 * @return
	 * @throws RuntimeException
	 */
	public TaskNode getFirstNode() throws RuntimeException;
	
	/**
	 * 指定顺序码order1所在数据以下到顺序码为order2的数据之前(不包括order2)的全部数据顺序码加一
	 * @param order1
	 * @param order2
	 * @return
	 * @throws RuntimeException
	 */
	public boolean plusOne(String order1, String order2) throws RuntimeException;
	
	/**
	 * 指定顺序码order1所在数据以下(不包括order1)到顺序码为order2的数据之前的全部数据顺序码减一
	 * @param order1
	 * @param order2
	 * @return
	 * @throws RuntimeException
	 */
	public boolean reduceOne(String order1, String order2) throws RuntimeException;
	
	/**
	 * 查找指定节点的下一节点,无法找到则返回null
	 * @param nid
	 * @return
	 * @throws RuntimeException
	 */
	public TaskNode getNext(String nid) throws RuntimeException;
	
	/**
	 * 查找指定节点的上一节点,无法找到则返回null
	 * @param nid
	 * @return
	 * @throws RuntimeException
	 */
	public TaskNode getPrev(String nid) throws RuntimeException;
	
}
