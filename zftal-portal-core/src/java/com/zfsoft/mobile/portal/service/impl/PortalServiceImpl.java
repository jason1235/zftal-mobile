package com.zfsoft.mobile.portal.service.impl;

import java.util.ArrayList;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.common.log.User;
import com.zfsoft.mobile.portal.dao.daointerface.IPortalDao;
import com.zfsoft.mobile.portal.entity.Card;
import com.zfsoft.mobile.portal.entity.bankCard;
import com.zfsoft.mobile.portal.entity.bookDetail;
import com.zfsoft.mobile.portal.entity.bookerDetail;
import com.zfsoft.mobile.portal.entity.businessFrom;
import com.zfsoft.mobile.portal.service.IPortalService;

public class PortalServiceImpl implements IPortalService{
	
	private IPortalDao portalDao;

	public void setPortalDao(IPortalDao portalDao) {
		this.portalDao = portalDao;
	}

	public IPortalDao getPortalDao() {
		return portalDao;
	}

	@Override
	public ArrayList<bookDetail> getAllBooks(bookDetail query) {
		// TODO Auto-generated method stub
		return portalDao.getAllBooks(query);
	}

	@Override
	public ArrayList<bookDetail> getAllWarnBooks(bookDetail query) {
		// TODO Auto-generated method stub
		return portalDao.getAllWarnBooks(query);
	}

	@Override
	public ArrayList<bookDetail> getAllLateBooks(bookDetail query) {
		// TODO Auto-generated method stub
		return portalDao.getAllLateBooks(query);
	}

	@Override
	public ArrayList<bookerDetail> getBooker(String yhm) {
		// TODO Auto-generated method stub
		return portalDao.getBooker(yhm);
	}

	@Override
	public ArrayList<bookDetail> getDetailBook(bookDetail query) {
		// TODO Auto-generated method stub
		return portalDao.getDetailBook(query);
	}

	@Override
	public Card getCardNumber(String yhm) {
		// TODO Auto-generated method stub
		return portalDao.getCardNumber(yhm);
	}

	@Override
	public ArrayList<bankCard> getBankCard(bankCard bankQuery) {
		// TODO Auto-generated method stub
		return portalDao.getBankCard(bankQuery);
	}

	@Override
	public ArrayList<businessFrom> getBusinessFrom(businessFrom businessQuery) {
		// TODO Auto-generated method stub
		return portalDao.getBusinessFrom(businessQuery);
	}

	@Override
	public float getYue(String kh) {
		// TODO Auto-generated method stub
		User user = SessionFactory.getUser();
		if(user.getYhlx().equals("teacher"))
			return portalDao.getYueFromTeacher(kh);
		else
			return portalDao.getYueFromStudent(kh);
	}

}
