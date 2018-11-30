package com.zfsoft.hrm.baseinfo.snapshot.service.test;

import java.util.Date;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotService;
import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * @ClassName: SnapshotSerivceTest 
 * @author jinjj
 * @date 2012-7-18 下午01:15:56 
 *  
 */
public class SnapshotSerivceTest extends TestCase {

	private ISnapshotService service;
	private Date snapTime;
	
	@Before
	public void setUp(){
		//service = (ISnapshotService)this.applicationContext.getBean("snapshotService");
		service = (ISnapshotService)ServiceFactory.getService("snapshotService");
		snapTime = TimeUtil.toDate(TimeUtil.format(new Date(), "yyyy-MM"));
	}
	
	@Test
	public void testAdd(){
		service.doSnapshot(snapTime);
	}
	@Test
	public void testDelete(){
		service.delete(snapTime);
	}
}
