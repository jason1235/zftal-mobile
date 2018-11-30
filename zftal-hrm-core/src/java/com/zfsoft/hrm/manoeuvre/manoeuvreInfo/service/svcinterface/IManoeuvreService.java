package com.zfsoft.hrm.manoeuvre.manoeuvreInfo.service.svcinterface;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfiguration;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditStatus;
import com.zfsoft.hrm.manoeuvre.exception.ManoeuvreException;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.entities.ManoeuvreInfo;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.query.ManoeuvreQuery;

public interface IManoeuvreService {

	/**
	 * 获取人员调配信息列表
	 * @param query
	 * @return
	 */
	public List<ManoeuvreInfo> getList(ManoeuvreQuery query);
	
	/**
	 * 分页查询人员调配信息
	 * @param query
	 * @return
	 */
	public PageList<ManoeuvreInfo> getPageList(ManoeuvreQuery query);
	
	/**
	 * 根据编号获取人员调配信息
	 * @param id
	 * @return
	 */
	public ManoeuvreInfo getById(String id);
	
	/**
	 * 新增人员调配信息
	 * @param info
	 */
	public boolean add(ManoeuvreInfo info);
	/**
	 * 保存人员调配信息(保存不执行)
	 * @param info
	 */
	public boolean save(ManoeuvreInfo info);
	
	/**
	 * 修改人员调配信息
	 * @param info
	 */
	public boolean modify(ManoeuvreInfo info);
	
	/**
	 * 删除人员调配信息
	 * @param id
	 */
	public void remove(String id);
	
	/**
	 * 修改人员调配信息当前环节
	 * @param info
	 */
	public boolean modifyCurrentTask(ManoeuvreInfo info);
	
	/**
	 * 审核方法
	 * @param info 
	 * @param auditStatus 审核状态信息(此信息中需已包含审核意见、审核结果、备注)
	 * @throws ManoeuvreException;

	 */
	public void doAudit(ManoeuvreInfo info, AuditStatus auditStatusInfo) throws ManoeuvreException;
	
	/**
	 * 返回当前审核人的审核设置列表
	 * @param staffid
	 * @return
	 * @throws ManoeuvreException
	 */
	public List<AuditConfiguration> getAuditConfigurationList(String staffid) throws ManoeuvreException;
	
	
}
