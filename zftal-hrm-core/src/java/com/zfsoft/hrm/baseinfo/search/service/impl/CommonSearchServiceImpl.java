package com.zfsoft.hrm.baseinfo.search.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zfsoft.dao.entities.IndexModel;
import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.dyna.entities.DynaBeanQuery;
import com.zfsoft.hrm.baseinfo.dyna.service.svcinterface.IDynaBeanService;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.search.constants.SearchConstants;
import com.zfsoft.hrm.baseinfo.search.dao.daointerface.ICommonSearchDao;
import com.zfsoft.hrm.baseinfo.search.entities.CommonSearch;
import com.zfsoft.hrm.baseinfo.search.entities.CommonSearchRelation;
import com.zfsoft.hrm.baseinfo.search.entities.Condition;
import com.zfsoft.hrm.baseinfo.search.exception.SearchException;
import com.zfsoft.hrm.baseinfo.search.query.CommonSearchQuery;
import com.zfsoft.hrm.baseinfo.search.query.ConditionQuery;
import com.zfsoft.hrm.baseinfo.search.service.svcinterface.ICommonSearchService;
import com.zfsoft.hrm.baseinfo.search.service.svcinterface.IConditionService;
import com.zfsoft.hrm.config.Direction;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.menu.business.IMenuBusiness;

/**
 * {@link ICommonSearchService}的实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-15
 * @version V1.0.0
 */
public class CommonSearchServiceImpl implements ICommonSearchService {
	
	Log log = LogFactory.getLog( CommonSearchServiceImpl.class );
	
	private ICommonSearchDao commonSearchDao;
	
	private IConditionService conditionService;
	private IDynaBeanService dynaBeanService;
	private IMenuBusiness menuBusiness;
	
	@Override
	public void addSearch(CommonSearch bean) throws SearchException {
		//新增菜单
		IndexModel model = new IndexModel();
		model.setFjgndm(IConstants.COMMON_SEARCH_ROOT_MENU);
		model.setGnmkmc(bean.getTitle());
		model.setDyym("");
		menuBusiness.addMenu(model);
		model.setDyym("/baseinfo/commonSearch_primary.html?guid="+model.getGnmkdm());
		menuBusiness.modify(model);
		//保存常用查询
		bean.setGuid(model.getGnmkdm());
		//保存常用查询系列关系
		commonSearchDao.insert(bean);
		for(Condition con : bean.getConditions()){
			CommonSearchRelation relation = new CommonSearchRelation();
			relation.setGuid(bean.getGuid());
			relation.setConditionId(con.getGuid());
			commonSearchDao.insertRelation(relation);
		}
	}

	@Override
	public CommonSearch getSearch(String guid) throws SearchException {
		CommonSearch entity = commonSearchDao.findById(guid);
		List<Condition> conditions = entity.getConditions();
		for(int i=0;i<conditions.size();i++){
			Condition con = conditions.get(i);
			con = conditionService.getById(con.getGuid());
			if(con != null){
				ConditionQuery cq = new ConditionQuery();
				cq.setParentId(con.getGuid());
				con.setChildren(conditionService.getFullList(cq));
				conditions.set(i, con);
			}
		}
		return entity;
	}

	@Override
	public List<CommonSearch> getSearchs(CommonSearchQuery query)
			throws SearchException {
		List<CommonSearch> list = commonSearchDao.findList(query);
		return list;
	}

	@Override
	public void modifySearch(CommonSearch bean) throws SearchException {
		//更新菜单名称
		IndexModel model = menuBusiness.getById(bean.getGuid());
		model.setGnmkmc(bean.getTitle());
		menuBusiness.modify(model);
		//更新常用查询
		commonSearchDao.update(bean);
		commonSearchDao.deleteRelations(bean.getGuid());
		for(Condition con : bean.getConditions()){
			CommonSearchRelation relation = new CommonSearchRelation();
			relation.setGuid(bean.getGuid());
			relation.setConditionId(con.getGuid());
			commonSearchDao.insertRelation(relation);
		}
	}

