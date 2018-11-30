/**   
 * @Title: IItemService.java 
 * @Package com.zfsoft.hrm.baseinfo.code.service.impl 
 * @author jinjj   
 * @date 2012-5-18 上午11:56:56 
 * @version V1.0   
 */
package com.zfsoft.hrm.baseinfo.code.service.svcinterface;

import java.util.LinkedHashMap;
import java.util.List;

import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.query.ItemQuery;

/** 
 * @ClassName: IItemService 
 * @author jinjj
 * @date 2012-5-18 上午11:56:56 
 *  
 */
public interface IItemService {

	/**
	 * 
	 * @Title: getEntity 
	 * @Description: 获取条目
	 * @param entity {@link Item}
	 * @return
	 * @throws RuntimeException
	 */
	public Item getEntity(Item entity)throws RuntimeException;
	
	/**
	 * 
	 * @Title: delete 
	 * @Description:删除条目 
	 * @param list {@link Item}
	 * @return
	 * @throws RuntimeException
	 */
	public int delete(List<Item> list) throws RuntimeException;
	
	/**
	 * 更新条目
	 * @Title: update 
	 * @Description: 更新条目
	 * @param entity {@link Item}
	 * @return
	 * @throws RuntimeException
	 */
	public int update(Item entity) throws RuntimeException;
	
	/**
	 * 
	 * @Title: insert 
	 * @Description:插入条目 
	 * @param entity {@link Item}
	 * @return
	 * @throws RuntimeException
	 */
	public int insert(Item entity) throws RuntimeException;
	
	/**
	 * 
	 * @Title: getList 
	 * @Description: 条目列表（未分页）
	 * @param query {@link ItemQuery}
	 * @return List<Item>
	 * @throws RuntimeException
	 */
	public List<Item> getList(ItemQuery query) throws RuntimeException;
	
	/**
	 * 
	 * @Title: getTreeList 
	 * @Description: 获取树形对象列表
	 * @param query 查询条件 guid,catalogId
	 * @return
	 * @throws RuntimeException
	 */
	public List<Item> getTreeList(ItemQuery query)throws RuntimeException;
	
	/**
	 * 
	 * @Title: updateOrder 
	 * @Description: 更新顺序码
	 * @param list 条目集合
	 * @return
	 * @throws RuntimeException
	 */
	public int updateOrder(List<Item> list)throws RuntimeException;
	
	/**
	 * 
	 * @Title: loadCode 
	 * @Description:代码库加载 
	 * @return List <br>
	 * 				index:0 Map&lt; String,LinkedHashMap&lt;String,Item &gt;&gt; <br>
	 * 				index:1 HashMap&lt; String,Catalog &gt;
	 * @throws RuntimeException
	 */
	public List<Object> loadCode() throws RuntimeException;
	
	/**
	 * 
	 * @Title: loadCodeByCatalogId 
	 * @Description: 根据编目编号加载所属条目数据
	 * @param catalogId
	 * @return
	 */
	public LinkedHashMap<String, Item> loadCodeByCatalogId(String catalogId);
	
	/**
	 * 
	 * @Title: insertList
	 * @Description:插入条目 数组
	 * @param entity {@link List<Item>}
	 * @return
	 * @throws RuntimeException
	 */
	public int insertList(List<Item> list) throws RuntimeException;
}