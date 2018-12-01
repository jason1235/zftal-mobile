package com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditStatus;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditStatusQuery;

public interface IAuditStatusBusiness {

	/**
	 * 新增审核状态信息
	 * @param info
	 * @return
	 * @throws RuntimeException
	 */
	public boolean add(AuditStatus info) throws RuntimeException;
	
	/**
	 * 修改审核状态信息
	 * @param info
	 * @return
	 * @throws RuntimeException
	 */
	public boolean modify(AuditStatus info) throws RuntimeException;
	
	/**
	 * 删除审核状态信息
	 * @param sid
	 * @return
	 * @throws RuntimeException
	 */
	public boolean remove(String sid) throws RuntimeException;
	
	/**
	 * 查询审核状态信息列表
	 * @param query
	 * @return
	 * @throws RuntimeException
	 */
	public List<AuditStatus> getList(AuditStatusQuery query) throws RuntimeException;
	
	/**
	 * 分页查询审核状态信息列表
	 * @param query
	 * @return
	 * @throws RuntimeException
	 */
	public PageList<AuditStatus> getPage(AuditStatusQuery query) throws RuntimeException;
	
	/**
	 * 根据Id查询审核状态信息
	 * @param sid
	 * @return
	 * @throws RuntimeException
	 */
	public AuditStatus getById(String sid) throws RuntimeException;
}
