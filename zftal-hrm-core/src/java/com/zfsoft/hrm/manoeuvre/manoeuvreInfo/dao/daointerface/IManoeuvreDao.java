package com.zfsoft.hrm.manoeuvre.manoeuvreInfo.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.entities.ManoeuvreInfo;
import com.zfsoft.hrm.manoeuvre.manoeuvreInfo.query.ManoeuvreQuery;


/**
 * 人员调配信息dao接口
 * @author yongjun.fang
 *
 */
public interface IManoeuvreDao {
	/**
	 * 查询所有的人员调配信息
	 * @param query
	 * @return 人员调配列表
	 * @throws DataAccessException
	 */
	public List<ManoeuvreInfo> findList(ManoeuvreQuery query) throws DataAccessException;
	
	/**
	 * 根据id查询对应的人员调配信息
	 * @param guId 唯一标识
	 * @return 对应的人员调配信息
	 * @throws DataAccessException
	 */
	public ManoeuvreInfo findById(String guId) throws DataAccessException;
	
	/**
	 * 增加人员调配信息
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public int insert(ManoeuvreInfo entity) throws DataAccessException;
	/**
	 * 保存人员调配信息（保存不执行）
	 * @param entity
	 * @return
	 * @throws DataAccessException
	 */
	public int save(ManoeuvreInfo entity) throws DataAccessException;
	
	/**
	 * 修改人员调配信息
	 * @param entity 人员调配信息
	 * @return
	 * @throws DataAccessException
	 */
	public int update(ManoeuvreInfo entity) throws DataAccessException;
	
	/**
	 * 删除人员调配信息
	 * @param guId 唯一标识
	 * @throws DataAccessException
	 */
	public void delete(String guId) throws DataAccessException;
	
	/**
	 * 修改进修信息当前环节
	 * @param entity 人员调配信息
	 * @return
	 * @throws DataAccessException
	 */
	public int updateTask(ManoeuvreInfo entity) throws DataAccessException;
	
	/**
	 * 获取分页人员调配列表
	 * @param query
	 * @return 分页人员调配列表
	 * @throws DataAccessException
	 */
	public List<ManoeuvreInfo> findPagingList(ManoeuvreQuery query) throws DataAccessException;
	
	/**
	 * 获取分页人员调配计数
	 * @param query
	 * @return 分页人员调配计数
	 * @throws DataAccessException
	 */
	public int findPagingCount(ManoeuvreQuery query) throws DataAccessException;
	
	public int updateForNodeChange(String nid1, String nid2) throws DataAccessException;
	
	public int existByNode(String nid) throws DataAccessException;
}
