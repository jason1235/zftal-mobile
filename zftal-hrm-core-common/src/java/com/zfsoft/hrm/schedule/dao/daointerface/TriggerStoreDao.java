package com.zfsoft.hrm.schedule.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.schedule.entities.TriggerStore;


public interface TriggerStoreDao {
	/**
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public List<TriggerStore> findList() throws DataAccessException;
	/**
	 * 
	 * @return
	 * @throws DataAccessException
	 */
	public TriggerStore findById() throws DataAccessException;
	/**
	 * 
	 * @param triggerStore
	 * @throws DataAccessException
	 */
	public void insert(TriggerStore triggerStore) throws DataAccessException;
	/**
	 * 
	 * @param id
	 * @throws DataAccessException
	 */
	public void delete(String id) throws DataAccessException;
	/**
	 * 
	 * @param triggerStore
	 * @throws DataAccessException
	 */
	public void update(TriggerStore triggerStore) throws DataAccessException;
}
