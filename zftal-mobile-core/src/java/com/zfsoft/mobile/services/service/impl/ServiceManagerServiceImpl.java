package com.zfsoft.mobile.services.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.zfsoft.common.factory.SessionFactory;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.core.exception.RuleException;
import com.zfsoft.mobile.basedata.dao.daointerface.IBaseDataDao;
import com.zfsoft.mobile.common.enums.ServiceSourceEnum;
import com.zfsoft.mobile.common.enums.ServiceTypeEnum;
import com.zfsoft.mobile.news.dao.INewsCatalogDao;
import com.zfsoft.mobile.news.entity.NewsCatalog;
import com.zfsoft.mobile.news.query.NewsCatalogQuery;
import com.zfsoft.mobile.services.dao.daointerface.IFwdyjsDao;
import com.zfsoft.mobile.services.dao.daointerface.IServiceManagerDao;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.dao.query.FwdyjsQuery;
import com.zfsoft.mobile.services.dao.query.ServiceManagerQuery;
import com.zfsoft.mobile.services.entity.AppServiceEntity;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.FwdyjsModel;
import com.zfsoft.mobile.services.entity.ServiceManager;
import com.zfsoft.mobile.services.entity.VisitsServiceEntity;
import com.zfsoft.mobile.services.entity.fwbmVisitEntity;
import com.zfsoft.mobile.services.service.IServiceManagerService;

public class ServiceManagerServiceImpl implements IServiceManagerService{

	private IServiceManagerDao serviceManagerDao;
	
	private IFwdyjsDao fwdyjsDao;
	
	private INewsCatalogDao newsCatalogDao;
	
	private IBaseDataDao baseDataDao;

	public IFwdyjsDao getFwdyjsDao() {
		return fwdyjsDao;
	}

	public void setFwdyjsDao(IFwdyjsDao fwdyjsDao) {
		this.fwdyjsDao = fwdyjsDao;
	}

	public void setServiceManagerDao(IServiceManagerDao serviceManagerDao) {
		this.serviceManagerDao = serviceManagerDao;
	}

	public IServiceManagerDao getServiceManagerDao() {
		return serviceManagerDao;
	}

