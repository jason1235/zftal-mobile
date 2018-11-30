/**
 * 
 */
package com.zfsoft.mobile.servlet.myPortalHttp.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.file.entity.ImageDB;
import com.zfsoft.mobile.servlet.myPortalHttp.dao.daointerface.IMyPortalHttpDao;
import com.zfsoft.mobile.servlet.myPortalHttp.service.IMyPortalHttpService;
import com.zfsoft.mobile.webservices.entity.CardBusinessEntity;
import com.zfsoft.mobile.webservices.query.CardBusinessQuery;

/**
 * @author zhangxu
 *
 */
public class MyPortalHttpServiceImpl implements IMyPortalHttpService {
	
	private IMyPortalHttpDao myPortalHttpDao;

	public void setMyPortalHttpDao(IMyPortalHttpDao myPortalHttpDao) {
		this.myPortalHttpDao = myPortalHttpDao;
	}

	public IMyPortalHttpDao getMyPortalHttpDao() {
		return myPortalHttpDao;
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.myPortalHttp.service.IMyPortalHttpService#getMyPicture(java.lang.String)
	 */
	@Override
	public List<ImageDB> getMyPicture(String userId) {
		return myPortalHttpDao.getMyPicture(userId);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.myPortalHttp.service.IMyPortalHttpService#getCardKH(java.lang.String)
	 */
	@Override
	public String getCardKH(String username) {
		// TODO Auto-generated method stub
		return myPortalHttpDao.getCardKH(username);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.myPortalHttp.service.IMyPortalHttpService#getCardNumber(java.lang.String)
	 */
	@Override
	public float getCardNumber(String username) {
		// TODO Auto-generated method stub
		return myPortalHttpDao.getCardNumber(username);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.myPortalHttp.service.IMyPortalHttpService#getodetailCount(com.zfsoft.mobile.webservices.query.CardBusinessQuery)
	 */
	@Override
	public int getodetailCount(CardBusinessQuery businessQuery) {
		// TODO Auto-generated method stub
		return myPortalHttpDao.getodetailCount(businessQuery);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.myPortalHttp.service.IMyPortalHttpService#getodetailList(com.zfsoft.mobile.webservices.query.CardBusinessQuery)
	 */
	@Override
	public PageList<CardBusinessEntity> getodetailList(
			CardBusinessQuery businessQuery) {
		// TODO Auto-generated method stub
		PageList<CardBusinessEntity> pageList = new PageList<CardBusinessEntity>();
		Paginator paginator = new Paginator();
		if(businessQuery!=null){
			paginator.setItemsPerPage(businessQuery.getPerPageSize());
			paginator.setPage((Integer)businessQuery.getToPage());
			paginator.setItems(myPortalHttpDao.getodetailCount(businessQuery));
			pageList.setPaginator(paginator);
			if((Integer)businessQuery.getToPage() > paginator.getPages()){
				return pageList;
			}
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				businessQuery.setStartRow(paginator.getBeginIndex());
				businessQuery.setEndRow(paginator.getEndIndex());
				List<CardBusinessEntity> list = myPortalHttpDao.getodetailList(businessQuery);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.myPortalHttp.service.IMyPortalHttpService#getcdetailCount(com.zfsoft.mobile.webservices.query.CardBusinessQuery)
	 */
	@Override
	public int getcdetailCount(CardBusinessQuery businessQuery) {
		// TODO Auto-generated method stub
		return myPortalHttpDao.getcdetailCount(businessQuery);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.myPortalHttp.service.IMyPortalHttpService#getcdetailList(com.zfsoft.mobile.webservices.query.CardBusinessQuery)
	 */
	@Override
	public PageList<CardBusinessEntity> getcdetailList(
			CardBusinessQuery businessQuery) {
		// TODO Auto-generated method stub
		PageList<CardBusinessEntity> pageList = new PageList<CardBusinessEntity>();
		Paginator paginator = new Paginator();
		if(businessQuery!=null){
			paginator.setItemsPerPage(businessQuery.getPerPageSize());
			paginator.setPage((Integer)businessQuery.getToPage());
			paginator.setItems(myPortalHttpDao.getcdetailCount(businessQuery));
			pageList.setPaginator(paginator);
			if((Integer)businessQuery.getToPage() > paginator.getPages()){
				return pageList;
			}
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				businessQuery.setStartRow(paginator.getBeginIndex());
				businessQuery.setEndRow(paginator.getEndIndex());
				List<CardBusinessEntity> list = myPortalHttpDao.getcdetailList(businessQuery);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.myPortalHttp.service.IMyPortalHttpService#updateMyPicture(com.zfsoft.hrm.file.entity.ImageDB)
	 */
	@Override
	public void updateMyPicture(ImageDB imageDB) {
		myPortalHttpDao.updateMyPicture(imageDB);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.servlet.myPortalHttp.service.IMyPortalHttpService#insertMyPicture(com.zfsoft.hrm.file.entity.ImageDB)
	 */
	@Override
	public void insertMyPicture(ImageDB imageDB) {
		myPortalHttpDao.insertMyPicture(imageDB);
	}

	@Override
	public Map<String, Object> getPortalSource(String userId) {
		// TODO Auto-generated method stub
		return myPortalHttpDao.getPortalSource(userId);
	}

}
