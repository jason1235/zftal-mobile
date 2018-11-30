package com.zfsoft.mobile.interfaceManager.service.impl;

import java.util.Date;
import java.util.List;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.mobile.interfaceManager.dao.daointerface.IInterfaceManagerDao;
import com.zfsoft.mobile.interfaceManager.dao.query.InterfaceManagerQuery;
import com.zfsoft.mobile.interfaceManager.entity.InterfaceManager;
import com.zfsoft.mobile.interfaceManager.service.IInterfaceManagerService;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.wsdl.WSDLParser;

public class InterfaceManagerServiceImpl implements IInterfaceManagerService{

	private IInterfaceManagerDao interfaceManagerDao;

	public void setInterfaceManagerDao(IInterfaceManagerDao interfaceManagerDao) {
		this.interfaceManagerDao = interfaceManagerDao;
	}

	public IInterfaceManagerDao getInterfaceManagerDao() {
		return interfaceManagerDao;
	}

	@Override
	public PageList<InterfaceManager> getList(InterfaceManagerQuery query) {
		// TODO Auto-generated method stub
		PageList<InterfaceManager> pageList = new PageList<InterfaceManager>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(interfaceManagerDao.getListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<InterfaceManager> list = interfaceManagerDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insert(InterfaceManager model) {
		// TODO Auto-generated method stub
		//check(model);
		
		WSDLParser paser = new WSDLParser();
		model.setClassJkdz(paser.getWebserviceUrl(model.getClassJkdz()));
		InterfaceManagerQuery interfaceQuery = new InterfaceManagerQuery();
		interfaceQuery.setClassJkbm(model.getClassJkbm());
		int count = interfaceManagerDao.getInsertList(interfaceQuery);
		if(count > 0)
			throw new RuleException("已经存在此接口编码的接口！");
		interfaceQuery = new InterfaceManagerQuery();
		interfaceQuery.setClassJkdz(model.getClassJkdz());
		count = interfaceManagerDao.getInsertList(interfaceQuery);
		if(count > 0)
			throw new RuleException("已经存在此接口地址的接口！");
		
		boolean result = true;
		try {
			result = paser.isCanPass(model.getClassJkdz());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("测试时出错！" + "错误信息为：" + e.getMessage());
			result = false;
		}
		model.setClassSyzt(result == true ? "1" : "0");
		model.setClassCjsj(new Date());
		model.setClassCjzid(SessionFactory.getUser().getYhm());
		model.setClassDeleted("0");
		interfaceManagerDao.insert(model);
	}

	@Override
	public void update(InterfaceManager model) {
		// TODO Auto-generated method stub
		WSDLParser paser = new WSDLParser();
		model.setClassJkdz(paser.getWebserviceUrl(model.getClassJkdz()));
		InterfaceManagerQuery interfaceQuery = new InterfaceManagerQuery();
		interfaceQuery.setClassJkid(model.getClassJkid());
		interfaceQuery.setClassJkbm(model.getClassJkbm());
		int count = interfaceManagerDao.getUpdateList(interfaceQuery);
		if(count > 0)
			throw new RuleException("已经存在此接口编码的接口！");
		interfaceQuery = new InterfaceManagerQuery();
		interfaceQuery.setClassJkid(model.getClassJkid());
		interfaceQuery.setClassJkdz(model.getClassJkdz());
		count = interfaceManagerDao.getUpdateList(interfaceQuery);
		if(count > 0)
			throw new RuleException("已经存在此接口地址的接口！");
		
		boolean result = true;
		try {
			result = paser.isCanPass(model.getClassJkdz());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("测试时出错！" + "错误信息为：" + e.getMessage());
			result = false;
		}
		model.setClassSyzt(result == true ? "1" : "0");
		
		model.setClassGxsj(new Date());
		model.setClassGxzid(SessionFactory.getUser().getYhm());
		interfaceManagerDao.update(model);
	}

	@Override
	public void remove(InterfaceManagerQuery query) {
		// TODO Auto-generated method stub
		query.setClassGxsj(new Date());
		query.setClassGxzid(SessionFactory.getUser().getYhm());
		interfaceManagerDao.delete(query);
	}

	@Override
	public void updateQiYong(InterfaceManagerQuery query) {
		// TODO Auto-generated method stub
		query.setClassSyzt("1");
		interfaceManagerDao.updateById(query);
	}

	@Override
	public void updateTingYong(InterfaceManagerQuery query) {
		// TODO Auto-generated method stub
		query.setClassSyzt("0");
		interfaceManagerDao.updateById(query);
	}
	
	@Override
	public InterfaceManager getInterfaceByBM(InterfaceManagerQuery query) {
		
		return interfaceManagerDao.getInterfaceByBM(query);
	}
}
