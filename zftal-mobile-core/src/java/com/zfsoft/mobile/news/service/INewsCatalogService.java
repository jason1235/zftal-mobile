package com.zfsoft.mobile.news.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.news.entity.NewsCatalog;
import com.zfsoft.mobile.news.query.NewsCatalogQuery;

/**
 * 资讯类别service
 * @author Administrator
 *
 */
public interface INewsCatalogService {
	
	/**
	 * 获取分页列表
	 * @param query
	 * @return
	 */
	PageList<NewsCatalog> getPageList(NewsCatalogQuery query);
	
	/**
	 * 新增类别
	 * @param query
	 */
	 void doSave(NewsCatalogQuery query, String originalName) throws Exception;
	
	/**
	 * 更新类别
	 * @param query
	 */
	void doUpdate(NewsCatalogQuery query) throws Exception;
	
	/**
	 * 删除类别（逻辑删除）
	 * @param query
	 */
	void doDelete(NewsCatalogQuery query) throws Exception;
	
	/**
	 * 根据Id查找类别
	 * @param query
	 */
	NewsCatalog findById(NewsCatalogQuery query);
	
	/**
	 * 根据类别名称查找类别
	 * @param query
	 * @return
	 */
	NewsCatalog findByName(NewsCatalogQuery query);
	
	/**
	 * 获取所有资讯类别
	 * @return
	 */
	List<NewsCatalog> getAllCatalog(Map<String, Object> param);
	
	/**
	 * 启用类别
	 * @param query
	 */
	void enable(NewsCatalogQuery query) throws Exception;
	
	/**
	 * 停用类别
	 * @param query
	 */
	void disable(NewsCatalogQuery query) throws Exception;
	
	/**
	 * 查询类别下已经启用的资讯数量
	 * @param query
	 * @return
	 */
	int countEnable(NewsCatalogQuery query);
	
	void updateIndex(Map<String,String> map) throws Exception;
}
