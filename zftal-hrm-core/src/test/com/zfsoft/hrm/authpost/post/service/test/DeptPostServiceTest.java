package com.zfsoft.hrm.authpost.post.service.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import base.BaseTxTestCase;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.authpost.post.entities.DeptPost;
import com.zfsoft.hrm.authpost.post.query.DeptPostQuery;
import com.zfsoft.hrm.authpost.post.service.svcinterface.IDeptPostService;

/** 
 * @author jinjj
 * @date 2012-7-24 下午03:41:18 
 *  
 */
public class DeptPostServiceTest extends BaseTxTestCase {

	private IDeptPostService deptPostService;
	private DeptPost post;
	@Before
	public void setUp(){
		deptPostService = (IDeptPostService)this.applicationContext.getBean("deptPostService");
		post = new DeptPost();
		post.setDeptId("001");
		post.setDuty("岗位职责");
		post.setLevel("1");
		post.setPostId("002");
		post.setPlanNumber(1);
		post.setSuperiorId("");
		post.setGuid(post.getDeptId()+post.getPostId());
	}
	@Test
	@Rollback(false)
	public void testSave(){
		deptPostService.save(post);
	}
	@Test
	@Rollback(false)
	public void testQuery(){
		DeptPost entity = deptPostService.getById(post.getGuid());
		Assert.assertEquals("001002", entity.getGuid());
	}
	@Test
	@Rollback(false)
	public void testPage(){
		DeptPostQuery query = new DeptPostQuery();
		PageList pageList = deptPostService.getPageList(query);
		Assert.assertEquals(1, pageList.size());
	}
	@Test
	@Rollback(false)
	public void testUpdate(){
		post.setDuty("1111");
		deptPostService.update(post);
		DeptPost entity = deptPostService.getById(post.getGuid());
		Assert.assertEquals("1111", entity.getDuty());
	}
	@Test
	@Rollback(false)
	public void testRemove(){
		deptPostService.remove(post.getGuid());
		DeptPost entity = deptPostService.getById(post.getGuid());
		Assert.assertNull(entity);
	}
}
