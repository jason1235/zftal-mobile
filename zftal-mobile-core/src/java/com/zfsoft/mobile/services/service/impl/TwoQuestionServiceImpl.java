package com.zfsoft.mobile.services.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.exampaper.query.TwoResultQuery;
import com.zfsoft.mobile.services.dao.daointerface.ITwoQuestionDao;
import com.zfsoft.mobile.services.entity.TwoExamAnwserEntity;
import com.zfsoft.mobile.services.entity.TwoExamDyJsEntity;
import com.zfsoft.mobile.services.entity.TwoExamDyYhEntity;
import com.zfsoft.mobile.services.entity.TwoExamPaperEntity;
import com.zfsoft.mobile.services.entity.TwoExamQuestionEntity;
import com.zfsoft.mobile.services.service.ITwoQuestionService;

public class TwoQuestionServiceImpl implements ITwoQuestionService{

	private ITwoQuestionDao twoQuestionDao;

	public ITwoQuestionDao getTwoQuestionDao() {
		return twoQuestionDao;
	}

	public void setTwoQuestionDao(ITwoQuestionDao twoQuestionDao) {
		this.twoQuestionDao = twoQuestionDao;
	}

	@Override
	public PageList<TwoExamPaperEntity> getExamPaperList(TwoExamPaperEntity query) {
		PageList<TwoExamPaperEntity> pageList = new PageList<TwoExamPaperEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(twoQuestionDao.getExamPaperCount(query));
			pageList.setPaginator(paginator);
			//if((Integer)query.getToPage() > paginator.getPages())
			//	return pageList;
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<TwoExamPaperEntity> list = twoQuestionDao.getExamPaperList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insert(TwoExamPaperEntity model) {
		twoQuestionDao.insert(model);
	}

	@Override
	public void update(TwoExamPaperEntity model) {
		twoQuestionDao.update(model);
	}

	@Override
	public void remove(Map<String, Object> param) {
		twoQuestionDao.delete(param);
	}

	@Override
	public List<TwoExamQuestionEntity> getQuestionList(TwoExamQuestionEntity questionQuery) {
		return twoQuestionDao.getQuestionList(questionQuery);
	}

	@Override
	public void insertQuestion(TwoExamQuestionEntity questionModel) {
		twoQuestionDao.insertQuestion(questionModel);
	}

	@Override
	public void updateQuestion(TwoExamQuestionEntity questionModel) {
		twoQuestionDao.updateQuestion(questionModel);
	}

	@Override
	public String getMaxSort(String papermainid) {
		return twoQuestionDao.getMaxSort(papermainid);
	}

	@Override
	public void updateIndex(Map<String, String> map) {
		twoQuestionDao.updateIndex(map);
	}

	@Override
	public List<TwoExamDyJsEntity> getExamDyJsList(TwoExamDyJsEntity entity) {
		return twoQuestionDao.getExamDyJsList(entity);
	}

	@Override
	public void insertExamDyJs(TwoExamDyJsEntity entity) {
		twoQuestionDao.insertExamDyJs(entity);
	}

	@Override
	public void removeQuestion(TwoExamQuestionEntity questionQuery) {
		twoQuestionDao.removeQuestion(questionQuery);
	}

	@Override
	public Integer getValueStatic(Map<String, String> map) {
		return twoQuestionDao.getValueStatic(map);
	}

	@Override
	public PageList<TwoExamPaperEntity> getExamList(TwoExamPaperEntity query) {
		PageList<TwoExamPaperEntity> pageList = new PageList<TwoExamPaperEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(twoQuestionDao.getExamListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages())
				return pageList;
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<TwoExamPaperEntity> list = twoQuestionDao.getExamList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insertAnswer(TwoExamDyYhEntity questionAnswer) {
		twoQuestionDao.insertAnswer(questionAnswer);
	}

	@Override
	public String getPapermainidByQes(String questionid) {
		return twoQuestionDao.getPapermainidByQes(questionid);
	}

	@Override
	public void insertExamYh(Map<String, String> map) {
		twoQuestionDao.insertExamYh(map);
	}

	@Override
	public void control(TwoExamPaperEntity query) {
		twoQuestionDao.control(query);
	}

	@Override
	public void deleteExamDyJs(TwoExamDyJsEntity entity) {
		twoQuestionDao.deleteExamDyJs(entity);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.IQuestionService#getExamAnwserList(com.zfsoft.mobile.services.entity.ExamAnwserEntity)
	 */
	@Override
	public PageList<TwoExamAnwserEntity> getExamAnwserList(
			TwoExamAnwserEntity query) {
		PageList<TwoExamAnwserEntity> pageList = new PageList<TwoExamAnwserEntity>();
		Paginator paginator = new Paginator();
		if(query != null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(twoQuestionDao.getExamAnwserCount(query));
			pageList.setPaginator(paginator);
			//if((Integer)query.getToPage() > paginator.getPages())
			//	return pageList;
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<TwoExamAnwserEntity> list = twoQuestionDao.getExamAnwserList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.ITwoQuestionService#getTotalScoreByQus(com.zfsoft.mobile.services.entity.TwoExamPaperEntity)
	 */
	@Override
	public String getTotalScoreByQus(TwoExamPaperEntity model) {
		return twoQuestionDao.getTotalScoreByQus(model);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.ITwoQuestionService#getUserList(com.zfsoft.mobile.exampaper.query.TwoResultQuery)
	 */
	@Override
	public PageList<TwoResultQuery> getUserList(TwoResultQuery twoResultQuery) {
		PageList<TwoResultQuery> pageList = new PageList<TwoResultQuery>();
		Paginator paginator = new Paginator();
		if(twoResultQuery!=null){
			paginator.setItemsPerPage(twoResultQuery.getPerPageSize());
			paginator.setPage((Integer)twoResultQuery.getToPage());
			paginator.setItems(twoQuestionDao.getUserListCount(twoResultQuery));
			pageList.setPaginator(paginator);
			//if((Integer)query.getToPage() > paginator.getPages())
			//	return pageList;
			if(paginator.getBeginIndex() <= paginator.getItems()){
				twoResultQuery.setStartRow(paginator.getBeginIndex());
				twoResultQuery.setEndRow(paginator.getEndIndex());
				List<TwoResultQuery> list = twoQuestionDao.getUserList(twoResultQuery);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
}
