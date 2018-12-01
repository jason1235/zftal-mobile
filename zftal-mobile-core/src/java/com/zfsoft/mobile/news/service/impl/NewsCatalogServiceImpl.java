package com.zfsoft.mobile.news.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.mobile.common.enums.ServiceSourceEnum;
import com.zfsoft.mobile.common.enums.ServiceTypeEnum;
import com.zfsoft.mobile.news.dao.INewsCatalogDao;
import com.zfsoft.mobile.news.entity.NewsCatalog;
import com.zfsoft.mobile.news.query.NewsCatalogQuery;
import com.zfsoft.mobile.news.service.INewsCatalogService;
import com.zfsoft.mobile.services.dao.query.ServiceManagerQuery;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.service.IServiceManagerService;

public class NewsCatalogServiceImpl implements INewsCatalogService {

	private INewsCatalogDao newsCatalogDao;
	private IServiceManagerService serviceManagerService;
	
	@Override
	public PageList<NewsCatalog> getPageList(NewsCatalogQuery query) {
		PageList<NewsCatalog> pageList = new PageList<NewsCatalog>();
		Paginator paginator = new Paginator();
		if (query != null) {
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage(query.getToPage());
			paginator.setItems(newsCatalogDao.getPageCount(query));
			pageList.setPaginator(paginator);
			
			if (paginator.getBeginIndex() <= paginator.getItems()) {
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(newsCatalogDao.getPageList(query));
			}
		}
		return pageList;
	}

	@Override
	public void doSave(NewsCatalogQuery query, String originalName) throws Exception {
		NewsCatalog newsCatalog = null;
		if (query.getCatalogId() == null || "".equals(query.getCatalogId())) {
			// 增加
			//query.setCatalogId(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			String maxPxm = newsCatalogDao.getMaxPxm();
			int pxm = 1;
			if (maxPxm != null) {
				pxm = Integer.parseInt(maxPxm) + 1;
			}
			query.setPxm(pxm);
			newsCatalog = newsCatalogDao.findByName(query);
			if (newsCatalog != null) {
				throw new RuleException("资讯类别已存在");	
			}
			newsCatalogDao.insert(query);
			newsCatalogDao.insertFwqx(query.getCatalogCode());
			
			//同时创建资讯类别服务
			/*ServiceManager model = new ServiceManager();
			model.setClassFwbm(query.getCatalogCode());
			model.setClassFwmc(query.getCatalogName());
			model.setClassFwlx(ServiceTypeEnum.NEWS_APPLICATION.getKey());
			model.setClassFwly(ServiceSourceEnum.CUSTOM_SOURCE.getKey());
			model.setClassFwtbdz(query.getLogoUrl());
			model.setClassFwtbid(query.getLogoId());
			serviceManagerService.insert(model);*/
			
		} else {
			// 修改
			if (!query.getCatalogName().equals(originalName)) {
				newsCatalog = newsCatalogDao.findByName(query);
				if (newsCatalog != null) {
					throw new RuleException("资讯类别已存在");
				}
			}
			newsCatalogDao.update(query);
			
			//同步更新服务中信息
			newsCatalog = newsCatalogDao.findById(query);
			ServiceManagerQuery modelQuery = new ServiceManagerQuery();
			modelQuery.setClassFwbm(newsCatalog.getCatalogCode());
			modelQuery.setClassDeleted("0");
			/*PageList<ServiceManager> list = serviceManagerService.getList(modelQuery);
			if (list != null && list.size() > 0) {
				ServiceManager model = list.get(0);
				model.setClassFwbm(query.getCatalogCode());
				model.setClassFwmc(query.getCatalogName());
				model.setClassFwlx(ServiceTypeEnum.NEWS_APPLICATION.getKey());
				//model.setClassFwly(ServiceSourceEnum.CUSTOM_SOURCE.getKey());
				model.setClassFwtbdz(query.getLogoUrl());
				model.setClassFwtbid(query.getLogoId());
				model.setClassGxsj(new Date());
				model.setClassGxzid(SessionFactory.getUser().getYhm());
				serviceManagerService.update(model);
			}*/
			
		}
	}

	@Override
	public void doUpdate(NewsCatalogQuery query) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void updateIndex(Map<String, String> map) throws Exception{
		newsCatalogDao.updateIndex(map);
	}

	@Override
	public void doDelete(NewsCatalogQuery query) throws Exception {
		int ct = newsCatalogDao.countNewsByCatalogId(query);
		if (ct > 0) {
			throw new RuleException("无法删除，类别下还存在资讯");
		}
		if ("0".equals(query.getSource())) {
			throw new RuleException("无法删除系统类别");
		}
		NewsCatalog newsCatalog =newsCatalogDao.findById(query);
		newsCatalogDao.delete(query);
		
		
		ServiceManagerQuery modelQuery = new ServiceManagerQuery();
		modelQuery.setClassFwbm(newsCatalog.getCatalogCode());
		PageList<ServiceManager> lst = serviceManagerService.getList(modelQuery);
		if (lst != null && lst.size() > 0) {
			ServiceManager model = lst.get(0);
			Map<String, Object> param = new HashMap<String, Object>();
	        param.put("yhid", SessionFactory.getUser().getYhm());
	        List<String> tids = new ArrayList<String>();
	        tids.add(model.getClassId());
	        param.put("ids", tids);
	        param.put("news", "news");
	        serviceManagerService.remove(param);
		}

	}

	@Override
	public NewsCatalog findById(NewsCatalogQuery query) {
		
		return newsCatalogDao.findById(query);
	}
	
	@Override
	public NewsCatalog findByName(NewsCatalogQuery query) {
		
		return newsCatalogDao.findByName(query);
	}
	
	@Override
	public List<NewsCatalog> getAllCatalog(Map<String, Object> param) {
		
		return newsCatalogDao.getAllCatalog(param);
	}

	@Override
	public void enable(NewsCatalogQuery query) throws Exception{
		newsCatalogDao.enable(query);

	}

	@Override
	public void disable(NewsCatalogQuery query) throws Exception{
		newsCatalogDao.disable(query);

	}
	
	@Override
	public int countEnable(NewsCatalogQuery query) {
		return newsCatalogDao.countEnable(query);
	}
	

	public INewsCatalogDao getNewsCatalogDao() {
		return newsCatalogDao;
	}

	public void setNewsCatalogDao(INewsCatalogDao newsCatalogDao) {
		this.newsCatalogDao = newsCatalogDao;
	}

	public void setServiceManagerService(IServiceManagerService serviceManagerService) {
		this.serviceManagerService = serviceManagerService;
	}

	public IServiceManagerService getServiceManagerService() {
		return serviceManagerService;
	}
	
	

}
