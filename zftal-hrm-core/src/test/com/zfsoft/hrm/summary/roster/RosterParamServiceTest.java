package com.zfsoft.hrm.summary.roster;

import java.util.HashMap;

import org.apache.commons.lang.SerializationUtils;
import org.junit.Assert;
import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.hrm.summary.roster.entity.RosterParam;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterParamService;

/** 
 * @author jinjj
 * @date 2012-11-21 上午08:59:29 
 *  
 */
public class RosterParamServiceTest extends BaseTxTestCase{

	@Test
	public void test(){
		IRosterParamService paramService = (IRosterParamService)this.applicationContext.getBean("rosterParamService");
		
		HashMap<String,String[]> param = new HashMap<String,String[]>();
		String[] a = {"a1","a2"};
		String[] b = {"b1","b2"};
		param.put("1", a);
		param.put("2", b);
		
		byte[] data = SerializationUtils.serialize(param);
		RosterParam p = new RosterParam();
		p.setGuid("guid");
		p.setData(data);
		paramService.save(p);
		
		p = paramService.getById(p);
		
		param = (HashMap<String,String[]>)SerializationUtils.deserialize(p.getData());
		Assert.assertEquals("a1", param.get("1")[0]);
	}
}
