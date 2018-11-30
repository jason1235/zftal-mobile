package com.zfsoft.hrm.authpost.post.business.impl;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.authpost.post.business.IPostHistoryBusiness;
import com.zfsoft.hrm.authpost.post.dao.daointerface.IPostHistoryDao;
import com.zfsoft.hrm.authpost.post.dao.daointerface.IPostHistoryLogDao;
import com.zfsoft.hrm.authpost.post.entities.PostHistoryLog;
import com.zfsoft.hrm.authpost.post.query.PostHistoryLogQuery;
import com.zfsoft.hrm.authpost.post.query.PostHistoryQuery;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * @author jinjj
 * @date 2012-7-26 下午01:38:27 
 *  
 */
public class PostHistoryBusinessImpl implements IPostHistoryBusiness {

	private IPostHistoryDao postHistoryDao;
	private IPostHistoryLogDao postHistoryLogDao;
	
	@Override
	public void doAuto() {
		Date snapTime = TimeUtil.toDate(TimeUtil.dateX().substring(0, 7));
		checkExist(snapTime);
		postHistoryDao.insert(snapTime);

	}

	@Override
	public void doManual(Date snapTime) {
		checkExist(snapTime);
		postHistoryDao.insert(snapTime);
	}
	
	private void checkExist(Date snapTime){
		PostHistoryLogQuery query = new PostHistoryLogQuery();
		query.setSnapTime(snapTime);
		List<PostHistoryLog> list = postHistoryLogDao.getList(query);
		if(list.size()>0){
			throw new RuleException("该快照周期已操作");
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageList getPage(PostHistoryQuery query) {
		PageList pageList = new PageList();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(postHistoryDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(postHistoryDao.getPagingList(query));
			}
		}
		return pageList;
	}

	@Override
	public void remove(Date snapTime) {
		postHistoryDao.remove(snapTime);
	}

	public void setPostHistoryDao(IPostHistoryDao postHistoryDao) {
		this.postHistoryDao = postHistoryDao;
	}

	public void setPostHistoryLogDao(IPostHistoryLogDao postHistoryLogDao) {
		this.postHistoryLogDao = postHistoryLogDao;
	}

}
