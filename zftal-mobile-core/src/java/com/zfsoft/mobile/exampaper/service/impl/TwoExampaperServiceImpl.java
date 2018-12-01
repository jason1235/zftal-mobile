package com.zfsoft.mobile.exampaper.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.exampaper.dao.IExampaperDao;
import com.zfsoft.mobile.exampaper.dao.ITwoExampaperDao;
import com.zfsoft.mobile.exampaper.entity.Exampaper;
import com.zfsoft.mobile.exampaper.entity.ExampaperQes;
import com.zfsoft.mobile.exampaper.entity.TwoExampaper;
import com.zfsoft.mobile.exampaper.entity.TwoExampaperQes;
import com.zfsoft.mobile.exampaper.query.ExampaperQuery;
import com.zfsoft.mobile.exampaper.query.ResultQuery;
import com.zfsoft.mobile.exampaper.query.TwoExampaperQuery;
import com.zfsoft.mobile.exampaper.query.TwoResultQuery;
import com.zfsoft.mobile.exampaper.service.IExampaperService;
import com.zfsoft.mobile.exampaper.service.ITwoExampaperService;

public class TwoExampaperServiceImpl implements ITwoExampaperService{
	
	private ITwoExampaperDao twoExampaperDao;	
	 

	@Override
	public List<TwoExampaper> getPageList(TwoExampaperQuery query) {
		return twoExampaperDao.getPageList(query);
	}

	@Override
	public int getPageCount(TwoExampaperQuery query) {
		return twoExampaperDao.getPageCount(query);
	}
	
	public ITwoExampaperDao getTwoExampaperDao() {
		return twoExampaperDao;
	}

	public void setTwoExampaperDao(ITwoExampaperDao twoExampaperDao) {
		this.twoExampaperDao = twoExampaperDao;
	}

	@Override
	public List<TwoExampaperQes> getAllQes(TwoExampaperQuery query) {
		return twoExampaperDao.getAllQes(query);
	}

	@Override
	public int insertQesResult(TwoResultQuery query) {
		return twoExampaperDao.insertQesResult(query);
	}

	@Override
	public int insertQesResultList(List<TwoResultQuery> query) {
		return twoExampaperDao.insertQesResultList(query);
	}
	
	/**
	 * 检查某个用户是否参与某项问卷调查
	 * @param query
	 * @return
	 */
	public int checkUserPartIn(TwoExampaperQuery query){
		return twoExampaperDao.checkUserPartIn(query);
	}
}
