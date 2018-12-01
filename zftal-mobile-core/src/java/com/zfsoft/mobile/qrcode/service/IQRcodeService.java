package com.zfsoft.mobile.qrcode.service;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.qrcode.entity.QRcode;

public interface IQRcodeService {

	PageList<QRcode> getList(QRcode query);

	void insert(QRcode model);

	void update(QRcode model);

	void remove(QRcode query);

}
