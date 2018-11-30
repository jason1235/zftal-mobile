package com.zfsoft.mobile.classCommunity.service.impl;

import com.zfsoft.mobile.classCommunity.dao.IClassSignInDao;
import com.zfsoft.mobile.classCommunity.entity.ClassSignInEntityForInsert;
import com.zfsoft.mobile.classCommunity.entity.ClassSignInRecordEntity;
import com.zfsoft.mobile.classCommunity.service.IClassSignInService;

/**
 * 课堂签到service实现
 * @author H110MF
 *
 */
public class ClassSignInServiceImpl implements IClassSignInService{
	private IClassSignInDao classSignInDao;
	
	/**
	 * 新增签到课堂
	 * @return
	 */
	public int insertClassSignIn(ClassSignInEntityForInsert classSignInEntityForInsert){
		return classSignInDao.insertClassSignIn(classSignInEntityForInsert);
	}
	
	/**
	 * 新增签到记录
	 * @return
	 */
	public int insertClassSignInRecord(ClassSignInRecordEntity classSignInRecordEntity){
		return classSignInDao.insertClassSignInRecord(classSignInRecordEntity);
	}

	public IClassSignInDao getClassSignInDao() {
		return classSignInDao;
	}

	public void setClassSignInDao(IClassSignInDao classSignInDao) {
		this.classSignInDao = classSignInDao;
	}
}
