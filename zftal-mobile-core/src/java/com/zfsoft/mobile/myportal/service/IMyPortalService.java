package com.zfsoft.mobile.myportal.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.myportal.entity.MhTslbEntity;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.myportal.query.MyPortalQuery;

public interface IMyPortalService {
	PageList<MyPortal> getPageList(MyPortalQuery query);
	void doSave(MyPortalQuery query) throws Exception;
	MyPortal findById(MyPortalQuery query);
	void doControl(MyPortalQuery query) throws Exception;
	List<MyPortal> getAllMyPortal(String userName);
	void updateIndex(Map<String,String> map) throws Exception;
	int getMaxPxm();
	MyPortal findByCode(MyPortalQuery query);
	int getMaxWebFwbm();
	void insertTslb(Map<String,Object> map);
	int getHaveTslbByLbmc(Map<String,Object> map);
	List<MhTslbEntity> getMhlbList();
	List<String> getMhLbListInWdmh();
	List<MyPortal> getFwListByTsgn(Map<String,Object> params);
	List<MyPortal> getAllMyFx(Map<String,Object> params);
	List<String> getFxLbListInWdmh();
}
