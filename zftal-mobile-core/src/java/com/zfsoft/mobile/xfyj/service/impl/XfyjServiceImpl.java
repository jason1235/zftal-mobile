package com.zfsoft.mobile.xfyj.service.impl;

import java.util.List;

//import com.ibm.db2.jcc.a.q;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.ballot.entity.BallotDetail;
import com.zfsoft.mobile.xfyj.dao.XfyjDao;
import com.zfsoft.mobile.xfyj.entity.XfyjEntity;
import com.zfsoft.mobile.xfyj.entity.XfyjxqEntity;
import com.zfsoft.mobile.xfyj.entity.XfyjxqQueryEntity;
import com.zfsoft.mobile.xfyj.service.XfyjService;

public class XfyjServiceImpl implements XfyjService{

	private XfyjDao xfyjDao;

	@Override
	public List<XfyjEntity> getList() {
		return xfyjDao.getList();
	}
	
	@Override
	public List<XfyjxqEntity> getDetails(XfyjxqQueryEntity query) {
		/*PageList<XfyjxqEntity> list = new PageList<XfyjxqEntity>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(xfyjDao.count(query));
			list.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				list.addAll(xfyjDao.getDetails(query));
			}
		}*/
		return xfyjDao.getDetails(query);
	}
	
	public XfyjDao getXfyjDao() {
		return xfyjDao;
	}

	public void setXfyjDao(XfyjDao xfyjDao) {
		this.xfyjDao = xfyjDao;
	}




}
