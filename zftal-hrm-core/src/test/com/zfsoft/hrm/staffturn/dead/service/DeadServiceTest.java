package com.zfsoft.hrm.staffturn.dead.service;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.staffturn.dead.entities.DeadInfo;
import com.zfsoft.hrm.staffturn.dead.service.svcinterface.IDeadService;

public class DeadServiceTest extends BaseTxTestCase {

	@Test
	public void get(){
		CodeUtil.initialize();
		IDeadService deadService=(IDeadService)this.applicationContext.getBean("deadService");
		DeadInfo deadInfo=new DeadInfo();
		deadInfo.setUserId("2013");
		deadInfo.setDeadSubsidy(50000);
		deadInfo.setDeadTime(new Date());
		deadInfo.setReceiveDate(new Date());
		deadInfo.setRemark("意外身亡");
		deadService.saveDeadInfo(deadInfo, "add");
		
		deadInfo=deadService.getDeadInfoByUserId("2013");
		
		deadInfo.setReceiveDate(new Date());
		
		deadInfo.setReceiver("ss");
		
		deadService.saveDeadInfo(deadInfo, "modify");
		
		deadInfo=deadService.getDeadInfoByUserId("2012");
		
		Assert.assertEquals("意外身亡", deadInfo.getRemark());
	}
}
