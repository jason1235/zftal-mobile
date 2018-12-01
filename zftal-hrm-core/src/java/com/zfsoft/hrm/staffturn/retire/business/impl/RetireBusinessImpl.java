package com.zfsoft.hrm.staffturn.retire.business.impl;

import java.util.List;

import com.zfsoft.hrm.staffturn.retire.business.IRetireBusiness;
import com.zfsoft.hrm.staffturn.retire.dao.daointerface.IRetireDao;
import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;
import com.zfsoft.hrm.staffturn.retire.query.RetireInfoQuery;

public class RetireBusinessImpl implements IRetireBusiness {
	private IRetireDao retireDao;

	public void setRetireDao(IRetireDao retireDao) {
		this.retireDao = retireDao;
	}

	@Override
	public RetireInfo getPreRetireByUserId(String userId) {
		RetireInfoQuery query=new RetireInfoQuery();
		query.setQueryClass(RetireInfo.class);
		query.setUserId(userId);
		List<RetireInfo> retireInfos=retireDao.findByQuery(query);
		if(retireInfos.size()>0){
			return retireInfos.get(0);
		}
		return null;
	}

	@Override
	public RetireInfo getRetireInfoByUserId(String userId) {
		RetireInfoQuery query=new RetireInfoQuery();
		query.setQueryClass(RetireInfo.class);
		query.setUserId(userId);
		query.setState(1);
		List<RetireInfo> retireInfos=retireDao.findByQuery(query);
		
		if(retireInfos.size()>0){
			return retireInfos.get(0);
		}
		return null;
	}
	
	@Override
	public RetireInfo getRetireInfoByUserIdAndState(String userId,String state) {
		RetireInfoQuery query=new RetireInfoQuery();
		query.setQueryClass(RetireInfo.class);
		query.setUserId(userId);
		query.setState(2);
		List<RetireInfo> retireInfos=retireDao.findByQuery(query);
		
		if(retireInfos.size()>0){
			return retireInfos.get(0);
		}
		return null;
	}

	@Override
	public void updateStateToDead(String userId) {
		RetireInfoQuery query=new RetireInfoQuery();
		query.setQueryClass(RetireInfo.class);
		query.setUserId(userId);
		query.setState(1);
		List<RetireInfo> retireInfos=retireDao.findByQuery(query);
		if(retireInfos.size()>0){
			RetireInfo retireInfo=retireInfos.get(0);
			retireInfo.setState(2);
			retireDao.update(retireInfo);
		}
	}

		
}
