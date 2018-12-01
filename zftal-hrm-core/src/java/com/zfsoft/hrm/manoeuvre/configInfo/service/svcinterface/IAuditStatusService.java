package com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditStatus;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditStatusQuery;
import com.zfsoft.hrm.manoeuvre.exception.ManoeuvreException;

public interface IAuditStatusService {

	/**
	 * 新增审核状态信息
	 * @param info
	 * @return
	 * @throws ManoeuvreException
	 */
	public boolean add(AuditStatus info) throws ManoeuvreException;
	
	/**
	 * 修改审核状态信息
	 * @param info
	 * @return
	 * @throws ManoeuvreException
	 */
	public boolean modify(AuditStatus info) throws ManoeuvreException;
	
	/**
	 * 删除审核状态信息
	 * @param sid
	 * @return
	 * @throws ManoeuvreException
	 */
	public boolean remove(String sid) throws ManoeuvreException;
	
	/**
	 * 查询审核状态信息列表
	 * @param query
	 * @return
	 * @throws ManoeuvreException
	 */
	public List<AuditStatus> getList(AuditStatusQuery query) throws ManoeuvreException;
	
	/**
	 * 分页查询审核状态信息列表
	 * @param query
	 * @return
	 * @throws ManoeuvreException
	 */
	public PageList<AuditStatus> getPage(AuditStatusQuery query) throws ManoeuvreException;
	
	/**
	 * 根据ID查询审核状态信息
	 * @param sid
	 * @return
	 * @throws ManoeuvreException
	 */
	public AuditStatus getById(String sid) throws ManoeuvreException;
	
}
