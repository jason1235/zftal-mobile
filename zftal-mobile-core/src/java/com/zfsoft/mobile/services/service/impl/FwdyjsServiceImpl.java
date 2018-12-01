package com.zfsoft.mobile.services.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.IJsglDao;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.myportal.entity.PortalRelativeRole;
import com.zfsoft.mobile.services.dao.daointerface.IFwdyjsDao;
import com.zfsoft.mobile.services.dao.query.FwdyjsQuery;
import com.zfsoft.mobile.services.entity.FwdyjsModel;
import com.zfsoft.mobile.services.service.IFwdyjsService;

public class FwdyjsServiceImpl  implements IFwdyjsService{

	private IFwdyjsDao fwdyjsDao;

	public void setFwdyjsDao(IFwdyjsDao fwdyjsDao) {
		this.fwdyjsDao = fwdyjsDao;
	}

	public IFwdyjsDao getFwdyjsDao() {
		return fwdyjsDao;
	}

	@Override
	public List<FwdyjsModel> getPagedList(FwdyjsModel query) {
		// TODO Auto-generated method stub
		/*PageList<FwdyjsModel> pageList = new PageList<FwdyjsModel>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(fwdyjsDao.getCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<FwdyjsModel> list = fwdyjsDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;*/
		return fwdyjsDao.getPagedList(query);
	}

	@Override
	public List<FwdyjsModel> getPagedListWeifp(FwdyjsModel query) {
		// TODO Auto-generated method stub
		/*PageList<FwdyjsModel> pageList = new PageList<FwdyjsModel>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(fwdyjsDao.getWfpCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<FwdyjsModel> list = fwdyjsDao.getWfpList(query);
				pageList.addAll(list);
			}
		}
		return pageList;*/
		return fwdyjsDao.getPagedListWfp(query);
	}

	/*@Override
	public void insert(FwdyjsQuery fwdyjsquery) {
		// TODO Auto-generated method stub
		fwdyjsDao.insert(fwdyjsquery);
		fwdyjsDao.insertYhqx(fwdyjsquery);
	}*/

	/*@Override
	public void delete(FwdyjsQuery fwdyjsquery) {
		// TODO Auto-generated method stub
		fwdyjsDao.delete(fwdyjsquery);
		fwdyjsDao.deleteYhqx(fwdyjsquery);
	}*/

	@Override
	public ArrayList<FwdyjsModel> getWeiModel(FwdyjsModel query) {
		// TODO Auto-generated method stub
		return fwdyjsDao.getWeiModel(query);
	}

	@Override
	public boolean insert(FwdyjsModel t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(FwdyjsModel t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FwdyjsModel getModel(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FwdyjsModel getModel(FwdyjsModel t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean batchDelete(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean batchDelete(List list) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean batchUpdate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<FwdyjsModel> getModelList(FwdyjsModel t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FwdyjsModel> getModelList(String... str) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getCount(FwdyjsModel t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<FwdyjsModel> getPagedByScope(FwdyjsModel t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FwdyjsModel> getModelListByScope(FwdyjsModel t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageList<FwdyjsModel> getPagedListWeifpNew(FwdyjsModel query) {
		PageList<FwdyjsModel> pageList = new PageList<FwdyjsModel>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(fwdyjsDao.getPagedListWeiCountNew(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<FwdyjsModel> list = fwdyjsDao.getPagedListWeifpNew(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insert(Map<String, Object> param) {
		fwdyjsDao.insert(param);
		fwdyjsDao.insertYhqx(param);
	}

	@Override
	public void delete(Map<String, Object> param) {
		fwdyjsDao.delete(param);
		fwdyjsDao.deleteYhqx(param);
	}

	

}
