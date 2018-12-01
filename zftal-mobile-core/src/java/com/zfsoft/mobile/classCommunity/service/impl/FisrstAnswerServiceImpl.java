package com.zfsoft.mobile.classCommunity.service.impl;

import java.util.List;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.classCommunity.dao.IFirstAnswerDao;
import com.zfsoft.mobile.classCommunity.entity.AnswerEntity;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerEntity;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerItemEntity;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerQuery;
import com.zfsoft.mobile.classCommunity.service.IFirstAnswerService;

/**
 * 抢答service实现
 * @author H110MF
 *
 */
public class FisrstAnswerServiceImpl implements IFirstAnswerService{

	private IFirstAnswerDao firstAnswerDao;
	
	@Override
	public List<FirstAnswerEntity> getList(FirstAnswerQuery query) {
		
		PageList<FirstAnswerEntity> pageList = new PageList<FirstAnswerEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(firstAnswerDao.getListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages()){
				return pageList;
			}
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<FirstAnswerEntity> list = firstAnswerDao.getList(query);
				pageList.addAll(list);
			} 
		}
		return pageList;
	}

	
	public IFirstAnswerDao getFirstAnswerDao() {
		return firstAnswerDao;
	}

	public void setFirstAnswerDao(IFirstAnswerDao firstAnswerDao) {
		this.firstAnswerDao = firstAnswerDao;
	}


	@Override
	public List<FirstAnswerItemEntity> getItemList(String id) {
		return firstAnswerDao.getItemList(id);
	}


	@Override
	public int insertAnswer(FirstAnswerEntity firstAnswer) {
		return firstAnswerDao.insertAnswer(firstAnswer);
	}


	@Override
	public int insertAnswerItem(FirstAnswerItemEntity firstAnswerItem) {
		return firstAnswerDao.insertAnswerItem(firstAnswerItem);
	}


	@Override
	public int insertSubAnswer(AnswerEntity answerEntity) {
		return firstAnswerDao.insertSubAnswer(answerEntity);
	}


	@Override
	public int checkAnswer(AnswerEntity answerEntity) {
		return firstAnswerDao.checkAnswer(answerEntity);
	}
}
