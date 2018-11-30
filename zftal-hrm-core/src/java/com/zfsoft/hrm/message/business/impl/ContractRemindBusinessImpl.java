package com.zfsoft.hrm.message.business.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.zfsoft.hrm.message.business.IContractRemindBusiness;
import com.zfsoft.hrm.message.dao.daointerface.IContractRemindDao;

/** 
 * @author jinjj
 * @date 2012-9-25 下午04:19:28 
 *  
 */
public class ContractRemindBusinessImpl implements IContractRemindBusiness {

	private IContractRemindDao remindDao;
	
	@Override
	public List<Map<String, String>> getList(Date endDate) {
		
		return remindDao.getList(endDate);
	}

	public void setRemindDao(IContractRemindDao remindDao) {
		this.remindDao = remindDao;
	}

}
