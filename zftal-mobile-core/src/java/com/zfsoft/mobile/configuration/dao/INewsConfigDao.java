package com.zfsoft.mobile.configuration.dao;

import java.util.List;
import java.util.Map;


import com.zfsoft.mobile.configuration.entity.NewsConfig;
import com.zfsoft.mobile.configuration.query.NewsConfigQuery;

public interface INewsConfigDao {
	List<NewsConfig> getAll();
	NewsConfig findByKey(NewsConfigQuery query);
	void add(NewsConfigQuery query);
	void update(NewsConfigQuery query);
	int delete(NewsConfigQuery query);
}
