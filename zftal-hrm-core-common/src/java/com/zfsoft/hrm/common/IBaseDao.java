package com.zfsoft.hrm.common;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.dao.query.BaseQuery;

/**
 * 实体基础数据操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-15
 * @version V1.0.0
 */
public interface IBaseDao<T> {
	
	/**
	 * 查询符合条件的列表数据
	 * @param query 查询条件
	 * @return 符合条件的数据列表
	 * @throws DataAccessException (runtime)如果操作出现异常
	 */
	public List<T> findList(BaseQuery query) throws DataAccessException;
	
	/**
	 * 统计查询符合条件的列表数据记录数据
	 * @param query 查询条件
	 * @return 符合条件的数据数
	 * @throws DataAccessException (runtime)如果操作出现异常
	 */
	public int count(BaseQuery query) throws DataAccessException;
	
	/**
	 * 查询指定主键的记录
	 * @param guid 主键（全局ID）
	 * @return 对应的记录
	 * @throws DataAccessException (runtime)如果操作出现异常
	 */
	public T findById(Object guid) throws DataAccessException;
	
	/**
	 * 插入实体数据
	 * @param entity 插入的实体数据
	 * @return 插入的记录数数量
	 * @throws DataAccessException (runtime)如果操作出现异常
	 */
	public int insert(T entity) throws DataAccessException;
	
	/**
	 * 更新实体数据
	 * @param entity 更新的实体数据
	 * @return 更新的记录数数量
	 * @throws DataAccessException (runtime)如果操作出现异常
	 */
	public int update(T entity) throws DataAccessException;
	
	/**
	 * 删除实体数据
	 * @param guid 删除的实体数据全局ID
	 * @return 删除的记录数数量
	 * @throws DataAccessException (runtime)如果操作出现异常
	 */
	public int delete(Object guid) throws DataAccessException;
	
}
