package com.zfsoft.mobile.services.service;

import java.util.List;
import java.util.Map;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.mobile.exampaper.query.TwoResultQuery;
import com.zfsoft.mobile.services.entity.ExamAnwserEntity;
import com.zfsoft.mobile.services.entity.ExamDyJsEntity;
import com.zfsoft.mobile.services.entity.ExamDyYhEntity;
import com.zfsoft.mobile.services.entity.ExamPaperEntity;
import com.zfsoft.mobile.services.entity.ExamQuestionEntity;
import com.zfsoft.mobile.services.entity.TwoExamAnwserEntity;
import com.zfsoft.mobile.services.entity.TwoExamDyJsEntity;
import com.zfsoft.mobile.services.entity.TwoExamDyYhEntity;
import com.zfsoft.mobile.services.entity.TwoExamPaperEntity;
import com.zfsoft.mobile.services.entity.TwoExamQuestionEntity;


public interface ITwoQuestionService {
	
	void insertAnswer(TwoExamDyYhEntity questionAnswer);
	
	PageList<TwoExamPaperEntity> getExamList(TwoExamPaperEntity query);

	PageList<TwoExamPaperEntity> getExamPaperList(TwoExamPaperEntity query);

	void insert(TwoExamPaperEntity model);

	void update(TwoExamPaperEntity model);

	void remove(Map<String, Object> param);

	List<TwoExamQuestionEntity> getQuestionList(TwoExamQuestionEntity questionQuery);

	void insertQuestion(TwoExamQuestionEntity questionModel);

	void updateQuestion(TwoExamQuestionEntity questionModel);

	String getMaxSort(String papermainid);

	void updateIndex(Map<String, String> map);

	List<TwoExamDyJsEntity> getExamDyJsList(TwoExamDyJsEntity entity);

	void insertExamDyJs(TwoExamDyJsEntity entity);

	void removeQuestion(TwoExamQuestionEntity questionQuery);

	Integer getValueStatic(Map<String, String> map);
	
	String getPapermainidByQes(String questionid);

	void insertExamYh(Map<String, String> map);

	void control(TwoExamPaperEntity query);

	void deleteExamDyJs(TwoExamDyJsEntity entity);

	/** 
	* @author: zhangxu
	* @Title: getExamAnwserList 
	* @Description: 查询当前时间的答题人对每道题的答案
	* @param @param examAnwserQuery
	* @param @return    设定文件 
	* @return PageList<ExamAnwserEntity>    返回类型 
	* @throws 
	*/
	PageList<TwoExamAnwserEntity> getExamAnwserList(
			TwoExamAnwserEntity examAnwserQuery);

	/** 
	* @author: zhangxu
	* @Title: getTotalScoreByQus 
	* @Description: 
	* @param @param model
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	String getTotalScoreByQus(TwoExamPaperEntity model);

	/** 
	* @author: zhangxu
	* @Title: getUserList 
	* @Description: 
	* @param @param twoResultQuery
	* @param @return    设定文件 
	* @return PageList<TwoResultQuery>    返回类型 
	* @throws 
	*/
	PageList<TwoResultQuery> getUserList(TwoResultQuery twoResultQuery);

}
