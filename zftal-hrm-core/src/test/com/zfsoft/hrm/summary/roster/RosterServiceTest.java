package com.zfsoft.hrm.summary.roster;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.hrm.summary.roster.entity.Roster;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterService;

/** 
 * 花名册test
 * @author jinjj
 * @date 2012-8-30 上午11:57:23 
 *  
 */
public class RosterServiceTest extends BaseTxTestCase {

	private IRosterService rosterService;
	
	@Test
	public void test(){
		Roster roster = new Roster();
		roster.setName("测试");
		roster.setDescription("test");
		roster.setCreatetime(new Date());
		roster.setCreator("admin");
		rosterService = (IRosterService)this.applicationContext.getBean("rosterService");
		
		rosterService.save(roster);
		
		roster.setDescription("test2");
		rosterService.update(roster);
		
		Roster newObj = rosterService.getById(roster.getGuid());
		Assert.assertEquals(newObj.getDescription(), "test2");
		
		rosterService.delete(roster.getGuid());
	}
}
