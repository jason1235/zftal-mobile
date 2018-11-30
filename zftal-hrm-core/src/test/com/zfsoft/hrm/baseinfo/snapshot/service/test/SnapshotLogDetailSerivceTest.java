package com.zfsoft.hrm.baseinfo.snapshot.service.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import base.BaseTxTestCase;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.snapshot.query.SnapshotLogDetailQuery;
import com.zfsoft.hrm.baseinfo.snapshot.service.svcinterface.ISnapshotLogDetailService;
import com.zfsoft.orcus.lang.TimeUtil;

/** 
 * @ClassName: SnapshotSerivceTest 
 * @author jinjj
 * @date 2012-7-18 下午01:15:56 
 *  
 */
public class SnapshotLogDetailSerivceTest extends BaseTxTestCase {

	private ISnapshotLogDetailService service;
	private Date snapTime;
	
	@Before
	public void setUp(){
		//service = (ISnapshotLogDetailService)ServiceFactory.getService("snapshotLogDetailService");
		service = (ISnapshotLogDetailService)this.applicationContext.getBean("snapshotLogDetailService");
		snapTime = TimeUtil.toDate(TimeUtil.dateX());
	}
	@Test
	public void testPage(){
		SnapshotLogDetailQuery query = new SnapshotLogDetailQuery();
		PageList list = service.getPage(query);
		Assert.assertTrue(list.size()>0);
	}
	@Test
	@Rollback(false)
	public void testSave(){
//		SnapshotLogDetail detail = new SnapshotLogDetail();
////		List<InfoClass> list = (List<InfoClass>)InfoClassCache.instance().getCache();
//		detail.setSnapTime(snapTime);
//		detail.setClazz(list.get(0));
//		detail.setOperateTime(new Date());
//		service.save(detail);
	}
}
