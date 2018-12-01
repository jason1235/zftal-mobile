package com.zfsoft.mobile.exampaper.service;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.exampaper.entity.Exampaper;
import com.zfsoft.mobile.exampaper.entity.ExampaperQes;
import com.zfsoft.mobile.exampaper.query.ExampaperQuery;
import com.zfsoft.mobile.exampaper.query.ResultQuery;

public interface IExampaperService {

	/**
	 * 获取问卷分页列表
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
	
	Exampaper selectExampaperById(String id);
}
