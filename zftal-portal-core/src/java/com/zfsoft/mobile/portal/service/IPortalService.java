package com.zfsoft.mobile.portal.service;

import java.util.ArrayList;

import com.zfsoft.mobile.portal.entity.Card;
import com.zfsoft.mobile.portal.entity.bankCard;
import com.zfsoft.mobile.portal.entity.bookDetail;
import com.zfsoft.mobile.portal.entity.bookerDetail;

public interface IPortalService {

	ArrayList<bookDetail> getAllBooks(bookDetail query);

	ArrayList<bookDetail> getAllWarnBooks(bookDetail query);

	ArrayList<bookDetail> getAllLateBooks(bookDetail query);

	ArrayList<bookerDetail> getBooker(String yhm);

	ArrayList<bookDetail> getDetailBook(bookDetail query);

	Card getCardNumber(String yhm);

	ArrayList<bankCard> getBankCard(bankCard bankQuery);

	ArrayList<com.zfsoft.mobile.portal.entity.businessFrom> getBusinessFrom(
			com.zfsoft.mobile.portal.entity.businessFrom businessQuery);

	float getYue(String kh);

}
