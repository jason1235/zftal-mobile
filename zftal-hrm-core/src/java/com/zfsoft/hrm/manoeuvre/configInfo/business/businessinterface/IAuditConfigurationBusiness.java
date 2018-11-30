package com.zfsoft.hrm.manoeuvre.configInfo.business.businessinterface;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfiguration;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigurationQuery;

/**
 * 审核设置信息business接口
 * @author yongjun.fang
 *
 */
public interface IAuditConfigurationBusiness {

	/**
	 * 新增信息
	 * @param info
	 * @return
	 * @throws RuntimeException
	 */
	public boolean add(AuditConfiguration info) throws RuntimeException;
	
	/**
	 * 修改信息
	 * @param info
	 * @return
	 * @throws RuntimeException
	 */
	public boolean modify(AuditConfiguration info) throws RuntimeException;
	
	/**
	 * 删除信息
	 * @param aid
	 * @return
	 * @throws RuntimeException
	 */
	public boolean remove(String aid) throws RuntimeException;
	
	/**
	 * 删除指定环节下所有审核设置信息
	 * @param nid
	 * @return
	 * @throws RuntimeException
	 */
	public boolean removeWithTaskNode(String nid) throws RuntimeException;
	
	/**
	 * 查询信息列表
	 * @param query
	 * @return
	 * @throws RuntimeException
	 */
	public List<AuditConfiguration> getList(AuditConfigurationQuery query) throws RuntimeException;
	/**
	 * 根据ID查询信息列表
	 * @param query
	 * @return
	 * @throws RuntimeException
	 */
	public List<AuditConfiguration> getListById(String Nid) throws RuntimeException;
	
	/**
	 * 分页查询信息
	 * @param query
	 * @return
	 * @throws RuntimeException
	 */
	public PageList<AuditConfiguration> getPage(AuditConfigurationQuery query) throws RuntimeException;
	
	/**
	 * 根据id查询信息
	 * @param aid
	 * @return
	 * @throws RuntimeException
	 */
	public AuditConfiguration getInfoById(String aid) throws RuntimeException;

	/**
	 * 取得角色包含的人员
	 * @param param
	 * @return
	 */
	public List<String> findPersonByRole(AuditConfiguration info);

	/**
	 * 取得角色
	 * @return
	 */
	public List<Map<String, String>> getRoles();

	public String getBmdmByUser(AuditConfiguration info);
	
}
