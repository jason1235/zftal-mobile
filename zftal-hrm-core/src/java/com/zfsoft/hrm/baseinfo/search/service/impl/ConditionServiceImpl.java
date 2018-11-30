package com.zfsoft.hrm.baseinfo.search.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.code.entities.Item;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;
import com.zfsoft.hrm.baseinfo.search.dao.daointerface.IConditionDao;
import com.zfsoft.hrm.baseinfo.search.entities.Condition;
import com.zfsoft.hrm.baseinfo.search.exception.SearchException;
import com.zfsoft.hrm.baseinfo.search.query.ConditionQuery;
import com.zfsoft.hrm.baseinfo.search.service.svcinterface.IConditionService;
import com.zfsoft.hrm.config.IConstants;
import com.zfsoft.hrm.core.exception.RuleException;

/**
 * {@link IConditionService}的实现
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-14
 * @version V1.0.0
 */
public class ConditionServiceImpl implements IConditionService {
	
	private IConditionDao dao;

	public void setDao(IConditionDao dao) {
		this.dao = dao;
	}

	@Override
	public void addItem(Condition entity) throws SearchException {
		
		/*
		 * 增加条件时，应先获取该条件隶属的条件系列下的条件数量，
		 * 并将得到的条件数据作为该条件的序号使用 
		 */
		ConditionQuery query = new ConditionQuery();
		query.setParentId( entity.getParentId() );
		
		int count = dao.count(query);
		entity.setIndex(count);
		
		dao.insert(entity);
	}

	@Override
	public void addCatalog(Condition entity) throws SearchException {

		entity.setParentId( IConstants.ROOT );
		entity.setExpress("");
		
		//新增条件系列与新增条件的业务一致，所有统一在增加条件中处理
		addItem(entity);
	}

	@Override
	public Condition getById(String guid) throws SearchException {

		return dao.findById(guid);
	}

	@Override
	public List<Condition> getCatalogList(ConditionQuery query)
			throws SearchException {

		query.setParentId( IConstants.ROOT );
		
		return dao.findList(query);
	}

	@Override
	public List<Condition> getItemList(ConditionQuery query)
			throws SearchException {

		return dao.findList(query);
	}

	@Override
	public void modifyCatalog(Condition entity) throws SearchException {

		//条件系列定义时，无需定义条件表达式
		entity.setExpress("");
		dao.update(entity);
	}

	@Override
	public void modifyItem(Condition entity) throws SearchException {

		dao.update(entity);
	}

	@Override
	public void removeItem(String guid) throws SearchException {
		ConditionQuery query = new ConditionQuery();
		query.setParentId(guid);
		List<Condition> list = dao.findList(query);
		if(list.size()>0)
			throw new RuleException("条件包含子节点，无法删除");
		dao.delete(guid);
	}

	@Override
	public List<Condition> getFullList(ConditionQuery query)
			throws SearchException {
		List<Condition> list = dao.findList(query);
		
		for (Condition condition : list) {
			ConditionQuery query1 = new ConditionQuery();
			
			query1.setParentId( condition.getGuid() );
			
			//递归遍历得到所有的数据
			condition.setChildren( getFullList( query1 ) );
		}
		
		return list;
	}

	@Override
	public void updateOrder(List<Condition> list) throws SearchException {
		for(Condition con:list){
			dao.update(con);
		}
	}

	@Override
	public PageList getPagingInfo(ConditionQuery query) {
		PageList pageList = new PageList();
		Paginator paginator = new Paginator();
		if(query!=null){
		try{
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(dao.findPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(dao.findPagingInfoList(query));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		}	
		return pageList;
	}
	
	public void saveBatch(String propertyName,Map<String,String> keyMap,Condition con){
		InfoClass clazz = InfoClassCache.getOverallInfoClass();
		InfoProperty p = clazz.getPropertyByName(propertyName);
		List<Condition> parentList = new ArrayList<Condition>();
		parentList.add(con);
		scanItem(p,null,keyMap,parentList);
	}
	
	private void scanItem(InfoProperty p,String parentId,Map<String,String> keyMap,List<Condition> parentList){
		List<Item> list = CodeUtil.getChildren(p.getCodeId(), parentId);
		for(Item i:list){
			if(keyMap.containsKey(i.getGuid())){
				Condition con = new Condition();
				con.setExpress(p.getFieldName() + " == '"+i.getGuid()+"'");
				con.setTitle(i.getDescription());
				con.setText(i.getDescription());
				Condition parent = parentList.get(parentList.size()-1);
				con.setParentId(parent.getGuid());
				con.setType(parent.getType());
				addItem(con);
				parentList.add(con);
			}
			scanItem(p,i.getGuid(),keyMap,parentList);
			if(keyMap.containsKey(i.getGuid())){
				parentList.remove(parentList.size()-1);
			}
		}
	}
	
	public void deleteBatch(Condition root){
		ConditionQuery query = new ConditionQuery();
		query.setParentId(root.getGuid());
		List<Condition> list = dao.findList(query);
		if(list.size()>0){
			for(Condition c: list){
				deleteBatch(c);
			}
		}
		if(!root.getParentId().equals(IConstants.ROOT)){
			dao.delete(root.getGuid());
		}
	}
}
