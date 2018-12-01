package com.zfsoft.mobile.exampaper.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.exampaper.dao.IExampaperDao;
import com.zfsoft.mobile.exampaper.entity.Exampaper;
import com.zfsoft.mobile.exampaper.entity.ExampaperQes;
import com.zfsoft.mobile.exampaper.query.ExampaperQuery;
import com.zfsoft.mobile.exampaper.query.ResultQuery;
import com.zfsoft.mobile.exampaper.service.IExampaperService;

public class ExampaperServiceImpl implements IExampaperService{
	
	private IExampaperDao exampaperDao;	
	 

	@Override
	public List<Exampaper> getPageList(ExampaperQuery query) {
		return exampaperDao.getPageList(query);
	}

	@Override
	public int getPageCount(ExampaperQuery query) {
		return exampaperDao.getPageCount(query);
	}
	
	public IExampaperDao getExampaperDao() {
		return exampaperDao;
	}

	public void setExampaperDao(IExampaperDao exampaperDao) {
		this.exampaperDao = exampaperDao;
	}

	@Override
	public List<ExampaperQes> getAllQes(ExampaperQuery query) {
		return exampaperDao.getAllQes(query);
	}

	@Override
	public int insertQesResult(ResultQuery query) {
		return exampaperDao.insertQesResult(query);
	}

	@Override
	public int insertQesResultList(List<ResultQuery> query) {
		return exampaperDao.insertQesResultList(query);
	}
	
	/**
	 * 检查某个用户是否参与某项问卷调查
	 * @param query
	 * @return
	 */
	public int checkUserPartIn(ExampaperQuery query){
		return exampaperDao.checkUserPartIn(query);
	}

	@Override
	public Exampaper selectExampaperById(String id) {
		return exampaperDao.selectExampaperById(id);
	}
}
