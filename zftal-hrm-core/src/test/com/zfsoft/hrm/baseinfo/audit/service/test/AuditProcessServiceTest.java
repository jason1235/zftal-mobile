package com.zfsoft.hrm.baseinfo.audit.service.test;

import org.junit.Assert;
import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditInfo;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditProcess;
import com.zfsoft.hrm.baseinfo.audit.query.AuditProcessQuery;
import com.zfsoft.hrm.baseinfo.audit.service.impl.AuditProcessServiceImpl;

/** 
 * @author jinjj
 * @date 2012-10-10 下午03:31:11 
 *  
 */
public class AuditProcessServiceTest extends BaseTxTestCase {

	private AuditProcessServiceImpl processService;
	
	@Test
	public void test()throws Exception{
		processService = (AuditProcessServiceImpl)this.applicationContext.getBean("baseAuditProcessService");
		
		AuditProcess process = new AuditProcess();
		process.setClassId("C393FE11C4DC8E46E040007F01003F39");
		process.setGh("gh");
		process.setLogId("logId");
		
		processService.save(process);
		AuditProcessQuery query = new AuditProcessQuery();
		query.setClassId(process.getClassId());
		
		PageList<AuditProcess> pageList = processService.getPagingList(query);
		Assert.assertSame(1, pageList.size());
		
		//审核第一步通过
		AuditInfo info = new AuditInfo();
		info.setClassId(process.getClassId());
		info.setGuid(process.getGuid());
		info.setOperator("admin");
		info.setRoleId("C74E178E439943E7E040007F010067FE");
		info.setInfo("");
		processService.doPass(info);
		process = processService.getById(process.getGuid());
		Assert.assertSame(1, process.getStep());
		
		//审核第二步拒绝
//		info.setRoleId("C74E11A19D017405E040007F01006800");
//		processService.doReject(info);
//		process = processService.getById(process.getGuid());
//		Assert.assertSame(-1, process.getStep());
		
		//审核第二步通过
		info.setRoleId("C74E11A19D017405E040007F01006800");
		processService.doPass(info);
		process = processService.getById(process.getGuid());
		Assert.assertSame(99, process.getStep());
	}
}
