package com.zfsoft.mobile.news.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.news.entity.Counts;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.news.query.NewsQuery;

public interface INewsService {

	/**
	 * 获取资讯分页列表
	 * @param query
	 * @return
	 */
	PageList<News> getPageList(NewsQuery query);
	
	/**
	 * 保存资讯
	 * @param query
	 */
	void doSave(NewsQuery query) throws Exception;
	
	/**
	 * 根据ID获取资讯
	 * @param query
	 * @return
	 */
	News findById(NewsQuery query);
	
	/**
	 * 资讯控制
	 * @param query
	 * @throws Exception
	 */
	void controlNews(NewsQuery query) throws Exception;
	
	/**
	 * 根据资讯类别获取置顶和推荐数
	 * @param query
	 * @return
	 */
	Counts getTopAndRmdCount(NewsQuery query);
	
	/**
	 * 获取头条资讯数
	 * @return
	 */
	int getHeadlineCount();
	
	/**
	 * 根据类别编码获取推荐资讯
	 * @param query
	 * @return
	 */
	List<News> getRmdByCatalogCode(NewsQuery query);
	
	/**
	 * 根据类别名称获取推荐资讯
	 * @param query
	 * @return
	 */
	List<News> getRmdByCatalogName(NewsQuery query);
	
	
	/**
	 * 根据类别编码获取资讯（除推荐）
	 * @param query
	 * @return
	 */
	List<News> getUnRmdByCatalogCode(NewsQuery query);
	
	/**
	 * 根据类别名称获取资讯（除推荐）
	 * @param query
	 * @return
	 */
	List<News> getUnRmdByCatalogName(NewsQuery query);
}
