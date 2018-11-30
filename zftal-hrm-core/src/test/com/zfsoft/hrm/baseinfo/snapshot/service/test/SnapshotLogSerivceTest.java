package com.zfsoft.hrm.baseinfo.snapshot.service.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import base.BaseTxTestCase;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.snapshot.entities.SnapshotLog;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotLogQuery;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotLogService;
import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * @ClassName: SnapshotSerivceTest 
 * @author jinjj
 * @date 2012-7-18 下午01:15:56 
 *  
 */
public class SnapshotLogSerivceTest extends BaseTxTestCase {

	private ISnapshotLogService service;
	private Date snapTime;
	
	@Before
	public void setUp(){
		//service = (ISnapshotLogService)ServiceFactory.getService("snapshotLogService");
		service = (ISnapshotLogService)this.applicationContext.getBean("snapshotLogService");
		snapTime = TimeUtil.toDate(TimeUtil.dateX());
	}
	
	@Test
	public void testPage(){
		SnapshotLogQuery query = new SnapshotLogQuery();
		PageList list = service.getPage(query);
		Assert.assertTrue(list.size()>0);
	}
	@Test
	@Rollback(false)
	public void save(){
		SnapshotLog log = new SnapshotLog();
		log.setSnapTime(snapTime);
		service.save(log);
	}
}
