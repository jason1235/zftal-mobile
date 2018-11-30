package com.zfsoft.hrm.authpost.post.dao.daointerface;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.authpost.post.entities.PostHistory;
import com.zfsoft.hrm.authpost.post.query.PostHistoryQuery;

/** 
 * 历史岗位DAO
 * @author jinjj
 * @date 2012-7-25 下午06:22:50 
 *  
 */
public interface IPostHistoryDao {

	/**
	 * 插入历史数据
	 * @param snapTime
	 * @throws DataAccessException
	 */
	public void insert(Date snapTime) throws DataAccessException;
	
	/**
	 * 删除历史数据
	 * @param snapTime
	 * @throws DataAccessException
	 */
	public void remove(Date snapTime) throws DataAccessException;
	
	/**
	 * 分页集合
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public List<PostHistory> getPagingList(PostHistoryQuery query) throws DataAccessException;
	/**
	 * 分页计数
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public int getPagingCount(PostHistoryQuery query) throws DataAccessException;
}
