package com.zfsoft.hrm.staffturn.dead.business.impl;

import java.util.List;

import com.zfsoft.hrm.staffturn.dead.business.IDeadBusiness;
import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;
/**
 * 离世
 * @author 沈鲁威 Patrick Shen
 * @since 2012-8-7
 * @version V1.0.0
 */
public class DeadBusinessImpl implements IDeadBusiness {

	@Override
	public List<RetireInfo> getPreRetireInfoList(String deptCode, String name) {
		return null;
	}

	@Override
	public void saveRetire(RetireInfo retireInfo) {
		
	}

	@Override
	public void removeRetire(String userId) {
		
	}

	@Override
	public void dealPreRetireToRetire(RetireInfo retireInfo) {
		
	}

	@Override
	public List<RetireInfo> getRetireInfoList(RetireInfo retireInfo,
			String bothday, String sex) {
		return null;
	}
		
}
