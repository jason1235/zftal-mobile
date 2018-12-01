package com.zfsoft.mobile.services.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.services.dao.daointerface.IBusinessDao;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.OauthYhDyXt;
import com.zfsoft.mobile.services.service.IBusinessService;

public class BusinessServiceImpl implements IBusinessService{

	private IBusinessDao businessDao;
	@Override
	public PageList<Business> getList(BusinessQuery query) {
		PageList<Business> pageList = new PageList<Business>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(businessDao.getListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<Business> list = businessDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
	public void setBusinessDao(IBusinessDao businessDao) {
		this.businessDao = businessDao;
	}
	public IBusinessDao getBusinessDao() {
		return businessDao;
	}
	@Override
	public void updateQiYong(BusinessQuery query) {
		// TODO Auto-generated method stub
		query.setClassSyzt("1");
		businessDao.updateById(query);
	}
	@Override
	public void remove(BusinessQuery query) {
		// TODO Auto-generated method stub
		query.setClassGxsj(new Date());
		query.setClassGxzid(SessionFactory.getUser().getYhm());
		businessDao.delete(query);
	}
	@Override
	public void updateTingYong(BusinessQuery query) {
		// TODO Auto-generated method stub
		query.setClassSyzt("0");
		businessDao.updateById(query);
	}
	@Override
	public void insert(Business model) throws Exception {
		// TODO Auto-generated method stub
		//check(model);
		int maxPxm = businessDao.getMaxPxm();
		model.setPxm(maxPxm+1);
		model.setClassSyzt("1");
		model.setClassCjsj(new Date());
		model.setClassCjzid(SessionFactory.getUser().getYhm());
		model.setClassDeleted("0");
		businessDao.insert(model);
	}
	
	@Override
	public void update(Business model) throws Exception {
		// TODO Auto-generated method stub
		//check(model);
		model.setClassGxsj(new Date());
		model.setClassGxzid(SessionFactory.getUser().getYhm());
		businessDao.update(model);
	}
	
	@Override
	public String check(Business model){
		// TODO Auto-generated method stub
		BusinessQuery query = new BusinessQuery();
		int count = 0;
		if(model != null && model.getClassXtbm() != null){
			query.setClassXtbm(model.getClassXtbm());
			count = businessDao.getListCount(query);
		}
		if(count > 0)
			return "存在相同的系统编码";
		
		return null;
		/*if(model != null && model.getClassXtmc() != null){
			query.setClassXtmc(model.getClassXtmc());
			count = businessDao.getListCount(query);
		}
		if(count > 0)
			throw new Exception("存在相同的系统名称");*/
	}
	@Override
	public String getProcodeBm(String classSsywxt) {
		return businessDao.getProcodeBm(classSsywxt);
	}
	@Override
	public void getYdht(BusinessQuery ydhtQuery) {
		// TODO Auto-generated method stub
		int count = businessDao.getListCount(ydhtQuery);
		if(count <= 0){
			Business model = new Business();
			model.setClassXtbm("ydmh");
			model.setClassXtdz("http:10.71.32.117:8080/zftal-mobile");
			model.setClassXtmc("移动门户");
			model.setClassSyzt("1");
			model.setClassCjsj(new Date());
			model.setClassCjzid(SessionFactory.getUser().getYhm());
			model.setClassDeleted("0");
			model.setProcode("999");
			businessDao.insert(model);
		}
	}
	@Override
	public List<OauthYhDyXt> getOauthYhDyXtList(OauthYhDyXt model) {
		return  businessDao.getOauthYhDyXtList(model);
	}
	@Override
	public void insertOauthYhDyXt(OauthYhDyXt model) {
		businessDao.insertOauthYhDyXt(model);
	}
	@Override
	public void updateOauthYhDyXt(OauthYhDyXt model) {
		businessDao.updateOauthYhDyXt(model);
	}
	@Override
	public int getOauthYhDyXtCount(OauthYhDyXt model) {
		return businessDao.getOauthYhDyXtCount(model);
	}
	@Override
	public void updateIndex(Map<String, String> map) {
		businessDao.updateIndex(map);
	}
	@Override
	public List<Business> getBusinessList(BusinessQuery businessQuery) {
		return businessDao.getBusinessList(businessQuery);
	}

}
