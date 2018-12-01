package com.zfsoft.hrm.authpost.post.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.authpost.post.entities.PostInfo;
/**
 * 
 * @author 沈鲁威 Patrick Shen
 * @since 2012-7-24
 * @version V1.0.0
 */
public interface IPostInfoDao {
	/**
	 * 
	 * @param authType
	 * @return
	 * @throws DataAccessException
	 */
	public List<PostInfo> findListByType(String typeCode) throws DataAccessException;
	/**
	 * 
	 * @param guid
	 * @return
	 * @throws DataAccessException
	 */
	public PostInfo findById(String guid) throws DataAccessException;
	/**
	 * 
	 * @param entity
	 * @throws DataAccessException
	 */
	public void insert(PostInfo entity) throws DataAccessException;
	/**
	 * 
	 * @param entity
	 * @throws DataAccessException
	 */
	public void update(PostInfo entity) throws DataAccessException;
	/**
	 * 
	 * @param guid
	 * @throws DataAccessException
	 */
	public void delete(String guid) throws DataAccessException;
}
