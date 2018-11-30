package com.zfsoft.mobile.classCommunity.dao;

import com.zfsoft.mobile.classCommunity.entity.ClassSignInEntityForInsert;
import com.zfsoft.mobile.classCommunity.entity.ClassSignInRecordEntity;

/**
 * 课堂签到dao
 * @author H110MF
 *
 */
public interface IClassSignInDao {

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
