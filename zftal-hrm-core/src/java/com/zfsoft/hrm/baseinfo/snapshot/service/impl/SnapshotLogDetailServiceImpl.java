package com.zfsoft.hrm.baseinfo.snapshot.service.impl;

import java.util.Date;
import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.snapshot.dao.daointerface.ISnapshotLogDetailDao;
import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotLogDetail;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotLogDetailQuery;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotLogDetailService;

/** 
 * @ClassName: SnapshotLogDetailServiceImpl 
 * @author jinjj
 * @date 2012-7-18 上午11:46:25 
 *  
 */
public class SnapshotLogDetailServiceImpl implements ISnapshotLogDetailService {

	private ISnapshotLogDetailDao snapshotLogDetailDao;
	
	@SuppressWarnings("unchecked")
	@Override
	public PageList getPage(SnapshotLogDetailQuery query) {
		PageList pageList = new PageList();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(snapshotLogDetailDao.getPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<SnapshotLogDetail> list = snapshotLogDetailDao.getPagingList(query);
				for(int i=0;i<list.size();i++){
					SnapshotLogDetail detail = list.get(i);
					String classId = detail.getClazz().getGuid();
					detail.setClazz( InfoClassCache.getInfoClass(classId) );
					list.set(i, detail);
				}
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void delete(Date snapTime) {
		SnapshotLogDetailQuery query = new SnapshotLogDetailQuery();
		query.setSnapTime(snapTime);
		snapshotLogDetailDao.delete(query);
		
	}


	@Override
	public void save(SnapshotLogDetail detail) {
		snapshotLogDetailDao.insert(detail);
	}

	public void setSnapshotLogDetailDao(ISnapshotLogDetailDao snapshotLogDetailDao) {
		this.snapshotLogDetailDao = snapshotLogDetailDao;
	}

}
