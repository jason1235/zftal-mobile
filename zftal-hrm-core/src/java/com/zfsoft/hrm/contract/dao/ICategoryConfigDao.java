package com.zfsoft.hrm.contract.dao;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.contract.entity.CategoryConfig;

public interface ICategoryConfigDao {
	public PageList<CategoryConfig> getPagedList(CategoryConfig categoryConfig) throws DataAccessException;
	
	public void delete(String id) throws DataAccessException;
	
	public void insert(CategoryConfig categoryConfig) throws DataAccessException;
	
	public void update(CategoryConfig categoryConfig) throws DataAccessException;
	
	public CategoryConfig getById(String id) throws DataAccessException;
	
	public int checkDm(String id) throws DataAccessException;
	
	public String getXm(String gh) throws DataAccessException;
}
