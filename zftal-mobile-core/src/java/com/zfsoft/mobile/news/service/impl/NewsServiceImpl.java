package com.zfsoft.mobile.news.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.common.enums.ShowTypeEnum;
import com.zfsoft.mobile.news.dao.INewsDao;
import com.zfsoft.mobile.news.entity.Counts;
import com.zfsoft.mobile.news.entity.News;
import com.zfsoft.mobile.news.query.NewsQuery;
import com.zfsoft.mobile.news.service.INewsService;

public class NewsServiceImpl implements INewsService {

	private INewsDao newsDao;
	
	@Override
	public PageList<News> getPageList(NewsQuery query) {
		PageList<News> pageList = new PageList<News>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(newsDao.getPageCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(newsDao.getPageList(query));
			}
		}
		return pageList;
	}

	@Override
	public void doSave(NewsQuery query) throws Exception {
		/*if (ShowTypeEnum.DETAIL_SHOW.getText().equals(query.getShowType())) {
			newsDao.updateDetail(query);
		}*/
		if (query.getId() == null || "".equals(query.getId())) {
			newsDao.insert(query);
		} else {
			newsDao.update(query);
		}
		
		
	}

	@Override
	public News findById(NewsQuery query) {
		
		return newsDao.findById(query);
	}

	@Override
	public void controlNews(NewsQuery query) throws Exception {
		newsDao.newsControl(query);
		
	}
	
	@Override
	public Counts getTopAndRmdCount(NewsQuery query) {
		
		return newsDao.getTopAndRmdCount(query);
	}
	
	@Override
	public int getHeadlineCount() {
		return newsDao.getHeadlineCount();
	}

	
	
	@Override
	public List<News> getRmdByCatalogCode(NewsQuery query) {
		return newsDao.getRmdByCatalogCode(query);
	}

	@Override
	public List<News> getRmdByCatalogName(NewsQuery query) {
		return newsDao.getRmdByCatalogName(query);
	}

	@Override
	public List<News> getUnRmdByCatalogCode(NewsQuery query) {
		return newsDao.getUnRmdByCatalogCode(query);
	}

	@Override
	public List<News> getUnRmdByCatalogName(NewsQuery query) {
		return newsDao.getUnRmdByCatalogName(query);
	}

	public void setNewsDao(INewsDao newsDao) {
		this.newsDao = newsDao;
	}

	public INewsDao getNewsDao() {
		return newsDao;
	}
	
}
