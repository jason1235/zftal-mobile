package com.zfsoft.mobile.configuration.service.impl;

import java.util.List;

import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.mobile.configuration.dao.INewsConfigDao;
import com.zfsoft.mobile.configuration.entity.NewsConfig;
import com.zfsoft.mobile.configuration.query.NewsConfigQuery;
import com.zfsoft.mobile.configuration.service.INewsConfigService;

public class NewsConfigServiceImpl implements INewsConfigService {

	private INewsConfigDao newsConfigDao;
	
	
	public INewsConfigDao getNewsConfigDao() {
		return newsConfigDao;
	}

	public void setNewsConfigDao(INewsConfigDao newsConfigDao) {
		this.newsConfigDao = newsConfigDao;
	}

	@Override
	public List<NewsConfig> getAll() {
		return newsConfigDao.getAll();
	}

	@Override
	public NewsConfig findByKey(NewsConfigQuery query) {
		return newsConfigDao.findByKey(query);
	}
	
	@Override
	public void add(NewsConfigQuery query) throws Exception {
		if (query.getKey() != null) {
			NewsConfig config = newsConfigDao.findByKey(query);
			if (config != null) {
				throw new RuleException("Key已经存在");
			} else {
				newsConfigDao.add(query);
			}
		}
		
	}

	@Override
	public void update(NewsConfigQuery query) throws Exception {
		newsConfigDao.update(query);
	}

	@Override
	public int delete(NewsConfigQuery query) throws Exception {
		
		return newsConfigDao.delete(query);
	}

	
}
