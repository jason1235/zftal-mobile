package com.zfsoft.mobile.classCommunity.service;

import com.zfsoft.mobile.classCommunity.entity.ClassSignInEntityForInsert;
import com.zfsoft.mobile.classCommunity.entity.ClassSignInRecordEntity;

/**
 * 课堂签到service
 * @author H110MF
 *
 */
public interface IClassSignInService {
	/**
	 * 新增签到课堂
	 * @return
	 */
	int insertClassSignIn(ClassSignInEntityForInsert classSignInEntityForInsert);
	
	/**
	 * 新增签到记录
	 * @return
	 */
	int insertClassSignInRecord(ClassSignInRecordEntity classSignInRecordEntity);
}