	@Override
	public void removeSearch(String guid) throws SearchException {
		//删除常用查询
		commonSearchDao.delete(guid);
		commonSearchDao.deleteRelations(guid);
		//删除菜单
		menuBusiness.remove(guid);
	}
	
	@Override
	public void deleteRelation(CommonSearchRelation relation)
			throws SearchException {
		commonSearchDao.deleteRelation(relation);
	}

	@Override
	public CommonSearch getCountData(String guid) throws SearchException {
		DynaBeanQuery query = new DynaBeanQuery( InfoClassCache.getOverallInfoClass() );
		query.setExpress( SearchConstants.STATE_IN_SERVICE_1 );
		CommonSearch entity = getSearch(guid);
		List<Condition> cons = entity.getConditions();
		for(int i=0;i<cons.size();i++){
			Condition con = cons.get(i);
			queryData(con,con.getExpress());
			cons.set(i, con);
		}
		entity.setConditions(cons);
		entity.setData( dynaBeanService.findCount(query) );//总数
		return entity;
	}
	
	private void queryData(Condition con,String rootExpress){
		DynaBeanQuery query = new DynaBeanQuery( InfoClassCache.getOverallInfoClass() );
		//String express = SearchUtil.siftByInService( con.getCondition() );
		StringBuilder express = new StringBuilder(con.getCondition());
//		if(!con.getParentId().equals(IConstants.ROOT)){
			if(!StringUtils.isEmpty(express.toString()) && !StringUtils.isEmpty(rootExpress)){
				express.append(" and ").append(rootExpress);
			}
//		}
		query.setExpress( express.toString() );
		
		int cnt = 0;
		
		if(con.getChildren().size()>0){
			for(int i=0;i<con.getChildren().size();i++){
				Condition child = con.getChildren().get(i);
				queryData(child,rootExpress);
				con.getChildren().set(i, child);
				cnt = dynaBeanService.findCount(query);
			}
		}else{
			cnt = dynaBeanService.findCount(query);
		}
		con.getResult().setProperty("count", String.valueOf(cnt));
	}
	
	@Override
	public void insertRelation(CommonSearchRelation relation)
			throws SearchException {
		commonSearchDao.insertRelation(relation);
	}

	public void setConditionService(IConditionService conditionService) {
		this.conditionService = conditionService;
	}

	public void setCommonSearchDao(ICommonSearchDao commonSearchDao) {
		this.commonSearchDao = commonSearchDao;
	}

	@Override
	public PageList getSearchsPagingInfo(CommonSearchQuery query)
			throws SearchException {

		PageList pageList = new PageList();
		Paginator paginator = new Paginator();
		if(query!=null){
		try{
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(commonSearchDao.findPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(commonSearchDao.findPagingInfoList(query));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		}	
		return pageList;
	
	}
	
	public List<Condition> getSubs(String parentGuid){
		ConditionQuery query = new ConditionQuery();
		query.setParentId(parentGuid);
		Condition root = getRoot(parentGuid);
		List<Condition> list = conditionService.getItemList(query);
		//DynaBeanQuery dquery = new DynaBeanQuery( InfoClassCache.getOverallInfoClass() );
		for(int i=0;i<list.size();i++){
			Condition sub = list.get(i);
			queryData(sub,root.getExpress());
			//int cnt = dynaBeanService.findCount(dquery);
			//sub.getResult().setProperty("count", String.valueOf(cnt));
			list.set(i, sub);
		}
		return list;
	}
	
	private Condition getRoot(String id){
		Condition con = conditionService.getById(id);
		if(con.getParentId().equals(IConstants.ROOT)){
			return con;
		}else{
			return getRoot(con.getParentId());
		}
	}
	
	public void doMenuMove(String menuId,Direction dirc){
		menuBusiness.menuMove(menuId,dirc);
	}

	public void setDynaBeanService(IDynaBeanService dynaBeanService) {
		this.dynaBeanService = dynaBeanService;
	}

	public void setMenuBusiness(IMenuBusiness menuBusiness) {
		this.menuBusiness = menuBusiness;
	}

}
