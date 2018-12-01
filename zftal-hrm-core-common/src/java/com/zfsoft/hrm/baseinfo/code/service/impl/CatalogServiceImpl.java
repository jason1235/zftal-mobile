package com.zfsoft.hrm.baseinfo.code.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.hrm.baseinfo.code.dao.daointerface.ICatalogDao;
import com.zfsoft.hrm.baseinfo.code.dao.daointerface.IItemDao;
import com.zfsoft.hrm.baseinfo.code.entities.Catalog;
import com.zfsoft.hrm.baseinfo.code.query.CatalogQuery;
import com.zfsoft.hrm.baseinfo.code.service.svcinterface.ICatalogService;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.core.exception.RuleException;

/** 
 * @ClassName: CatalogServiceImpl 
 * @Description: 代码编目服务类
 * @author jinjj
 * @date 2012-5-18 上午11:52:01 
 *  
 */
public class CatalogServiceImpl implements ICatalogService {
	
	Log log = LogFactory.getLog(CatalogServiceImpl.class);
	
	private IItemDao itemDao;
	private ICatalogDao catalogDao;
	

	public int delete(Catalog model)throws RuntimeException{
		itemDao.deleteCatalogRealatedItems(model);//删除编目相关条目
		int cnt = catalogDao.delete(model.getGuid());//删除编目 外键关系：需要先删除条目
		CodeUtil.delCatalog(model);
		return cnt;
	}
	
	@Override
	public Catalog getEntity(Catalog entity) throws RuntimeException {
		return catalogDao.getEntity(entity);
	}

	@Override
	public List<Catalog> getList(CatalogQuery query) throws RuntimeException {
		return catalogDao.getList(query);
	}

	@Override
	public int insert(Catalog entity) throws RuntimeException {
		Catalog old = catalogDao.getEntity(entity);
		if(old != null){
			throw new RuleException("该编号数据已存在");
		}
		int count = catalogDao.insert(entity);
		CodeUtil.insertCatalog(entity);
		return count;
	}

	@Override
	public int update(Catalog entity) throws RuntimeException {
		int cnt = catalogDao.update(entity);
		entity=catalogDao.getEntity(entity);
		CodeUtil.updateCatalog(entity);
		return cnt;
	}
	
	@Override
	public int insertList(List<Catalog> list) throws RuntimeException {
		int cnt=0;
		for(Catalog catalog:list){
			cnt += catalogDao.insert(catalog);
		}
		for(Catalog catalog:list){
			CodeUtil.insertCatalog(catalog);
		}
		return cnt;
	}
	
	@SuppressWarnings("unchecked")
	public PageList searchPageList(CatalogQuery query)throws RuntimeException {
		PageList pageList = new PageList();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			
			paginator.setItems(catalogDao.findPagingCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				pageList.addAll(catalogDao.findPagingInfoList(query));
			}
		}	
		return pageList;
	}
	
	public Catalog getModel(Catalog model)throws RuntimeException{
		return catalogDao.getEntity(model);
	}
	
	public void setItemDao(IItemDao itemDao) {
		this.itemDao = itemDao;
	}

	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

}
