package com.zfsoft.hrm.manoeuvre.configInfo.dao.daointerface;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfiguration;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigurationQuery;


/**
 * 审核设置信息dao
 * @author yongjun.fang
 *
 */
public interface IAuditConfigurationDao {

	/**
	 * 添加审核设置信息
	 * @param info
	 * @return
	 * @throws DataAccessException
	 */
	public int insert(AuditConfiguration info) throws DataAccessException;
	
	/**
	 * 修改审核设置信息
	 * @param info
	 * @return
	 * @throws DataAccessException
	 */
	public int update(AuditConfiguration info) throws DataAccessException;
	
	/**
	 * 删除审核设置信息
	 * @param aid
	 * @return
	 * @throws DataAccessException
	 */
	public int delete(String aid) throws DataAccessException;
	
	/**
	 * 删除一个环节下的所有审核设置
	 * @param nid
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteWithTaskNode(String nid) throws DataAccessException;
	
	/**
	 * 查询审核设置信息列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<AuditConfiguration> findAuditConfigurationList(AuditConfigurationQuery query) throws DataAccessException;
	
	/**
	 * 分页查询审核设置信息列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<AuditConfiguration> findAuditConfigurationPage(AuditConfigurationQuery query) throws DataAccessException;
	
	/**
	 * 根据id查找审核设置信息
	 * @param aid
	 * @return
	 * @throws DataAccessException
	 */
	public AuditConfiguration findAuditConfigurationById(String aid) throws DataAccessException;
	
	/**
	 * 分页计数
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public int findAuditConfigurationPageCount(AuditConfigurationQuery query) throws DataAccessException;

	/**
	 * 取得角色包含的人员
	 * @param param
	 * @return
	 */
	public List<String> findPersonByRole(AuditConfiguration info);

	/**
	 * 是否存在
	 * @param info
	 * @return
	 */
	public int isExist(AuditConfiguration info);
	
	/**
	 * 根据Id查询审核设置信息列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<AuditConfiguration> findAuditConfigurationListById(String nid);

	/**
	 * 取得角色
	 * @return
	 */
	public List<Map<String, String>> getRoles();

	public String getBmdmByUser(AuditConfiguration info);
	
}
