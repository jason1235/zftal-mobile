package com.zfsoft.hrm.expertvote.vote.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.expertvote.vote.dao.IDeclareInstanceDao;
import com.zfsoft.hrm.expertvote.vote.entity.DeclareInstance;
import com.zfsoft.hrm.expertvote.vote.query.DeclareInstanceQuery;
import com.zfsoft.hrm.expertvote.vote.service.IDeclareInstanceService;

/**
 * 
 * @author ChenMinming
 * @date 2014-3-27
 * @version V1.0.0
 */
public class DeclareInstanceServiceImpl implements IDeclareInstanceService {

	private IDeclareInstanceDao declareInstanceDao;
	@Override
	public DeclareInstance getById(String id) {
		return declareInstanceDao.findById(id);
	}

	@Override
	public PageList<DeclareInstance> getPageList(DeclareInstanceQuery query) {
		PageList<DeclareInstance> pageList = new PageList<DeclareInstance>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(declareInstanceDao.getInstancePageCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<DeclareInstance> list = declareInstanceDao.getInstancePageList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	public void setDeclareInstanceDao(IDeclareInstanceDao declareInstanceDao) {
		this.declareInstanceDao = declareInstanceDao;
	}
	

}
