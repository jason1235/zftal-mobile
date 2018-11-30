package com.zfsoft.hrm.baseinfo.infoclass.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.query.CatalogQuery;

/**
 * 信息类目录数据操作接口
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-18
 * @version V1.0.0
 */
public interface ICatalogDao {

	/**
	 * 查询符合条件的目录信息
	 * @param query 查询条件
	 * @return 信息类目录列表
	 * @throws DataAccessException 如果操作出现异常
	 */
	public List<Catalog> findList(CatalogQuery query) throws DataAccessException;
	
	/**
	 * 查询符合条件的记录数
	 * @param query 查询条件
	 * @return 符合条件的记录数
	 * @throws DataAccessException
	 */
	public int findCount(CatalogQuery query) throws DataAccessException;
	
	/**
	 * 根据主键ID查询目录信息
	 * @param guid 目录信息的全局ID
	 * @return 单条目录信息
	 * @throws DataAccessException 如果操作出现异常
	 */
	public Catalog findById(String guid) throws DataAccessException;
	
	/**
	 * 增加目录信息
	 * @param entity 增加的目录信息
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void insert(Catalog entity) throws DataAccessException;
	
	/**
	 * 修改目录信息
	 * @param entity 目录信息
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void update(Catalog entity) throws DataAccessException;
	
	/**
	 * 删除目录信息
	 * @param guid 目录全局ID
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void delete(String guid) throws DataAccessException;
	
	/**
	 * 更新索引
	 * <p>
	 * 对指定的索引及其后面的索引向前提一位
	 * <br>用于目录删除操作后对索引的更新.
	 * </p>
	 * @param startIndex 开始的索引
	 * @throws DataAccessException 如果操作出现异常
	 */
	public void updateIndex( int startIndex ) throws DataAccessException;

}
