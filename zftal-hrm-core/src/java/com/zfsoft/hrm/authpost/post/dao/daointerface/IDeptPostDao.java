package com.zfsoft.hrm.authpost.post.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.authpost.post.entities.DeptPost;
import com.zfsoft.hrm.authpost.post.query.DeptPostQuery;

/** 
 * 部门岗位DAO
 * @author jinjj
 * @date 2012-7-24 上午11:27:13 
 *  
 */
public interface IDeptPostDao {

	/**
	 * 插入岗位
	 * @param post
	 * @throws DataAccessException
	 */
	public void insert(DeptPost post) throws DataAccessException;
	
	/**
	 * 更新岗位
	 * @param post
	 * @throws DataAccessException
	 */
	public void update(DeptPost post) throws DataAccessException;
	
	/**
	 * 获取岗位
	 * @param guid
	 * @throws DataAccessException
	 */
	public DeptPost getById(String guid) throws DataAccessException;
	
	/**
	 * 删除岗位
	 * @param guid
	 * @throws DataAccessException
	 */
	public void remove(String guid) throws DataAccessException;
	
	/**
	 * 获取岗位分页集合
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<DeptPost> getPagingList(DeptPostQuery query) throws DataAccessException;
	
	/**
	 * 获取岗位分页计数
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public int getPagingCount(DeptPostQuery query) throws DataAccessException;
	
	/**
	 * 获取岗位集合
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<DeptPost> getList(DeptPostQuery query) throws DataAccessException;
}
