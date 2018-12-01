package com.zfsoft.mobile.helpguide.dao;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.helpguide.entity.HelpGuideEntity;
import com.zfsoft.mobile.helpguide.query.HelpGuideQuery;
import com.zfsoft.mobile.news.entity.Counts;

public interface IHelpGuideDao {
	
	/**
	 * 获取数量
	 * @param query
	 * @return
	 */
	int getPageCount(HelpGuideQuery query);
	int getMaxPxm();
	
	/**
	 * 获取分页列表
	 * @param query
	 * @return
	 */
	PageList<HelpGuideEntity> getPageList(HelpGuideQuery query);
	List<HelpGuideEntity> getAllList(HelpGuideQuery query);
	
	/**
	 * 根据ID获取
	 * @param query
	 * @return
	 */
	HelpGuideEntity findById(HelpGuideQuery query);
	
	/**
	 * 插入
	 * @param query
	 */
	void insert(HelpGuideQuery query);
	
	/**
	 * 更新
	 * @param query
	 */
	int update(HelpGuideQuery query);
	
	void updateIndex(Map<String, String> map);
	
	void delete(Map<String,Object> id);
	void delete(String id);
	
	/**
	 * 控制（启用，撤销启用，推荐，撤销推荐）
	 * @param query
	 * @return
	 */
	int guideControl(HelpGuideQuery query);
	
	/**
	 * 根据类别获取推荐数
	 * @param query
	 * @return
	 */
	Counts getRmdCount(HelpGuideQuery query);

}
