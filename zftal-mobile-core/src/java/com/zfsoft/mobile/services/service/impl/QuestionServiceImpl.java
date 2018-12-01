package com.zfsoft.mobile.services.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.dao.page.Paginator;
import com.zfsoft.mobile.services.dao.daointerface.IQuestionDao;
import com.zfsoft.mobile.services.entity.Business;
import com.zfsoft.mobile.services.entity.ExamAnwserEntity;
import com.zfsoft.mobile.services.entity.ExamDyJsEntity;
import com.zfsoft.mobile.services.entity.ExamDyYhEntity;
import com.zfsoft.mobile.services.entity.ExamPaperEntity;
import com.zfsoft.mobile.services.entity.ExamQuestionEntity;
import com.zfsoft.mobile.services.service.IQuestionService;

public class QuestionServiceImpl implements IQuestionService{

	private IQuestionDao questionDao;

	public void setQuestionDao(IQuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public IQuestionDao getQuestionDao() {
		return questionDao;
	}

	@Override
	public PageList<ExamPaperEntity> getExamPaperList(ExamPaperEntity query) {
		PageList<ExamPaperEntity> pageList = new PageList<ExamPaperEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(questionDao.getExamPaperCount(query));
			pageList.setPaginator(paginator);
			//if((Integer)query.getToPage() > paginator.getPages())
			//	return pageList;
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ExamPaperEntity> list = questionDao.getExamPaperList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insert(ExamPaperEntity model) {
		questionDao.insert(model);
	}

	@Override
	public void update(ExamPaperEntity model) {
		questionDao.update(model);
	}

	@Override
	public void remove(Map<String, Object> param) {
		questionDao.delete(param);
	}

	@Override
	public List<ExamQuestionEntity> getQuestionList(ExamQuestionEntity questionQuery) {
		return questionDao.getQuestionList(questionQuery);
	}

	@Override
	public void insertQuestion(ExamQuestionEntity questionModel) {
		questionDao.insertQuestion(questionModel);
	}

	@Override
	public void updateQuestion(ExamQuestionEntity questionModel) {
		questionDao.updateQuestion(questionModel);
	}

	@Override
	public String getMaxSort(String papermainid) {
		return questionDao.getMaxSort(papermainid);
	}

	@Override
	public void updateIndex(Map<String, String> map) {
		questionDao.updateIndex(map);
	}

	@Override
	public List<ExamDyJsEntity> getExamDyJsList(ExamDyJsEntity entity) {
		return questionDao.getExamDyJsList(entity);
	}

	@Override
	public void insertExamDyJs(ExamDyJsEntity entity) {
		questionDao.insertExamDyJs(entity);
	}

	@Override
	public void removeQuestion(ExamQuestionEntity questionQuery) {
		questionDao.removeQuestion(questionQuery);
	}

	@Override
	public Integer getValueStatic(Map<String, String> map) {
		return questionDao.getValueStatic(map);
	}

	@Override
	public PageList<ExamPaperEntity> getExamList(ExamPaperEntity query) {
		PageList<ExamPaperEntity> pageList = new PageList<ExamPaperEntity>();
		Paginator paginator = new Paginator();
		if(query!=null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(questionDao.getExamListCount(query));
			pageList.setPaginator(paginator);
			if((Integer)query.getToPage() > paginator.getPages())
				return pageList;
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ExamPaperEntity> list = questionDao.getExamList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}

	@Override
	public void insertAnswer(ExamDyYhEntity questionAnswer) {
		questionDao.insertAnswer(questionAnswer);
	}

	@Override
	public String getPapermainidByQes(String questionid) {
		return questionDao.getPapermainidByQes(questionid);
	}

	@Override
	public void insertExamYh(Map<String, String> map) {
		questionDao.insertExamYh(map);
	}

	@Override
	public void control(ExamPaperEntity query) {
		questionDao.control(query);
	}

	@Override
	public void deleteExamDyJs(ExamDyJsEntity entity) {
		questionDao.deleteExamDyJs(entity);
	}

	/* (non-Javadoc)
	 * @see com.zfsoft.mobile.services.service.IQuestionService#getExamAnwserList(com.zfsoft.mobile.services.entity.ExamAnwserEntity)
	 */
	@Override
	public PageList<ExamAnwserEntity> getExamAnwserList(
			ExamAnwserEntity query) {
		PageList<ExamAnwserEntity> pageList = new PageList<ExamAnwserEntity>();
		Paginator paginator = new Paginator();
		if(query != null){
			paginator.setItemsPerPage(query.getPerPageSize());
			paginator.setPage((Integer)query.getToPage());
			paginator.setItems(questionDao.getExamAnwserCount(query));
			pageList.setPaginator(paginator);
			//if((Integer)query.getToPage() > paginator.getPages())
			//	return pageList;
			if(paginator.getBeginIndex() <= paginator.getItems()){
				query.setStartRow(paginator.getBeginIndex());
				query.setEndRow(paginator.getEndIndex());
				List<ExamAnwserEntity> list = questionDao.getExamAnwserList(query);
				pageList.addAll(list);
			}
		}
		return pageList;
	}
}
