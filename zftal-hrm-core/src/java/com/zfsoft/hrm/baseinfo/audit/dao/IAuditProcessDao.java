package com.zfsoft.hrm.baseinfo.audit.dao;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditProcess;
import com.zfsoft.hrm.baseinfo.audit.query.AuditProcessQuery;

/** 
 * 审核流程DAO
 * @author jinjj
 * @date 2012-10-9 下午02:50:20 
 *  
 */
public interface IAuditProcessDao {

	/**
	 * 插入
	 * @param process
	 */
	public void insert(AuditProcess process);
	
	/**
	 * 更新
	 * @param process
	 */
	public void update(AuditProcess process);
	
	/**
	 * 删除
	 * @param guid
	 */
	public void delete(String guid);
	
	/**
	 * 查询
	 * @param guid
	 * @return
	 */
	public AuditProcess getById(String guid);
	
	/**
	 * 查询列表
	 * @param query
	 * @return
	 */
	public List<AuditProcess> getList(AuditProcessQuery query);
	
	/**
	 * 教职工申请分页查询
	 * @param query
	 * @return
	 */
	public PageList<AuditProcess> getPagingList(AuditProcessQuery query);
	
	/**
	 * 教职工申请分页计数
	 * @param query
	 * @return
	 */
	public int getPagingCount(AuditProcessQuery query);
	
	/**
	 * 审核分页查询
	 * @param query
	 * @return
	 */
	public PageList<AuditProcess> getAuditPagingList(AuditProcessQuery query);
	
	/**
	 * 审核分页计数
	 * @param query
	 * @return
	 */
	public int getAuditPagingCount(AuditProcessQuery query);
}
