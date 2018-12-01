package com.zfsoft.hrm.baseinfo.org.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.org.entities.OrgInfo;
import com.zfsoft.hrm.baseinfo.org.query.OrgQuery;
import com.zfsoft.hrm.baseinfo.org.service.svcinterface.IOrgService;

import junit.framework.TestCase;

public class OrgServiceTest extends BaseTxTestCase {

	private IOrgService service;
	
	@Test
	public void test(){
		service = (IOrgService)this.applicationContext.getBean("baseOrgService");
		/*List<String> orgList = new ArrayList<String>();
		
		service.disuse("100402");*/
		/*OrgQuery query = new OrgQuery();
		OrgInfo parent = new OrgInfo();
		parent.setOid("100402");
		query.setParent(parent);
		List<OrgInfo> list = service.getList(query);
		for (OrgInfo orgInfo : list) {
			System.out.println(orgInfo.getName());
		}*/
		//service.use("100402");
		/*OrgInfo info = new OrgInfo();
		info.setOid("10040201");
		info.setType("1");
		service.modify(info);*/
		service.remove("10040106");
	}
}
