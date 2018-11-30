package com.zfsoft.mobile.portal.dao.daointerface;

import java.util.ArrayList;

import com.zfsoft.mobile.portal.entity.Card;
import com.zfsoft.mobile.portal.entity.bankCard;
import com.zfsoft.mobile.portal.entity.bookDetail;
import com.zfsoft.mobile.portal.entity.bookerDetail;
import com.zfsoft.mobile.portal.entity.businessFrom;

public interface IPortalDao {
	
	ArrayList<bookerDetail> getBooker(String classYrh);

	ArrayList<bookDetail> getAllBooks(bookDetail query);

	ArrayList<bookDetail> getAllWarnBooks(bookDetail query);

	ArrayList<bookDetail> getAllLateBooks(bookDetail query);

	ArrayList<bookDetail> getDetailBook(bookDetail query);

	Card getCardNumber(String yhm);

	ArrayList<bankCard> getBankCard(bankCard bankQuery);

	ArrayList<businessFrom> getBusinessFrom(businessFrom businessQuery);

	float getYueFromTeacher(String kh);

	float getYueFromStudent(String kh);
    
}
