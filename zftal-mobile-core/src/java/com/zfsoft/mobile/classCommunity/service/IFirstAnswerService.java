package com.zfsoft.mobile.classCommunity.service;

import java.util.List;

import com.zfsoft.mobile.classCommunity.entity.AnswerEntity;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerEntity;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerItemEntity;
import com.zfsoft.mobile.classCommunity.entity.FirstAnswerQuery;

/**
 * 抢答service
 * @author H110MF
 *
 */
public interface IFirstAnswerService {

	List<FirstAnswerEntity> getList(FirstAnswerQuery firstAnswerQuery);

	List<FirstAnswerItemEntity> getItemList(String id);

	int insertAnswer(FirstAnswerEntity firstAnswer);

	int insertAnswerItem(FirstAnswerItemEntity firstAnswerItem);

	int insertSubAnswer(AnswerEntity answerEntity);
	
	int checkAnswer(AnswerEntity answerEntity);
}
