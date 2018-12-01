/**
 * 
 */
package com.zfsoft.mobile.news.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.news.dao.ISchoolSceneryCatalogDao;
import com.zfsoft.mobile.news.entity.SchoolScenery;
import com.zfsoft.mobile.news.entity.SchoolSceneryCatalog;
import com.zfsoft.mobile.news.service.ISchoolSceneryCatalogService;

/**
 * @author zhangxu
 * @description 
 * @date 2017-5-10 上午10:02:18
 */
public class SchoolSceneryCatalogServiceImpl implements
		ISchoolSceneryCatalogService {

	private ISchoolSceneryCatalogDao schoolSceneryCatalogDao;

	public ISchoolSceneryCatalogDao getSchoolSceneryCatalogDao() {
		return schoolSceneryCatalogDao;
	}

	public void setSchoolSceneryCatalogDao(
			ISchoolSceneryCatalogDao schoolSceneryCatalogDao) {
		this.schoolSceneryCatalogDao = schoolSceneryCatalogDao;
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.news.service.ISchoolSceneryCatalogService#getList(com.zfsoft.mobile.news.entity.SchoolSceneryCatalog)
	 */
	@Override
	public PageList<SchoolSceneryCatalog> getList(SchoolSceneryCatalog query) {
		// TODO Auto-generated method stub
		PageList<SchoolSceneryCatalog> pageList = new PageList<SchoolSceneryCatalog>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(schoolSceneryCatalogDao.getListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<SchoolSceneryCatalog> list = schoolSceneryCatalogDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.news.service.ISchoolSceneryCatalogService#insert(com.zfsoft.mobile.news.entity.SchoolSceneryCatalog)
	 */
	@Override
	public void insert(SchoolSceneryCatalog query) {
		// TODO Auto-generated method stub
		int maxSortNumber = schoolSceneryCatalogDao.getMaxSortNumber(new SchoolScenery());
		query.setSortNumber(maxSortNumber+1);
		query.setCreateTime(new Date());
		schoolSceneryCatalogDao.insert(query);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.news.service.ISchoolSceneryCatalogService#update(com.zfsoft.mobile.news.entity.SchoolSceneryCatalog)
	 */
	@Override
	public void update(SchoolSceneryCatalog query) {
		// TODO Auto-generated method stub
		schoolSceneryCatalogDao.update(query);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.news.service.ISchoolSceneryCatalogService#updateIndex(java.util.Map)
	 */
	@Override
	public void updateIndex(Map<String, String> map) {
		// TODO Auto-generated method stub
		schoolSceneryCatalogDao.updateIndex(map);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.news.service.ISchoolSceneryCatalogService#delete(com.zfsoft.mobile.news.entity.SchoolSceneryCatalog)
	 */
	@Override
	public void delete(SchoolSceneryCatalog query) {
		// TODO Auto-generated method stub
		schoolSceneryCatalogDao.delete(query);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.news.service.ISchoolSceneryCatalogService#getAllList()
	 */
	@Override
	public List<SchoolSceneryCatalog> getAllList() {
		// TODO Auto-generated method stub
		return schoolSceneryCatalogDao.getAllList();
	}
	
	
}
