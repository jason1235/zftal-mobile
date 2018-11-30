package com.zfsoft.mobile.helpguide.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.helpguide.entity.HelpGuideEntity;
import com.zfsoft.mobile.helpguide.query.HelpGuideQuery;
import com.zfsoft.mobile.news.entity.Counts;

public interface IHelpGuideService {
	
	PageList<HelpGuideEntity> getPageList(HelpGuideQuery query);
	List<HelpGuideEntity> getAllList(HelpGuideQuery query);
	HelpGuideEntity findById(HelpGuideQuery query);
	
	void saveOrUpdate(HelpGuideQuery query);
	void updateIndex(Map<String, String> map);
	void remove(Map<String,Object> param);
	int guideControl(HelpGuideQuery query);
	int getMaxPxm();
	void remove(String ids);
	Counts getRmdCount(HelpGuideQuery query);
}
