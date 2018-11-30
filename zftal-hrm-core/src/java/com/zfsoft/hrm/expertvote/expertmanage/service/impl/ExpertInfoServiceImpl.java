package com.zfsoft.hrm.expertvote.expertmanage.service.impl;

import java.util.List;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.expertvote.expertmanage.dao.IExpertInfoDao;
import com.zfsoft.hrm.expertvote.expertmanage.entity.ExpertInfo;
import com.zfsoft.hrm.expertvote.expertmanage.service.IExpertInfoService;

public class ExpertInfoServiceImpl implements IExpertInfoService {
	private IExpertInfoDao expertInfoDao;
	
	@Override
	public void insert(ExpertInfo expertInfo) throws RuntimeException {
		expertInfoDao.insert(expertInfo);
	}

	public void setExpertInfoDao(IExpertInfoDao expertInfoDao) {
		this.expertInfoDao = expertInfoDao;
	}

	@Override
	public PageList<ExpertInfo> getPagedExpert(ExpertInfo expertInfo)
			throws RuntimeException {
		PageList<ExpertInfo> pageList = new PageList<ExpertInfo>();
		pageList.addAll(expertInfoDao.getPagedExpert(expertInfo));
		Paginator paginator = new Paginator();
		paginator.setItemsPerPage(expertInfo.getShowCount());
		paginator.setPage(expertInfo.getCurrentPage());
		paginator.setItems(expertInfo.getTotalResult());
		pageList.setPaginator(paginator);
		return pageList;
	}

	@Override
	public void delete(String id) throws RuntimeException {
		expertInfoDao.delete(id);
	}

	@Override
	public List<String> getListIdByAll() throws RuntimeException {
		return expertInfoDao.getListIdByAll();
	}

	@Override
	public List<String> getListIdBySingle(String zydm) throws RuntimeException {
		return expertInfoDao.getListIdBySingle(zydm);
	}
}
