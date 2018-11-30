package com.zfsoft.mobile.helpguide.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.helpguide.dao.IHelpGuideDao;
import com.zfsoft.mobile.helpguide.entity.HelpGuideEntity;
import com.zfsoft.mobile.helpguide.query.HelpGuideQuery;
import com.zfsoft.mobile.helpguide.service.IHelpGuideService;
import com.zfsoft.mobile.news.entity.Counts;

public class IHelpGuideServiceImpl implements IHelpGuideService{

	private IHelpGuideDao helpGDao;
	
	@Override
	public PageList<HelpGuideEntity> getPageList(HelpGuideQuery query) {
		PageList<HelpGuideEntity> pagelist = new PageList<HelpGuideEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			//paginator.setItemsPerPage(10);
			paginator.setPage(query.getToPage());
			paginator.setItems(helpGDao.getPageCount(query));
			pagelist.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pagelist;
			}
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<HelpGuideEntity> list = helpGDao.getPageList(query);
				pagelist.addAll(list);
			}
		}
		return pagelist;
	}

	@Override
	public HelpGuideEntity findById(HelpGuideQuery query) {
			return	helpGDao.findById(query);
	}

	@Override
	public void saveOrUpdate(HelpGuideQuery query) {
		if(StringUtils.isNotBlank(query.getId())){
			helpGDao.update(query);
		}else{
			int pxm = helpGDao.getMaxPxm();
			query.setOrderNumber(pxm+1);
			helpGDao.insert(query);
		}
	}
	
	@Override
	public void remove(Map<String,Object> ids) {
		helpGDao.delete(ids);
	}
	
	public void remove(String id) {
		helpGDao.delete(id);
	}
	
	@Override
	public void updateIndex(Map<String, String> map) {
		helpGDao.updateIndex(map);
	}

	@Override
	public int guideControl(HelpGuideQuery query) {
		return helpGDao.guideControl(query);
	}
	@Override
	public int getMaxPxm() {
		return helpGDao.getMaxPxm();
	}

	@Override
	public Counts getRmdCount(HelpGuideQuery query) {
		return helpGDao.getRmdCount(query);
	}

	public IHelpGuideDao getHelpGDao() {
		return helpGDao;
	}

	public void setHelpGDao(IHelpGuideDao helpGDao) {
		this.helpGDao = helpGDao;
	}

	@Override
	public List<HelpGuideEntity> getAllList(HelpGuideQuery query) {
		return helpGDao.getAllList(query);
	}


}
