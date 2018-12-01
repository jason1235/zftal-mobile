package com.zfsoft.mobile.myportal.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.mobile.myportal.dao.IMyPortalDao;
import com.zfsoft.mobile.myportal.entity.MhTslbEntity;
import com.zfsoft.mobile.myportal.entity.MyPortal;
import com.zfsoft.mobile.myportal.query.MyPortalQuery;
import com.zfsoft.mobile.myportal.service.IMyPortalService;

public class MyPortalServiceImpl implements IMyPortalService {

	IMyPortalDao myPortalDao;
	
	public IMyPortalDao getMyPortalDao() {
		return myPortalDao;
	}

	public void setMyPortalDao(IMyPortalDao myPortalDao) {
		this.myPortalDao = myPortalDao;
	}

	@Override
	public PageList<MyPortal> getPageList(MyPortalQuery query) {
		PageList<MyPortal> pageList = new PageList<MyPortal>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(myPortalDao.getPageCount(query));
			pageList.setPaginator(paginator);
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(myPortalDao.getPageList(query));
			}
		}
		return pageList;
	}

	@Override
	public void doSave(MyPortalQuery query) throws Exception {
		if (query.getId() == null || "".equals(query.getId())) {
			query.setPxm((myPortalDao.getMaxPxm()+1) + "");
			MyPortal myPortal = myPortalDao.findByCode(query);
			if (myPortal == null) {
				myPortalDao.insert(query);
			} else {
				throw new RuleException("服务编码已存在");
			}
		} else {
			myPortalDao.update(query);
		}
	}

	@Override
	public MyPortal findById(MyPortalQuery query) {
		return myPortalDao.findById(query);
	}

	@Override
	public void doControl(MyPortalQuery query) throws Exception {
		myPortalDao.myPortalControl(query);
	}

	@Override
	public List<MyPortal> getAllMyPortal(String userName) {
		return myPortalDao.getAllMyPortal(userName);
	}
	
	@Override
	public void updateIndex(Map<String, String> map) throws Exception{
		myPortalDao.updateIndex(map);
	}

	@Override
	public int getMaxPxm() {
		return myPortalDao.getMaxPxm();
	}
	
	@Override
	public MyPortal findByCode(MyPortalQuery query) {
		return myPortalDao.findByCode(query);
	}

	@Override
	public int getMaxWebFwbm() {
		// TODO Auto-generated method stub
		return myPortalDao.getMaxWebFwbm();
	}
	@Override
	public void insertTslb(Map<String,Object> map){
		myPortalDao.insertTslb(map);
	}
	
	@Override
	public int getHaveTslbByLbmc(Map<String,Object> map){
		return myPortalDao.getHaveTslbByLbmc(map);
	}
	
	@Override
	public List<MhTslbEntity> getMhlbList(){
		return myPortalDao.getMhlbList();
	}
	
	@Override
	public List<String> getMhLbListInWdmh(){
		return myPortalDao.getMhLbListInWdmh();
	}
	
	@Override
	public List<MyPortal> getFwListByTsgn(Map<String,Object> params){
		return myPortalDao.getFwListByTsgn(params);
	}
	
	@Override
	public List<MyPortal> getAllMyFx(Map<String,Object> params){
		return myPortalDao.getAllMyFx(params);
	}
	
	@Override
	public List<String> getFxLbListInWdmh(){
		return myPortalDao.getFxLbListInWdmh();
	}
}
