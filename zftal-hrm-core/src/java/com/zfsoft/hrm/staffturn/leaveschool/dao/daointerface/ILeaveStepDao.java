package com.zfsoft.hrm.staffturn.leaveschool.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveStep;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveStepQuery;

/** 
 * 离校步骤dao
 * @author jinjj
 * @date 2012-8-1 上午12:45:28 
 *  
 */
public interface ILeaveStepDao {

	/**
	 * 新增步骤
	 * @param step
	 * @throws DataAccessException
	 */
	public void insert(LeaveStep step) throws DataAccessException;
	
	/**
	 * 更新步骤
	 * @param step
	 * @throws DataAccessException
	 */
	public void update(LeaveStep step) throws DataAccessException;
	
	/**
	 * 删除步骤
	 * @param guid
	 * @throws DataAccessException
	 */
	public void remove(String guid) throws DataAccessException;
	
	/**
	 * 获取单个步骤
	 * @param guid
	 * @return
	 * @throws DataAccessException
	 */
	public LeaveStep getById(String guid) throws DataAccessException;
	
	/**
	 * 获取步骤集合
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<LeaveStep> getList(LeaveStepQuery query) throws DataAccessException;
	
	/**
	 * 获取步骤分页
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<LeaveStep> getPagingList(LeaveStepQuery query) throws DataAccessException;
	
	/**
	 * 获取步骤分页计数
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public int getPagingCount(LeaveStepQuery query) throws DataAccessException;
}