	@Override
	public PageList<ServiceManager> getList(ServiceManagerQuery serviceQuery) {
		PageList<ServiceManager> pageList = new PageList<ServiceManager>();
		Paginator paginator = new Paginator();
		if(serviceQuery!=null){
			paginator.setItemsPerPage(serviceQuery.getPerPageSize());
			paginator.setPage((Integer)serviceQuery.getToPage());
			paginator.setItems(serviceManagerDao.getListCount(serviceQuery));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				serviceQuery.setStartRow(paginator.getBeginIndex());
				serviceQuery.setEndRow(paginator.getEndIndex());
				List<ServiceManager> list = serviceManagerDao.getList(serviceQuery);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void remove(Map<String, Object> param) {
		// TODO Auto-generated method stub
		//model.setClassGxsj(new Date());
		//model.setClassGxzid(SessionFactory.getUser().getYhm());
		List<String> tids = (List<String>)param.get("ids");
		
		//如果是由信息类过来的，同步删除信息类服务管理表中的数据
		for(String id : tids){
			ServiceManagerQuery serviceQuery = new ServiceManagerQuery();
			serviceQuery.setClassId(id);
			ServiceManager serviceManager = this.getList(serviceQuery).get(0);
			if(serviceManager.getClassFwly().equals(ServiceSourceEnum.INFOCLASS_SOURCE.getKey())){
				baseDataDao.removePublish(serviceManager.getClassFwbm());
			}
		}
		
		serviceManagerDao.delete(param);
		
		/*String news = (String)param.get("news");
		if(StringUtils.isEmpty(news)){
			//同步删除资讯类别
			for(String id : tids){
				ServiceManagerQuery serviceQuery = new ServiceManagerQuery();
				serviceQuery.setClassId(id);
				ServiceManager serviceManager = this.getList(serviceQuery).get(0);
				if(serviceManager.getClassFwlx().equals(ServiceTypeEnum.NEWS_APPLICATION.getKey())){
					NewsCatalogQuery newsQuery = new NewsCatalogQuery();
					newsQuery.setCatalogCode(serviceManager.getClassFwbm());
					NewsCatalog newsCatalog = newsCatalogDao.findByName(newsQuery);
					newsQuery = new NewsCatalogQuery();
					newsQuery.setCatalogId(newsCatalog.getCatalogId());
					newsCatalogDao.delete(newsQuery);
				}
			}
		}*/
	}

	@Override
	public void fabu(Map<String, Object> param) {
		// TODO Auto-generated method stub
		serviceManagerDao.updateById(param);
	}

	@Override
	public void quxiao(Map<String, Object> param) {
		// TODO Auto-generated method stub
		serviceManagerDao.updateById(param);
	}

	@Override
	public void insert(ServiceManager model){
		// TODO Auto-generated method stub
		//check(model);
		int pxm = serviceManagerDao.getMaxPxm();
		model.setClassRdpx(pxm+1);
		//model.setClassFbzt("1");
		model.setClassCjsj(new Date());
		model.setClassCjzid(SessionFactory.getUser().getYhm());
		model.setClassDeleted("0");
		model.setClassShowway("1,2,3");
		serviceManagerDao.insert(model);
	}
	@Override
	public String check(ServiceManager model){
		// TODO Auto-generated method stub
		ServiceManagerQuery query = new ServiceManagerQuery();
		query.setClassDeleted("0");
		int count = 0;
		if(model != null && model.getClassFwbm() != null){
			query.setClassFwbm(model.getClassFwbm());
			count = serviceManagerDao.getListCount(query);
		}
		if(count > 0)
			return "服务编码相同的服务已存在";
		/*if(model != null && model.getClassFwmc() != null){
			query.setClassFwbm(null);
			query.setClassFwmc(model.getClassFwmc());
			count = serviceManagerDao.getListCount(query);
		}
		if(count > 0)
			return "存在相同的服务名称";*/
		return "success";
	}

	@Override
	public void update(ServiceManager model) {
		// TODO Auto-generated method stub
		if(model.getClassGxsj() == null){
			model.setClassGxsj(new Date());
			model.setClassGxzid(SessionFactory.getUser().getYhm()); 
			serviceManagerDao.update(model);
			
			/*if(model.getClassFwlx().equals(ServiceTypeEnum.NEWS_APPLICATION.getKey())){
				NewsCatalogQuery newsQuery = new NewsCatalogQuery();
				newsQuery.setCatalogCode(model.getClassFwbm());
				NewsCatalog newsCatalog = newsCatalogDao.findByName(newsQuery);
				newsCatalog.setCatalogName(model.getClassFwmc());
				newsCatalog.setLogoId(model.getClassFwtbid());
				newsCatalog.setLogoUrl(model.getClassFwtbdz());
				newsCatalog.setUpdater(SessionFactory.getUser().getYhm());
				newsCatalogDao.updateByService(newsCatalog);
			}*/
		}else{
			System.out.println("aaa");
			serviceManagerDao.update(model);//为资讯里面的同步更新操作，根据是否有更新时间来确定，在资讯里的同步修改中做了更新时间处理
			System.out.println("aaa");
		}
	}

	@Override
	public void up(ServiceManager model){
		// TODO Auto-generated method stub
		ServiceManagerQuery serviceQuery = new ServiceManagerQuery();
		serviceQuery.setClassId(model.getClassId());
		model = this.getList(serviceQuery).get(0);
		int pxm = model.getClassRdpx();
		serviceQuery = new ServiceManagerQuery();
		serviceQuery.setClassRdpx(pxm);
		ServiceManager upModel = new ServiceManager();
		ArrayList<ServiceManager> upList = serviceManagerDao.getUpList(serviceQuery);
		if(upList == null || (upList != null && upList.size() == 0))
			throw new RuleException("已经置顶，无法再上移！");
		upModel = upList.get(upList.size()-1);
		int upPxm = upModel.getClassRdpx();
		upModel.setClassRdpx(pxm);
		model.setClassRdpx(upPxm);
		serviceManagerDao.upOrDown(model);
        serviceManagerDao.upOrDown(upModel);
	}

	@Override
	public void down(ServiceManager model){
		// TODO Auto-generated method stub
		ServiceManagerQuery serviceQuery = new ServiceManagerQuery();
		serviceQuery.setClassId(model.getClassId());
		model = this.getList(serviceQuery).get(0);
		int pxm = model.getClassRdpx();
		serviceQuery = new ServiceManagerQuery();
		serviceQuery.setClassRdpx(pxm);
		ServiceManager downModel = new ServiceManager();
		ArrayList<ServiceManager> upList = serviceManagerDao.getDownList(serviceQuery);
		if(upList == null || (upList != null && upList.size() == 0))
			throw new RuleException("已经最后，无法再下移！");
		downModel = upList.get(0);
		int downPxm = downModel.getClassRdpx();
		downModel.setClassRdpx(pxm);
		model.setClassRdpx(downPxm);
		serviceManagerDao.upOrDown(model);
        serviceManagerDao.upOrDown(downModel);
	}

	@Override
	public void changeShow(ServiceManager model) {
		// TODO Auto-generated method stub
		serviceManagerDao.changeShow(model);
	}
	
	@Override
	public List<ServiceManager> getKyInfoClassService() {
		return serviceManagerDao.getKyInfoClassService();
	}

	public void setNewsCatalogDao(INewsCatalogDao newsCatalogDao) {
		this.newsCatalogDao = newsCatalogDao;
	}

	public INewsCatalogDao getNewsCatalogDao() {
		return newsCatalogDao;
	}

	public void setBaseDataDao(IBaseDataDao baseDataDao) {
		this.baseDataDao = baseDataDao;
	}

	public IBaseDataDao getBaseDataDao() {
		return baseDataDao;
	}

	@Override
	public void updateIndex(Map<String, String> map) {
		serviceManagerDao.updateIndex(map);
	}

	@Override
	public int getMaxWebFwbm() {
		return serviceManagerDao.getMaxWebFwbm();
	}

	@Override
	public List<fwbmVisitEntity> getVisitsByFwbm(fwbmVisitEntity entity) {
		return serviceManagerDao.getVisitsByFwbm(entity);
	}

	@Override
	public List<AppServiceEntity> getFwdyxt() {
		return serviceManagerDao.getFwdyxt();
	}

	@Override
	public List<AppServiceEntity> getAllFwbm() {
		return serviceManagerDao.getAllFwbm();
	}

	@Override
	public PageList<VisitsServiceEntity> getAllVSStatis(VisitsServiceEntity visitsQuery) {
		PageList<VisitsServiceEntity> pageList = new PageList<VisitsServiceEntity>();
		Paginator paginator = new Paginator();
		if(visitsQuery!=null){
			paginator.setItemsPerPage(visitsQuery.getPerPageSize());
			paginator.setPage((Integer)visitsQuery.getToPage());
			paginator.setItems(serviceManagerDao.getAllVSStatisCount(visitsQuery));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				visitsQuery.setStartRow(paginator.getBeginIndex());
				visitsQuery.setEndRow(paginator.getEndIndex());
				List<VisitsServiceEntity> list = serviceManagerDao.getAllVSStatis(visitsQuery);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.IServiceManagerService#recommendControl(java.util.Map)
	 */
	@Override
	public void updateRecommend(Map<String, Object> param) {
		serviceManagerDao.updateRecommend(param);
	}

}
