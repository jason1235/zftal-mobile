package com.zfsoft.hrm.baseinfo.code.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.query.CatalogQuery;

/** 
 * @ClassName: ICatalogDao 
 * @Description: 代码编目DAO
 * @author jinjj
 * @date 2012-5-18 上午11:48:14 
 *  
 */
public interface ICatalogDao {

	/**
	 * 
	 * @Title: getEntity 
	 * @Description: 获取编目
	 * @param entity 编目实体
	 * @return 
	 * @throws DataAccessException
	 */
	public Catalog getEntity(Catalog entity)throws DataAccessException;
	
	/**
	 * 
	 * @Title: update 
	 * @Description: 更新编目
	 * @param entity 编目实体
	 * @return
	 * @throws DataAccessException
	 */
	public int update(Catalog entity) throws DataAccessException;
	
	/**
	 * 
	 * @Title: insert 
	 * @Description: 插入编目 
	 * @param entity 编目实体
	 * @return
	 * @throws DataAccessException
	 */
	public int insert(Catalog entity) throws DataAccessException;
	
	/**
	 * 
	 * @Title: delete 
	 * @Description: 删除编目
	 * @param id 编目guid
	 * @return
	 * @throws DataAccessException
	 */
	public int delete(String id) throws DataAccessException;
	
	/**
	 * 
	 * @Title: getList 
	 * @Description: 编目列表（不分页）
	 * @param query 查询条件{@link CatalogQuery}
	 * @return List<Catalog>
	 * @throws DataAccessException
	 */
	public List<Catalog> getList(CatalogQuery query) throws DataAccessException;
	
	/**
	 * 获取编目分页列表数据
	 * @param query
	 * @return List &lt;Catalog>
	 * @throws DataAccessException
	 */
	public List<Catalog> findPagingInfoList(CatalogQuery query) throws DataAccessException;
	
	/**
	 * 获取编目分页查询计数
	 * @param query
	 * @return
	 * @throws DataAccessException
	 */
	public int findPagingCount(CatalogQuery query) throws DataAccessException;
}
