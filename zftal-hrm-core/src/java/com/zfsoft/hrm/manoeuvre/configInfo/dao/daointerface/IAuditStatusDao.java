package com.zfsoft.hrm.manoeuvre.configInfo.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditStatus;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditStatusQuery;


/**
 * 审核状态信息dao接口
 * @author yongjun.fang
 *
 */
public interface IAuditStatusDao {

	/**
	 * 新增审核状态信息
	 * @param info
	 * @return
	 * @throws DataAccessException
	 */
	public int insert(AuditStatus info) throws DataAccessException;
	
	/**
	 * 修改审核状态信息
	 * @param info
	 * @return
	 * @throws DataAccessException
	 */
	public int update(AuditStatus info) throws DataAccessException;
	
	/**
	 * 删除审核状态信息
	 * @param sid
	 * @return
	 * @throws DataAccessException
	 */
	public int delete(String sid) throws DataAccessException;
	
	/**
	 * 查询审核状态列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<AuditStatus> findList(AuditStatusQuery query) throws DataAccessException;
	
	/**
	 * 分页查询审核状态列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<AuditStatus> findPage(AuditStatusQuery query) throws DataAccessException;
	
	/**
	 * 根据ID查询审核状态信息
	 * @param sid
	 * @return
	 * @throws DataAccessException
	 */
	public AuditStatus findById(String sid) throws DataAccessException;
	
	/**
	 * 分页计数
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public int findPageCount(AuditStatusQuery query) throws DataAccessException;
}
