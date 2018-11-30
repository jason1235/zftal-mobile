package com.zfsoft.hrm.staffturn.retire.service.impl;

import java.util.List;

import com.zfsoft.hrm.staffturn.retire.dao.daointerface.RetireRuleDao;
import com.zfsoft.hrm.staffturn.retire.entities.RetireRule;
import com.zfsoft.hrm.staffturn.retire.service.svcinterface.RetireRuleService;
import com.zfsoft.service.base.BaseAnServiceImpl;

public class RetireRuleServiceImpl extends BaseAnServiceImpl<RetireRule> implements RetireRuleService{
	
	private RetireRuleDao dao;
	
	@Override
	public RetireRuleDao getDao() {
		return dao;
	}

	public void setDao(RetireRuleDao dao) {
		this.dao = dao;
	}

	@Override
	public List<RetireRule> findHigherRules(int seq) {
		RetireRule query=new RetireRule();
		query.setSeq(seq);
		return dao.findHigherRules(query);
	}

}
