package com.zfsoft.mobile.qrcode.dao.daointerface;

import java.util.List;

import com.zfsoft.mobile.qrcode.entity.QRcode;

public interface IQRcodeDao {

	int getListCount(QRcode query);

	List<QRcode> getList(QRcode query);

	void insert(QRcode model);

	void update(QRcode model);

	void remove(QRcode query);

}
