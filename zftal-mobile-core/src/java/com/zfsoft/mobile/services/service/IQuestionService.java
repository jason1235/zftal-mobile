package com.zfsoft.mobile.services.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.services.entity.ExamAnwserEntity;
import com.zfsoft.mobile.services.entity.ExamDyJsEntity;
import com.zfsoft.mobile.services.entity.ExamDyYhEntity;
import com.zfsoft.mobile.services.entity.ExamPaperEntity;
import com.zfsoft.mobile.services.entity.ExamQuestionEntity;


public interface IQuestionService {
	
	void insertAnswer(ExamDyYhEntity questionAnswer);
	
	PageList<ExamPaperEntity> getExamList(ExamPaperEntity query);

	PageList<ExamPaperEntity> getExamPaperList(ExamPaperEntity query);

	void insert(ExamPaperEntity model);

	void update(ExamPaperEntity model);

	void remove(Map<String, Object> param);

	List<ExamQuestionEntity> getQuestionList(ExamQuestionEntity questionQuery);

	void insertQuestion(ExamQuestionEntity questionModel);

	void updateQuestion(ExamQuestionEntity questionModel);

	String getMaxSort(String papermainid);

	void updateIndex(Map<String, String> map);

	List<ExamDyJsEntity> getExamDyJsList(ExamDyJsEntity entity);

	void insertExamDyJs(ExamDyJsEntity entity);

	void removeQuestion(ExamQuestionEntity questionQuery);

	Integer getValueStatic(Map<String, String> map);
	
	String getPapermainidByQes(String questionid);

	void insertExamYh(Map<String, String> map);

	void control(ExamPaperEntity query);

	void deleteExamDyJs(ExamDyJsEntity entity);

	/** 
	* @author: zhangxu
	* @Title: getExamAnwserList 
	* @Description: 查询当前时间的答题人对每道题的答案
	* @param @param examAnwserQuery
	* @param @return    设定文件 
	* @return PageList<ExamAnwserEntity>    返回类型 
	* @throws 
	*/
	PageList<ExamAnwserEntity> getExamAnwserList(
			ExamAnwserEntity examAnwserQuery);

}
