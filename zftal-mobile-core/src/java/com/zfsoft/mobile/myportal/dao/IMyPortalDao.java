package com.zfsoft.mobile.myportal.dao;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.myportal.entity.MhTslbEntity;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.myportal.query.MyPortalQuery;

public interface IMyPortalDao {
	int getPageCount(MyPortalQuery query);
	List<MyPortal> getAllMyPortal(String userName);
	PageList<MyPortal> getPageList(MyPortalQuery query);
	void insert(MyPortalQuery query);
	MyPortal findById(MyPortalQuery query);
	void update(MyPortalQuery query);
	void myPortalControl(MyPortalQuery query);
	void updateIndex(Map<String,String> map);
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
