package com.zfsoft.hrm.baseinfo.table.dao.daointerface;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.table.entities.Column;
import com.zfsoft.hrm.baseinfo.table.exception.TableException;

/**
 * 表结构字段信息操作DAO
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-6
 * @version V1.0.0
 * 
 */
public interface IColumnDao {
	/**
	 * 增加字段信息
	 * @param column 字段信息
	 * @throws TableException 如果操作出现异常
	 */
	public void add(Column column) throws DataAccessException;
	
	/**
	 * 重命名字段名字
	 * @param column 字段信息
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void rename(Column column) throws DataAccessException;
	
	/**
	 * 修改字段
	 * @param column 修改的字段信息
	 * @throws DataAccessException 修改字段信息
	 */
	public void modify(Column column) throws DataAccessException;
	
	/**
	 * 删除字段
	 * @param column 删除的字段信息
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void drop(Column column) throws DataAccessException;
	
	/**
	 * 注释字段
	 * @param column 字段
	 * @throws DataAccessException
	 */
	public void comment(Column column) throws DataAccessException;
	
	/**
	 * 根据字段名称查询目标表中某字段个数
	 * @param column
	 * @return
	 * @throws DataAccessException
	 */
	public int findCount(Column column) throws DataAccessException;
	/**
	 * 增加某字段的索引
	 * @param column
	 * @return
	 * @throws DataAccessException
	 */
	public void addIndex(Column column) throws DataAccessException;
	/**
	 * 删除某字段的索引
	 * @param column
	 * @return
	 * @throws DataAccessException
	 */
	public void removeIndex(Column column) throws DataAccessException;
}
