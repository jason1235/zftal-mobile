package com.zfsoft.workflow.service.impl;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.workflow.dao.ISpAuditingLogDao;

public class SpAuditingLogServiceImplTest extends BaseTxTestCase{

	ISpAuditingLogDao spAuditingLogDao;
	SpAuditingLogServiceImpl spAuditingLogService;
	
	@Before
	public void setUp() throws Exception {
		spAuditingLogService=(SpAuditingLogServiceImpl)this.applicationContext.getBean("spAuditingLogService");
		spAuditingLogDao=(ISpAuditingLogDao)this.applicationContext.getBean("spAuditingLogDao");
	}

	@Test
	public void testSetSpAuditingLogDao() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertNodeLog() {
		fail("Not yet implemented");
	}

	@Test
	public void testInsertTaskLog() {
		fail("Not yet implemented");
	}

	@Test
	public void testFindAuditingLogByWId() {
		fail("Not yet implemented");
	}

}
