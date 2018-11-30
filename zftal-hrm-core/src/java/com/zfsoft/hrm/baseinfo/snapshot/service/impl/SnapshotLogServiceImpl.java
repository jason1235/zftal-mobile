package com.zfsoft.hrm.baseinfo.snapshot.service.impl;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.snapshot.dao.daointerface.ISnapshotLogDao;
import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotLog;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotLogQuery;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotLogService;

/** 
 * 快照日志服务实现
 * @ClassName: SnapshotLogServiceImpl 
 * @author jinjj
 * @date 2012-7-18 上午11:30:57 
 *  
 */
public class SnapshotLogServiceImpl implements ISnapshotLogService {

	private ISnapshotLogDao snapshotLogDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList getPage(SnapshotLogQuery query) {
		PageList pageList = new PageList();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(snapshotLogDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(snapshotLogDao.getPagingList(query));
			}
		}
		return pageList;
	}

	@Override
	public void save(SnapshotLog log) {
		log.setOperateTime(new Date());
		snapshotLogDao.insert(log);
	}
	
	@Override
	public List<SnapshotLog> getList(SnapshotLogQuery query) {
		List<SnapshotLog> list = snapshotLogDao.getList(query);
		return list;
	}
	
	@Override
	public void delete(Date snapTime) {
		SnapshotLogQuery query = new SnapshotLogQuery();
		query.setSnapTime(snapTime);
		snapshotLogDao.delete(query);
	}

	public void setSnapshotLogDao(ISnapshotLogDao snapshotLogDao) {
		this.snapshotLogDao = snapshotLogDao;
	}

}
