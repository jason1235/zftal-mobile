package com.zfsoft.web.action;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.zfsoft.base.BaseTest;
import com.zfsoft.globalweb.action.BrandAction;



public class BrandActionTest extends BaseTest {

	private BrandAction brandAction = (BrandAction)super.getTestBean("BrandAction");

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	

	
	
	@Test
	public void testSelectBrandByPage() {
		String result = brandAction.selectByPage();
		assertEquals(result, "success");
	}

	
	
}
