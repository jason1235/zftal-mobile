package com.zfsoft.hrm.manoeuvre.configInfo.service.svcinterface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfiguration;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigurationQuery;
import com.zfsoft.hrm.manoeuvre.exception.ManoeuvreException;

/**
 * 审核设置信息service接口
 * @author yongjun.fang
 *
 */
public interface IAuditConfigurationService{

	/**
	 * 新增审核设置信息
	 * @param info
	 * @return
	 * @throws ManoeuvreException
	 */
	public boolean add(AuditConfiguration info) throws ManoeuvreException;
	
	/**
	 * 修改审核设置信息
	 * @param info
	 * @return
	 * @throws ManoeuvreException
	 */
	public boolean modify(AuditConfiguration info) throws ManoeuvreException;
	
	/**
	 * 删除审核设置信息
	 * @param aid
	 * @return
	 * @throws ManoeuvreException
	 */
	public void remove(String aid) throws ManoeuvreException;
	
	/**
	 * 查询审核设置信息列表
	 * @param query
	 * @return
	 * @throws ManoeuvreException
	 */
	public List<AuditConfiguration> getList(AuditConfigurationQuery query)
			throws ManoeuvreException;
	
	/**
	 * 分页查询审核设置信息
	 * @param query
	 * @return
	 * @throws ManoeuvreException
	 */
	public PageList<AuditConfiguration> getPage(AuditConfigurationQuery query)
			throws ManoeuvreException;
	
	/**
	 * 根据id查询审核设置信息
	 * @param aid
	 * @return
	 * @throws ManoeuvreException
	 */
	public AuditConfiguration getInfoById(String aid)
			throws ManoeuvreException;

	/**
	 * 取得角色包含的人员
	 * @param param
	 * @return
	 */
	public List<String> findPersonByRole(AuditConfiguration info);

	/**
	 * 取得角色列表
	 * @return
	 */
	public List<Map<String, String>> getRoles();
	
}
