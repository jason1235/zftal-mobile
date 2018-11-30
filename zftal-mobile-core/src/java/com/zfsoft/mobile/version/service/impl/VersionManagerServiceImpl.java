package com.zfsoft.mobile.version.service.impl;

import java.util.Date;
import java.util.List;

import com.common.Config;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.version.dao.daointerface.IVersionManagerDao;
import com.zfsoft.mobile.version.entity.VersionManager;
import com.zfsoft.mobile.version.service.IVersionManagerService;

public class VersionManagerServiceImpl implements IVersionManagerService {

	private IVersionManagerDao versionManagerDao;

	public PageList<VersionManager> getList(VersionManager query) {
		PageList<VersionManager> pageList = new PageList<VersionManager>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(versionManagerDao.getListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<VersionManager> list = versionManagerDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	
	@Override
	public void insert(VersionManager query) {
		query.setServeraddress(Config.getString("webservice.host.newmobile"));
		query.setCheckStatus("1");
		query.setTimecreate(new Date());
		versionManagerDao.insert(query);
	}
	
	@Override
	public void update(VersionManager query) {
		query.setTimeModify(new Date());
		versionManagerDao.update(query);
	}

	public void setVersionManagerDao(IVersionManagerDao versionManagerDao) {
		this.versionManagerDao = versionManagerDao;
	}

	public IVersionManagerDao getVersionManagerDao() {
		return versionManagerDao;
	}

	@Override
	public void updateStatusById(VersionManager query) {
		versionManagerDao.updateStatusById(query);
	}

	@Override
	public void delete(VersionManager query) {
		versionManagerDao.delete(query);
	}

	
	
	
}
