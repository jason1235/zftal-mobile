package com.zfsoft.hrm.message.service.svcinterface;

import com.zfsoft.hrm.message.config.IMessageJobDefine;

/** 
 * @author jinjj
 * @date 2012-9-25 下午04:21:32 
 *  
 */
public interface IContractRemindService {

	/**
	 * 合同提醒
	 * @param job
	 */
	public void doJob(IMessageJobDefine job);
}
