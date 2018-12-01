package com.zfsoft.hrm.baseinfo.table.dao.daointerface;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.table.entities.Table;

/**
 * 数据库表操作DAO
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-6
 * @version V1.0.0
 */
public interface ITableDao {
	
	/**
	 * 创建表结构
	 * @param table 表结构信息
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void create(Table table) throws DataAccessException;
	
	/**
	 * 删除表结构
	 * @param table 表实体
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void drop(Table table) throws DataAccessException;
	
	/**
	 * 查询指定名称的数量
	 * @param name 表名称
	 * @return
	 * @throws DataAccessException
	 */
	public int findCount(String name) throws DataAccessException;

	/**
	 * 对表注释
	 * @param table
	 * @throws DataAccessException
	 */
	public void comment(Table table) throws DataAccessException;
}
