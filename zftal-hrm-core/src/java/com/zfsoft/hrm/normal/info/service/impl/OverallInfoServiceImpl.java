package com.zfsoft.hrm.normal.info.service.impl;

import java.util.List;

import com.zfsoft.hrm.normal.info.dao.daointerface.IOverallInfoDao;
import com.zfsoft.hrm.normal.info.entity.OverallInfo;
import com.zfsoft.hrm.normal.info.service.svcinterface.IOverallInfoService;

/** 
 * 个人综合信息业务操作实现类
 * @ClassName: OverallInfoServiceImpl 
 * @author jinjj
 * @date 2012-6-18 上午09:45:36 
 *  
 */
public class OverallInfoServiceImpl implements IOverallInfoService {
	
	private IOverallInfoDao overallInfoDao;
	@Override
	public List<OverallInfo> userListThink(String searchStr) {
		OverallInfo query=new OverallInfo();
		query.setTerm(searchStr);
		return overallInfoDao.userListThink(query);
	}
	

	@Override
	public List<OverallInfo> userListThink(String searchStr, String expression) {
		OverallInfo query=new OverallInfo();
		query.setTerm(searchStr);
		query.setExpression(expression);
		return overallInfoDao.userListThink(query);
	}
	
	/**
	 * @param overallInfoDao the overallInfoDao to set
	 */
	public void setOverallInfoDao(IOverallInfoDao overallInfoDao) {
		this.overallInfoDao = overallInfoDao;
	}

}
