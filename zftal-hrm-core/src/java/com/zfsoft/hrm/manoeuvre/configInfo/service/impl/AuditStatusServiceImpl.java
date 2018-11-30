package com.zfsoft.hrm.manoeuvre.configInfo.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface.IAuditStatusBusiness;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditStatus;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditStatusQuery;
import com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface.IAuditStatusService;
import com.zfsoft.hrm.manoeuvre.exception.ManoeuvreException;

public class AuditStatusServiceImpl implements IAuditStatusService {
	
	private IAuditStatusBusiness auditStatusBusiness;
	
	/**
	 * 设置business
	 * @param auditStatusBusiness
	 */
	public void setAuditStatusBusiness(IAuditStatusBusiness auditStatusBusiness) {
		this.auditStatusBusiness = auditStatusBusiness;
	}
	
	//------------------------------------------------------------------------------

	@Override
	public boolean add(AuditStatus info) throws ManoeuvreException {
		try{
			return auditStatusBusiness.add(info);
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("审核失败",e);
		}
	}
	
	@Override
	public boolean modify(AuditStatus info) throws ManoeuvreException {
		try{
			return auditStatusBusiness.modify(info);
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("修改审核状态信息失败",e);
		}
	}

	@Override
	public boolean remove(String sid) throws ManoeuvreException {
		try{
			return auditStatusBusiness.remove(sid);
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("删除审核状态信息失败",e);
		}
	}

	@Override
	public AuditStatus getById(String sid) throws ManoeuvreException {
		try{
			return auditStatusBusiness.getById(sid);
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("查找审核状态信息失败",e);
		}
	}

	@Override
	public List<AuditStatus> getList(AuditStatusQuery query)
			throws ManoeuvreException {
		try{
			return auditStatusBusiness.getList(query);
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("查询审核状态信息失败",e);
		}
	}

	@Override
	public PageList<AuditStatus> getPage(AuditStatusQuery query)
			throws ManoeuvreException {
		try{
			return auditStatusBusiness.getPage(query);
		}catch(IllegalArgumentException e){
			throw new ManoeuvreException("参数异常",e);
		}catch(RuntimeException e){
			throw new ManoeuvreException("查询审核状态信息失败",e);
		}
	}

	

}
