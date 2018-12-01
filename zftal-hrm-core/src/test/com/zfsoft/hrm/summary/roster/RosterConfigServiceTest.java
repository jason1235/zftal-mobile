package com.zfsoft.hrm.summary.roster;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.hrm.summary.roster.entity.RosterConfig;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterConfigService;

/** 
 * 花名册test
 * @author jinjj
 * @date 2012-8-30 上午11:57:23 
 *  
 */
public class RosterConfigServiceTest extends BaseTxTestCase {

	private IRosterConfigService rosterConfigService;
	
	@Test
	public void test(){
		RosterConfig config = new RosterConfig();
		config.setGuid("0001");
		config.setQueryType("1");
		config.setCreatetime(new Date());
		rosterConfigService = (IRosterConfigService)this.applicationContext.getBean("rosterConfigService");
		
		rosterConfigService.save(config);
		
		config.setQueryType("2");
		rosterConfigService.update(config);
		
		RosterConfig newObj = rosterConfigService.getById(config.getGuid());
		Assert.assertEquals(newObj.getQueryType(), "2");
		
		rosterConfigService.delete(config.getGuid());
	}
}
