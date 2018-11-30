package com.zfsoft.hrm.message.service.test;

import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.hrm.message.config.ContractRemind;
import com.zfsoft.hrm.message.config.IMessageJobDefine;
import com.zfsoft.hrm.message.service.svcinterface.IMessageJobService;

/** 
 * @author jinjj
 * @date 2012-9-25 下午05:11:34 
 *  
 */
public class ContractRemindServiceTest extends BaseTxTestCase {

	private IMessageJobService remindService;
	
	@Test
	public void test()throws Exception{
		IMessageJobDefine job = new ContractRemind();
		remindService = (IMessageJobService)this.applicationContext.getBean(job.getServiceName());
		
		remindService.doJob(job);
	}
}
