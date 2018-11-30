package com.zfsoft.hrm.expertvote.vote.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.expertvote.vote.dao.IExpertGroupDao;
import com.zfsoft.hrm.expertvote.vote.entity.ExpertGroup;
import com.zfsoft.hrm.expertvote.vote.query.ExpertGroupQuery;
import com.zfsoft.hrm.expertvote.vote.service.IExpertGroupService;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-11
 * @version V1.0.0
 */
 public class ExpertGroupServiceImpl implements IExpertGroupService
{
	 private IExpertGroupDao expertGroupDao;

	@Override
	public void delete(String id) {
		expertGroupDao.delete(id);
	}

	@Override
	public PageList<ExpertGroup> getPageList(ExpertGroupQuery query) {
		PageList<ExpertGroup> pageList = new PageList<ExpertGroup>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(expertGroupDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ExpertGroup> list = expertGroupDao.getPagingList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	@Override
	public List<ExpertGroup> getList(ExpertGroupQuery query) {
		return expertGroupDao.getList(query);
	}

	@Override
	public void modifyExpertGroup(ExpertGroup expertGroup) {
		expertGroupDao.update(expertGroup);
	}

	@Override
	public void saveExpertGroup(ExpertGroup expertGroup) {
		expertGroupDao.insert(expertGroup);
	}

	@Override
	public ExpertGroup getById(String id) {
		return expertGroupDao.getById(id);
	}
	
	public void setExpertGroupDao(IExpertGroupDao expertGroupDao) {
		this.expertGroupDao = expertGroupDao;
	}


	
}
