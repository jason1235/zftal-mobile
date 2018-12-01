package com.zfsoft.hrm.baseinfo.org.dao.daointerface;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;

/**
 * 组织机构接口dao
 * @author yongjun.fang
 *
 */
public interface IOrgDao {

	/**
	 * 添加组织机构
	 * @param info
	 * @return
	 * @throws DataAccessException
	 */
	public int insert(OrgInfo info) throws DataAccessException;
	
	/**
	 * 修改组织机构
	 * @param info
	 * @return
	 * @throws DataAccessException
	 */
	public int update(OrgInfo info) throws DataAccessException;
	
	/**
	 * 废弃组织机构
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public int disuse(List<OrgInfo> list) throws DataAccessException;
	
	/**
	 * 启用组织机构
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public int use(List<OrgInfo> list) throws DataAccessException;
	
	/**
	 * 当一个部门信息变动时，若是部门类型发生变化，则调用本方法修改其全部子部门的部门类型
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int updateType(HashMap<String, Object> map) throws DataAccessException;
	
	/**
	 * 彻底删除组织机构
	 * @param oid
	 * @throws DataAccessException
	 */
	public void delete(String oid) throws DataAccessException;
	
	/**
	 * 查询组织机构列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrgInfo> findList(OrgQuery query) throws DataAccessException;
	
	public List<OrgInfo> findDisusedList(OrgQuery query) throws DataAccessException;
	
	/**
	 * 根据部门代码查询组织机构
	 * @param oid
	 * @return
	 * @throws DataAccessException
	 */
	public OrgInfo findById(String oid) throws DataAccessException;
	
	/**
	 * 分页查询组织机构列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrgInfo> findPage(OrgQuery query) throws DataAccessException;
	
	/**
	 * 分页计数
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public int findPageCount(OrgQuery query) throws DataAccessException;
	
	/**
	 * 判断一个部门是否被系统其它模块引用
	 * @param oid
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteJudge(String oid) throws DataAccessException;
	
	public int disusedJudge(String oid) throws DataAccessException;
	
	public List<OrgInfo> findOrgList(OrgQuery query);
	
}
