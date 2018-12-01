package com.zfsoft.hrm.baseinfo.code.dao.daointerface;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.query.ItemQuery;

/** 
 * @ClassName: IItemDao 
 * @Description: 代码条目DAO
 * @author jinjj
 * @date 2012-5-18 上午11:49:19 
 *  
 */
public interface IItemDao {

	
	/**
	 * 
	 * @Title: deleteCatalogRealatedItems 
	 * @Description: 删除编目所属条目
	 * @param catalog {@link Catalog}
	 * @return 
	 * @throws DataAccessException
	 */
	public int deleteCatalogRealatedItems(Catalog catalog) throws DataAccessException;
	
	/**
	 * 
	 * @Title: getEntity 
	 * @Description: 获取条目
	 * @param entity {@link Item}
	 * @return 
	 * @throws DataAccessException
	 */
	public Item getEntity(Item entity)throws DataAccessException;
	
	/**
	 * 
	 * @Title: update 
	 * @Description: 更新条目
	 * @param entity {@link Item}
	 * @return
	 * @throws DataAccessException
	 */
	public int update(Item entity) throws DataAccessException;
	
	/**
	 * 
	 * @Title: insert 
	 * @Description: 插入条目 
	 * @param entity {@link Item}
	 * @return
	 * @throws DataAccessException
	 */
	public int insert(Item entity) throws DataAccessException;
	
	/**
	 * 
	 * @Title: delete 
	 * @Description: 删除条目
	 * @param item 条目guid
	 * @return
	 * @throws DataAccessException
	 */
	public int delete(Item item) throws DataAccessException;
	
	/**
	 * 
	 * @Title: getList 
	 * @Description: 条目列表（不分页）
	 * @param query 查询条件{@link ItemQuery}
	 * @return List<Item>
	 * @throws DataAccessException
	 */
	public List<Item> getList(ItemQuery query) throws DataAccessException;
	
	public List<Item> getTopNodeList(ItemQuery query) throws DataAccessException;
}
