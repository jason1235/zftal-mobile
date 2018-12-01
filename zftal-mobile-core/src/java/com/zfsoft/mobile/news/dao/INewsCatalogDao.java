package com.zfsoft.mobile.news.dao;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.news.entity.NewsCatalog;
import com.zfsoft.mobile.news.query.NewsCatalogQuery;

/**
 * 资讯类别DAO
 * @author wy
 *
 */
public interface INewsCatalogDao {
	/**
	 * 获取分页列表
	 * @param query
	 * @return
	 */
	PageList<NewsCatalog> getPageList(NewsCatalogQuery query);
	
	/**
	 * 获取总数
	 * @param query
	 * @return
	 */
	int getPageCount(NewsCatalogQuery query);
	
	/**
	 * 根据类别名称获取类别
	 * @param query
	 * @return
	 */
	NewsCatalog findByName(NewsCatalogQuery query);
	
	/**
	 * 根据类别ID获取类别
	 * @param query
	 * @return
	 */
	NewsCatalog findById(NewsCatalogQuery query);
	
	/**
	 * 根据类别ID获取类别下新闻数
	 * @param query
	 * @return
	 */
	int countNewsByCatalogId(NewsCatalogQuery query);
	
	/**
	 * 获取最大的排序码
	 * @return
	 */
	String getMaxPxm();
	
	/**
	 * 获取所有的资讯类别
	 * @return
	 */
	List<NewsCatalog> getAllCatalog(Map<String, Object> param);
	
	/**
	 * 增加类别
	 * @param query
	 * @return
	 */
	int insert(NewsCatalogQuery query);
	
	/**
	 * 更新类别
	 * @param query
	 * @return
	 */
	int update(NewsCatalogQuery query);
	
	/**
	 * 删除类别
	 * @param query
	 */
	void delete(NewsCatalogQuery query);
	
	/**
	 * 启用类别
	 * @param query
	 */
	void enable(NewsCatalogQuery query);
	
	/**
	 * 停用类别
	 * @param query
	 */
	void disable(NewsCatalogQuery query);
	
	/**
	 * 查询类别下已经启用的资讯数量
	 * @param query
	 * @return
	 */
	int countEnable(NewsCatalogQuery query);

	void updateByService(NewsCatalog newsCatalog);

	void insertFwqx(String catalogCode);
	
	void updateIndex(Map<String,String> map);
}
