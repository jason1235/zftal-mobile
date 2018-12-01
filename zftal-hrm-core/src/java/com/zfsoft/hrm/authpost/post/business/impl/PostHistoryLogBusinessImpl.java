package com.zfsoft.hrm.authpost.post.business.impl;

import java.util.Date;
import java.util.List;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.authpost.post.business.IPostHistoryLogBusiness;
import com.zfsoft.hrm.authpost.post.dao.daointerface.IPostHistoryLogDao;
import com.zfsoft.hrm.authpost.post.entities.PostHistoryLog;
import com.zfsoft.hrm.authpost.post.query.PostHistoryLogQuery;
import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * @author jinjj
 * @date 2012-7-26 下午01:24:44 
 *  
 */
public class PostHistoryLogBusinessImpl implements IPostHistoryLogBusiness {

	private IPostHistoryLogDao postHistoryLogDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList getPage(PostHistoryLogQuery query) {
		PageList pageList = new PageList();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(postHistoryLogDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(postHistoryLogDao.getPagingList(query));
			}
		}
		return pageList;
	}

	@Override
	public void remove(Date snapTime) {
		PostHistoryLogQuery query = new PostHistoryLogQuery();
		query.setSnapTime(snapTime);
		postHistoryLogDao.delete(query);
	}

	@Override
	public void saveAutoLog() {
		PostHistoryLog log = new PostHistoryLog();
		log.setOperator("system");
		log.setOperateTime(new Date());
		log.setSnapTime(TimeUtil.toDate(TimeUtil.dateX().substring(0, 7)));
		postHistoryLogDao.insert(log);
	}

	@Override
	public void saveManualLog(Date snapTime) {
		PostHistoryLog log = new PostHistoryLog();
		log.setOperator(SessionFactory.getUser().getXm());
		log.setOperateTime(new Date());
		log.setSnapTime(snapTime);
		postHistoryLogDao.insert(log);
	}
	
	@Override
	public List<PostHistoryLog> getList(PostHistoryLogQuery query) {
		return postHistoryLogDao.getList(query);
	}

	public void setPostHistoryLogDao(IPostHistoryLogDao postHistoryLogDao) {
		this.postHistoryLogDao = postHistoryLogDao;
	}

}
