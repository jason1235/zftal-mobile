package com.zfsoft.common.dao;


import java.util.List;
import java.util.Map;


/**
 * 类名称： 通用DAO接口
 * 创建人：Penghui.Qu
 * 创建时间：2012-5-3
 * 修改人：Zhenfei.Cao
 * 修改时间：2012-8-2
 */
public interface BaseDao <T> {

	/**
	 * 增加记录
	 * @param t
	 * @return
	 */
	public int insert(T t);
	
	/**
	 * 增加记录
	 * @param t
	 * @return
	 */
	public int insertNew(T t);
	
	/**
	 * 修改记录
	 * @param t
	 * @return
	 */
	public int update(T t);
	
	/**
	 * 修改记录
	 * @param t
	 * @return
	 */
	public int updateNew(T t);
	
	/**
	 * 查询单条数据
	 * @param id
	 * @return
	 */
	public T getModel(String id);
	
	/**
	 * 查询单条数据
	 * @param t
	 * @return
	 */
	public T getModel(T t) ;
	
	/**
	 * 批量删除
	 * @param map
	 * @return
	 */
	public int batchDelete(Map<String,Object> map);
	
	
	
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int batchDelete(List list);
	
	@SuppressWarnings("unchecked")
	public int batchNewDelete(String jsdm);
	
	
	
	/**
	 * 批量修改
	 * @param map
	 * @return
	 */
	public int batchUpdate(Map<String,Object> map);
	
	/**
	 * 分页查询
	 * @param t
	 * @return
	 */
	public List<T> getPagedList(T t);
	
	
	/**
	 * 无分页查询
	 * @param t
	 * @return
	 */
	public List<T> getModelList(T t);
	
	
	/**
	 * 无分页查询
	 * @param str
	 * @return
	 */
	public List<T> getModelList(String... str);
	
	/**
	 * 统计记录数
	 * @param t
	 * @return
	 */
	public int getCount(T t);
	
	/**
	 * 按数据范围分页查询
	 * @param t
	 * @return
	 */
	public List<T> getPagedByScope(T t);
	
	/**
	 * 按数据范围无分页查询
	 * @param t
	 * @return
	 */
	public List<T> getModelListByScope(T t);
}
