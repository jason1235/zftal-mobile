package com.zfsoft.hrm.staffturn.retire.service.impl;

import java.util.Date;

import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.staffturn.retire.entities.RetireInfo;
import com.zfsoft.hrm.staffturn.retire.query.RetireInfoQuery;
import com.zfsoft.hrm.staffturn.retire.service.svcinterface.IRetireScanService;
import com.zfsoft.hrm.staffturn.retire.service.svcinterface.IRetireService;

public class RetireServiceImplTest extends BaseTxTestCase {

	@Test
	public void get(){
		CodeUtil.initialize();
		IRetireService retireService=(IRetireService)this.applicationContext.getBean("retireService");
		RetireInfo retireInfo=new RetireInfo();
		retireInfo.setUserId("2013");
		retireInfo.setRetirePost("08842");
		retireInfo.setRetireTime(new Date());
		retireInfo.setState(0);
		retireService.saveRetire(retireInfo, "add");
		
		
		RetireInfoQuery query=new RetireInfoQuery();
		query.setUserId("2012");
//		Assert.assertEquals("2012", retireInfos.get(0).getDynaBean().getValue("gh"));
	}
	/**
	 * 测试退休扫描
	 */
	@Test
	public void test(){
		IRetireScanService retireScanService = (IRetireScanService)this.applicationContext.getBean("retireScanService");
		retireScanService.doScan("admin", 0, null);
	}
}
