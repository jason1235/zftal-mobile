package com.zfsoft.hrm.baseinfo.search.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.query.BaseQuery;
import com.zfsoft.hrm.baseinfo.search.entities.Condition;

/**
 * {@link Condition }数据库操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-13
 * @version V1.0.0
 */
public interface IConditionDao {
	
	/**
	 * 查询列表 
	 * @param query 查询条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public List<Condition> findList(BaseQuery query) throws DataAccessException;
	
	/**
	 * 查询单条记录
	 * @param guid 全局ID
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public Condition findById(String guid) throws DataAccessException;
	
	/**
	 * 查询记录数
	 * @param query 查询条件
	 * @return 符合条件的记录数量
	 * @throws DataAccessException 如果操作出现异常
	 */
	public int count(BaseQuery query) throws DataAccessException;
	
	/**
	 * 增加
	 * @param bean 增加的实体
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void insert(Condition bean) throws DataAccessException;
	
	/**
	 * 增加,人工指定guid
	 * @param bean 增加的实体
	 */
	public void insertWithGuid(Condition bean);
	
	/**
	 * 修改
	 * @param bean 修改的实体
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void update(Condition bean) throws DataAccessException;
	
	/**
	 * 删除
	 * @param guid 全局ID
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void delete(String guid) throws DataAccessException;
	/**
	 * 分页查询列表 
	 * @param query 查询条件
	 * @return
	 * @throws DataAccessException 如果操作出现异常
	 */
	public List<Condition>findPagingInfoList(BaseQuery query)throws DataAccessException;
	
	
	/**
	 * 查询记录数
	 * @param query 查询条件
	 * @return 符合条件的记录数量
	 * @throws DataAccessException 如果操作出现异常
	 */
	public int findPagingCount(BaseQuery query) throws DataAccessException;
}
