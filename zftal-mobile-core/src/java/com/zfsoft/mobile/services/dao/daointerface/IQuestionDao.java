package com.zfsoft.mobile.services.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.mobile.services.entity.ExamAnwserEntity;
import com.zfsoft.mobile.services.entity.ExamDyJsEntity;
import com.zfsoft.mobile.services.entity.ExamDyYhEntity;
import com.zfsoft.mobile.services.entity.ExamPaperEntity;
import com.zfsoft.mobile.services.entity.ExamQuestionEntity;

public interface IQuestionDao {

	int getExamPaperCount(ExamPaperEntity query);

	List<ExamPaperEntity> getExamPaperList(ExamPaperEntity query);
	
	List<ExamPaperEntity> getExamList(ExamPaperEntity query);
	
	int getExamListCount(ExamPaperEntity query);

	void insert(ExamPaperEntity model);

	void update(ExamPaperEntity model);

	void delete(Map<String, Object> param);

	List<ExamQuestionEntity> getQuestionList(ExamQuestionEntity questionQuery);

	void insertQuestion(ExamQuestionEntity questionModel);
	
	void insertAnswer(ExamDyYhEntity questionAnswer);

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
	* @Title: getExamPaperCount 
	* @Description: 
	* @param @param query
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	int getExamAnwserCount(ExamAnwserEntity query);

	/** 
	* @author: zhangxu
	* @Title: getExamPaperList 
	* @Description: 
	* @param @param query
	* @param @return    设定文件 
	* @return List<ExamAnwserEntity>    返回类型 
	* @throws 
	*/
	List<ExamAnwserEntity> getExamAnwserList(ExamAnwserEntity query);

}
