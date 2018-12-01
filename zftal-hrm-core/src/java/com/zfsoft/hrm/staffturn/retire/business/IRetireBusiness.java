package com.zfsoft.hrm.staffturn.retire.business;

import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;

public interface IRetireBusiness {
	/**
	 * 获取预退休人员通过工号
	 * @param query
	 * @return
	 */
	public RetireInfo getPreRetireByUserId(String userId);
	/**
	 * 获取退休人员通过工号
	 * @param userId
	 * @return
	 */
	public RetireInfo getRetireInfoByUserId(String userId) ;
	/**
	 * 获取退休人员通过工号和状态
	 * @param userId
	 * @return
	 */
	public RetireInfo getRetireInfoByUserIdAndState(String userId,String state) ;
	/**
	 * 将退休人员弄死
	 * @param userId
	 */
	public void updateStateToDead(String userId);
}
