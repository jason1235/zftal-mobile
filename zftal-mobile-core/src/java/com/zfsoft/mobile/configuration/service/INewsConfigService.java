package com.zfsoft.mobile.configuration.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.configuration.entity.NewsConfig;
import com.zfsoft.mobile.configuration.query.NewsConfigQuery;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.myportal.query.MyPortalQuery;

public interface INewsConfigService {
	List<NewsConfig> getAll();
	NewsConfig findByKey(NewsConfigQuery query);
	void add(NewsConfigQuery query) throws Exception;
	void update(NewsConfigQuery query) throws Exception;
	int delete(NewsConfigQuery query) throws Exception;
}
