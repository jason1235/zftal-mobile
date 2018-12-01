package com.zfsoft.mobile.classCommunity.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zfsoft.mobile.classCommunity.entity.AnswerEntity;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerEntity;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerItemEntity;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerQuery;

/**
 * 抢答dao
 * @author H110MF
 *
 */
public interface IFirstAnswerDao {

	int getListCount(FirstAnswerQuery query);

	List<FirstAnswerEntity> getList(FirstAnswerQuery query);

	List<FirstAnswerItemEntity> getItemList(@Param("id")String id);

	int insertAnswer(FirstAnswerEntity firstAnswer);

	int insertAnswerItem(FirstAnswerItemEntity firstAnswerItem);

	int insertSubAnswer(AnswerEntity answerEntity);

	int checkAnswer(AnswerEntity answerEntity);
}
