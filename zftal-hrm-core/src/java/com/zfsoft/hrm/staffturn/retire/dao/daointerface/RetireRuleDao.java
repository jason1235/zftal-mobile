package com.zfsoft.hrm.staffturn.retire.dao.daointerface;

import java.util.List;

import com.zfsoft.dao.annotation.BaseAnDao;
import com.zfsoft.hrm.staffturn.retire.entities.RetireRule;

public interface RetireRuleDao extends BaseAnDao<RetireRule> {
	/**
	 * 查询优先级更高的规则
	 * @param query
	 * @return
	 */
	public List<RetireRule> findHigherRules(RetireRule query);
}
