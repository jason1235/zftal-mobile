package com.zfsoft.mobile.qrcode.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.qrcode.dao.daointerface.IQRcodeDao;
import com.zfsoft.mobile.qrcode.entity.QRcode;
import com.zfsoft.mobile.qrcode.service.IQRcodeService;
import com.zfsoft.mobile.services.entity.ServiceManager;

public class QRcodeServiceImpl implements IQRcodeService{

	private IQRcodeDao QRcodeDao;

	public void setQRcodeDao(IQRcodeDao qRcodeDao) {
		QRcodeDao = qRcodeDao;
	}

	public IQRcodeDao getQRcodeDao() {
		return QRcodeDao;
	}

	@Override
	public PageList<QRcode> getList(QRcode query) {
		PageList<QRcode> pageList = new PageList<QRcode>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(QRcodeDao.getListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<QRcode> list = QRcodeDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insert(QRcode model) {
		QRcodeDao.insert(model);
	}

	@Override
	public void update(QRcode model) {
		QRcodeDao.update(model);
	}

	@Override
	public void remove(QRcode query) {
		QRcodeDao.remove(query);
	}
}
