/**
 * 
 */
package com.zfsoft.mobile.news.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.news.dao.ISchoolSceneryDao;
import com.zfsoft.mobile.news.entity.SchoolScenery;
import com.zfsoft.mobile.news.service.ISchoolSceneryService;

/**
 * @author zhangxu
 * @description 
 * @date 2017-5-10 上午10:10:43
 */
public class SchoolSceneryServiceImpl implements ISchoolSceneryService{

	private ISchoolSceneryDao schoolSceneryDao;

	public ISchoolSceneryDao getSchoolSceneryDao() {
		return schoolSceneryDao;
	}

	public void setSchoolSceneryDao(ISchoolSceneryDao schoolSceneryDao) {
		this.schoolSceneryDao = schoolSceneryDao;
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.news.service.ISchoolSceneryService#getList(com.zfsoft.mobile.news.entity.SchoolScenery)
	 */
	@Override
	public PageList<SchoolScenery> getList(SchoolScenery query) {
		// TODO Auto-generated method stub
		PageList<SchoolScenery> pageList = new PageList<SchoolScenery>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(schoolSceneryDao.getListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<SchoolScenery> list = schoolSceneryDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.news.service.ISchoolSceneryService#insert(com.zfsoft.mobile.news.entity.SchoolScenery)
	 */
	@Override
	public void insert(SchoolScenery query) {
		// TODO Auto-generated method stub
		int maxSortNumber = schoolSceneryDao.getMaxSortNumber(new SchoolScenery());
		query.setSortNumber(maxSortNumber+1);
		query.setCreateTime(new Date());
		schoolSceneryDao.insert(query);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.news.service.ISchoolSceneryService#updateIndex(java.util.Map)
	 */
	@Override
	public void updateIndex(Map<String, String> map) {
		// TODO Auto-generated method stub
		schoolSceneryDao.updateIndex(map);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.news.service.ISchoolSceneryService#update(com.zfsoft.mobile.news.entity.SchoolScenery)
	 */
	@Override
	public void update(SchoolScenery query) {
		// TODO Auto-generated method stub
		schoolSceneryDao.update(query);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.news.service.ISchoolSceneryService#delete(com.zfsoft.mobile.news.entity.SchoolScenery)
	 */
	@Override
	public void delete(SchoolScenery query) {
		// TODO Auto-generated method stub
		schoolSceneryDao.delete(query);
	}
	
	
}
