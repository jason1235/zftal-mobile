package com.zfsoft.mobile.services.dao.daointerface;

import java.util.List;
import java.util.Map;

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

public interface ITwoQuestionDao {

	int getExamPaperCount(TwoExamPaperEntity query);

	List<TwoExamPaperEntity> getExamPaperList(TwoExamPaperEntity query);
	
	List<TwoExamPaperEntity> getExamList(TwoExamPaperEntity query);
	
	int getExamListCount(TwoExamPaperEntity query);

	void insert(TwoExamPaperEntity model);

	void update(TwoExamPaperEntity model);

	void delete(Map<String, Object> param);

	List<TwoExamQuestionEntity> getQuestionList(TwoExamQuestionEntity questionQuery);

	void insertQuestion(TwoExamQuestionEntity questionModel);
	
	void insertAnswer(TwoExamDyYhEntity questionAnswer);

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
	* @Title: getExamPaperCount 
	* @Description: 
	* @param @param query
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	int getExamAnwserCount(TwoExamAnwserEntity query);

	/** 
	* @author: zhangxu
	* @Title: getExamPaperList 
	* @Description: 
	* @param @param query
	* @param @return    设定文件 
	* @return List<ExamAnwserEntity>    返回类型 
	* @throws 
	*/
	List<TwoExamAnwserEntity> getExamAnwserList(TwoExamAnwserEntity query);

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
	* @Title: getUserListCount 
	* @Description: 
	* @param @param twoResultQuery
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	int getUserListCount(TwoResultQuery twoResultQuery);

	/** 
	* @author: zhangxu
	* @Title: getUserList 
	* @Description: 
	* @param @param twoResultQuery
	* @param @return    设定文件 
	* @return List<TwoResultQuery>    返回类型 
	* @throws 
	*/
	List<TwoResultQuery> getUserList(TwoResultQuery twoResultQuery);

}
