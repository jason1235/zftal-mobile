package com.zfsoft.hrm.staffturn.retire.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.staffturn.retire.entities.RetireRule;
import com.zfsoft.service.base.BaseAnService;

public interface RetireRuleService extends BaseAnService<RetireRule>{
	
	/**
	 * 查询优先级更高的规则
	 * @param seq
	 * @return
	 */
	public List<RetireRule> findHigherRules(int seq);

}
