package com.zfsoft.hrm.baseinfo.audit.business;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditProcess;
import com.zfsoft.hrm.baseinfo.audit.query.AuditProcessQuery;

/** 
 * @author jinjj
 * @date 2012-10-9 下午03:49:06 
 *  
 */
public interface IAuditProcessBusiness {

	/**
	 * 新增
	 * @param process
	 */
	public void save(AuditProcess process);
	
	/**
	 * 更新步骤
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
	 * 分页查询
	 * @param query
	 * @return
	 */
	public PageList<AuditProcess> getPagingList(AuditProcessQuery query);
	
	/**
	 * 审核分页查询
	 * @param query
	 * @return
	 */
	public PageList<AuditProcess> getAuditPagingList(AuditProcessQuery query);
}
