package com.zfsoft.mobile.news.dao;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.news.entity.Counts;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.news.query.NewsQuery;

/**
 * 资讯Dao
 * @author wy
 *
 */
public interface INewsDao {
	
	/**
	 * 获取资讯分页列表
	 * @param query
	 * @return
	 */
	PageList<News> getPageList(NewsQuery query);
	
	/**
	 * 获取资讯数量
	 * @param query
	 * @return
	 */
	int getPageCount(NewsQuery query);
	
	/**
	 * 插入新资讯
	 * @param query
	 */
	void insert(NewsQuery query);
	
	/**
	 * 根据ID获取资讯
	 * @param query
	 * @return
	 */
	News findById(NewsQuery query);
	
	/**
	 * 更新资讯
	 * @param query
	 */
	int update(NewsQuery query);
	
	/**
	 * 资讯控制（启用，撤销启用，置顶，撤销置顶，推荐，撤销推荐，删除）
	 * @param query
	 * @return
	 */
	int newsControl(NewsQuery query);
	
	/**
	 * 根据资讯类别获取置顶和推荐数
	 * @param query
	 * @return
	 */
	Counts getTopAndRmdCount(NewsQuery query);
	
	/**
	 * 将详情下的所有除此资讯停用
	 * @param query
	 * @return
	 */
	int updateDetail(NewsQuery query);
	
	/**
	 * 获取头条的资讯数
	 * @return
	 */
	int getHeadlineCount();
	
	List<News> getRmdByCatalogCode(NewsQuery query);
	
	List<News> getRmdByCatalogName(NewsQuery query);
	
	List<News> getUnRmdByCatalogCode(NewsQuery query);
	
	List<News> getUnRmdByCatalogName(NewsQuery query);
}
