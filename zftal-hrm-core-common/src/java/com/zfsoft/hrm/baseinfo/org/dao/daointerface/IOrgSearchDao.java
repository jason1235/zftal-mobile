package com.zfsoft.hrm.baseinfo.org.dao.daointerface;

import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;

public interface IOrgSearchDao {

	/**
	 * 获取指定部门下在职人员数
	 * @param oid
	 * @return
	 * @throws DataAccessException
	 */
	public int findExactCountByOrg(String oid) throws DataAccessException;
	
	/**
	 * 获取指定部门及其子部门下在职人员数
	 * @param oid
	 * @return
	 * @throws DataAccessException
	 */
	public int findStepCountByOrg(String oid) throws DataAccessException;
	
	/**
	 * 获得全体在职人员数
	 * @return
	 * @throws DataAccessException
	 */
	public int findPeopleCount() throws DataAccessException;
	
	/**
	 * 查询组织机构列表
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<OrgInfo> findOrgList(OrgQuery query) throws DataAccessException;
	
	public List<HashMap<String, String>> findCountMapByOrg() throws DataAccessException;
	
	/**
	 * 获取指定类型下的在职人员数量
	 * @param type
	 * @return 数量
	 * @throws DataAccessException
	 */
	public int findByType(String type) throws DataAccessException;
}
