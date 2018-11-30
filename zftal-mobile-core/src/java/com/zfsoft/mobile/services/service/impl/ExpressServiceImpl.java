/**
 * 
 */
package com.zfsoft.mobile.services.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.services.dao.daointerface.IExpressDao;
import com.zfsoft.mobile.services.dao.query.BusinessQuery;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ExpressCommentEntity;
import com.zfsoft.mobile.services.entity.ExpressCommentEntityForApp;
import com.zfsoft.mobile.services.entity.ExpressEntity;
import com.zfsoft.mobile.services.entity.ExpressEntityForApp;
import com.zfsoft.mobile.services.entity.ExpressPicEntity;
import com.zfsoft.mobile.services.service.IExpressService;

/**
 * @author zhangxu
 * @description 
 * @date 2017-12-21 下午02:08:26
 */
public class ExpressServiceImpl implements IExpressService {
	
	private IExpressDao expressDao;

	@Override
	public PageList<ExpressEntityForApp> getList(ExpressEntity query) {
		PageList<ExpressEntityForApp> pageList = new PageList<ExpressEntityForApp>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(expressDao.getListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ExpressEntityForApp> list = expressDao.getList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	public void setExpressDao(IExpressDao expressDao) {
		this.expressDao = expressDao;
	}

	public IExpressDao getExpressDao() {
		return expressDao;
	}

	@Override
	public List<ExpressPicEntity> getExpressPicById(String expressId) {
		return expressDao.getExpressPicById(expressId);
	}

	@Override
	public List<ExpressCommentEntityForApp> getCommentList(ExpressCommentEntity query) {
		PageList<ExpressCommentEntityForApp> pageList = new PageList<ExpressCommentEntityForApp>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(expressDao.getCommentListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ExpressCommentEntityForApp> list = expressDao.getCommentList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insertExpress(ExpressEntity expressEntity) {
		expressDao.insertExpress(expressEntity);
	}

	@Override
	public void insertExpressPic(ExpressPicEntity picEntity) {
		expressDao.insertExpressPic(picEntity);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.IExpressService#updateGoodFlag(com.zfsoft.mobile.services.entity.ExpressCommentEntity)
	 */
	@Override
	public void updateGoodFlag(ExpressCommentEntity query) {
		expressDao.updateGoodFlag(query);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.IExpressService#insertComment(com.zfsoft.mobile.services.entity.ExpressCommentEntity)
	 */
	@Override
	public void insertComment(ExpressCommentEntity query) {
		expressDao.insertComment(query);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.IExpressService#getMaxOrderNumber()
	 */
	@Override
	public int getMaxOrderNumber(String expressId) {
		return expressDao.getMaxOrderNumber(expressId);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.IExpressService#getPageList(com.zfsoft.mobile.services.entity.ExpressEntity)
	 */
	@Override
	public PageList<ExpressEntity> getPageList(ExpressEntity query) {
		PageList<ExpressEntity> pageList = new PageList<ExpressEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(expressDao.getPageListCount(query));
			pageList.setPaginator(paginator);
			
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ExpressEntity> list = expressDao.getPageList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.IExpressService#delete(java.util.Map)
	 */
	@Override
	public void delete(Map<String, Object> param) {
		expressDao.deleteExpressComment(param);
		expressDao.deleteExpressPic(param);
		expressDao.delete(param);
	}
	
	/**
	 * 
	* @author: liucb
	* @Title: updateGoodCount 
	* @Description: 点赞次数加一
	* @param @param params    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void updateGoodCount(Map<String,Object> params){
		expressDao.updateGoodCount(params);
	}
	
	/**
	 * 
	* @author: liucb
	* @Title: checkUserGoodFlag 
	* @Description: 判断某个用户是否针对某项评论点赞
	* @param @param params
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public int checkUserGoodFlag(Map<String,Object> params){
		int rows = expressDao.checkUserGoodFlag(params);
		return rows;
	}
	
	
	/**
	 * 
	* @author: liucb
	* @Title: insertExpressGoodFlag 
	* @Description: 添加某个用户是否针对某项评论点赞
	* @param @param params    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void insertExpressGoodFlag(Map<String,Object> params){
		expressDao.insertExpressGoodFlag(params);
	}
}
