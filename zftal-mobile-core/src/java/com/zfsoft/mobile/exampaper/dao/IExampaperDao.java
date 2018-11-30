package com.zfsoft.mobile.exampaper.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.exampaper.entity.Exampaper;
import com.zfsoft.mobile.exampaper.entity.ExampaperQes;
import com.zfsoft.mobile.exampaper.query.ExampaperQuery;
import com.zfsoft.mobile.exampaper.query.ResultQuery;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.news.query.NewsQuery;


/**
 * 调查问卷Dao
 * @author liucb
 *
 */
public interface IExampaperDao {

	/**
	 * 获取问卷列表
	 * @param query
	 * @return
	 */
	List<Exampaper> getPageList(ExampaperQuery query);
	
	/**
	 * 获取问卷数量
	 * @param query
	 * @return
	 */
	int getPageCount(ExampaperQuery query);
	
	
	/**
	 * 根据问卷id获取所有相关问题
	 * @param query
	 * @return
	 */
	List<ExampaperQes> getAllQes(ExampaperQuery query);
	
	/**
	 * 插入一条问卷答案记录
	 * @param query
	 */
	int insertQesResult(ResultQuery query);
	
	
	/**
	 * 批量插入问卷答案记录
	 * @param query
	 */
	int insertQesResultList(List<ResultQuery> query);
	
	/**
	 * 检查某个用户是否参与某项问卷调查
	 * @param query
	 * @return
	 */
	int checkUserPartIn(ExampaperQuery query);

	Exampaper selectExampaperById(@Param("id") String id);
}
