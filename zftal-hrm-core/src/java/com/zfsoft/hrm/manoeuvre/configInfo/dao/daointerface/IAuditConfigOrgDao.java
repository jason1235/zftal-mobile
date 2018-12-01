package com.zfsoft.hrm.manoeuvre.configInfo.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.manoeuvre.configInfo.entities.AuditConfigOrgInfo;
import com.zfsoft.hrm.manoeuvre.configInfo.query.AuditConfigOrgQuery;

public interface IAuditConfigOrgDao {

	public int insert(AuditConfigOrgInfo info) throws DataAccessException;
	
	public int update(AuditConfigOrgInfo info) throws DataAccessException;
	
	public void delete(String aoid) throws DataAccessException;
	
	public List<AuditConfigOrgInfo> findList(AuditConfigOrgQuery query) throws DataAccessException;
	
	public List<AuditConfigOrgInfo> findPage(AuditConfigOrgQuery query) throws DataAccessException;
	
	public int findPageCount(AuditConfigOrgQuery query) throws DataAccessException;
	
	public AuditConfigOrgInfo findById(String aoid) throws DataAccessException;
	
	public void deleteByAid(String aid) throws DataAccessException;
}
