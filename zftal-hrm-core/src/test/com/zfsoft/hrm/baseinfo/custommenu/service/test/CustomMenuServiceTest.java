package com.zfsoft.hrm.baseinfo.custommenu.service.test;

import org.junit.Assert;
import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.hrm.baseinfo.custommenu.entity.CustomMenu;
import com.zfsoft.hrm.baseinfo.custommenu.service.ICustomMenuService;

/** 
 * @author jinjj
 * @date 2012-12-18 下午06:13:11 
 *  
 */
public class CustomMenuServiceTest extends BaseTxTestCase {

	private ICustomMenuService customMenuService;
	@Test
	public void test() throws Exception{
		customMenuService = (ICustomMenuService)this.applicationContext.getBean("customMenuService");
		CustomMenu custom = new CustomMenu();
		custom.setName("aaa");
//		custom.setPropertyName("aaa");
		custom.setType("1");
//		custom.setParam("1");
		customMenuService.save(custom);
		CustomMenu old = customMenuService.getById(custom.getMenuId());
		Assert.assertNotNull(old);
		custom.setName("bbb");
		customMenuService.update(custom);
		old = customMenuService.getById(custom.getMenuId());
		Assert.assertEquals(custom.getName(), old.getName());
		customMenuService.delete(custom.getMenuId());
		old = customMenuService.getById(custom.getMenuId());
		Assert.assertNull(old);
	}
}
