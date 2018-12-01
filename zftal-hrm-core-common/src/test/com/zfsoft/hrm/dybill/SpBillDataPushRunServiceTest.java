package com.zfsoft.hrm.dybill;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zfsoft.hrm.dybill.service.ISpBillDataPushRunService;

import base.BaseTxTestCase;

public class SpBillDataPushRunServiceTest extends BaseTxTestCase{

	@Autowired
	private ISpBillDataPushRunService spBillDataPushRunService;
	
	@Test
	public void test(){
		spBillDataPushRunService.pushData("E3E3C446C4D9273BE040007F010071F7","2013002", "DBB47B3B85037CE9E040007F01003757");
	}
}
