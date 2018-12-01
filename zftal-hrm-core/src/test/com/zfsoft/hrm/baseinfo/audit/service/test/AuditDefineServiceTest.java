package com.zfsoft.hrm.baseinfo.audit.service.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.dao.page.PageList;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditDefine;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditInfo;
import com.zfsoft.hrm.baseinfo.audit.entity.AuditProcess;
import com.zfsoft.hrm.baseinfo.audit.query.AuditDefineQuery;
import com.zfsoft.hrm.baseinfo.audit.query.AuditProcessQuery;
import com.zfsoft.hrm.baseinfo.audit.service.IAuditDefineService;
import com.zfsoft.hrm.baseinfo.audit.service.impl.AuditProcessServiceImpl;


/** 
 * @author jinjj
 * @date 2012-9-28 下午03:48:13 
 *  
 */
public class AuditDefineServiceTest extends BaseTxTestCase {

	private IAuditDefineService auditDefineService;
	private AuditProcessServiceImpl processService;
	
	@Test
	public void testSave() throws Exception{
		auditDefineService = (IAuditDefineService)this.applicationContext.getBean("baseAuditDefineService");
		processService = (AuditProcessServiceImpl)this.applicationContext.getBean("baseAuditProcessService");
		
		createProcess();
		
		//采用了个人概况的信息类
		AuditDefine define = new AuditDefine();
		define.setClassId("C393FE11C4DC8E46E040007F01003F39");
		define.setRoleId("1");
		
		auditDefineService.save(define);
		
		AuditDefineQuery query = new AuditDefineQuery();
		query.setClassId(define.getClassId());
		List<AuditDefine> list = auditDefineService.getList(query);
		Assert.assertEquals(3, list.size());
		
	}
	
	@Test
	public void testDelete() throws Exception{
		auditDefineService = (IAuditDefineService)this.applicationContext.getBean("baseAuditDefineService");
		processService = (AuditProcessServiceImpl)this.applicationContext.getBean("baseAuditProcessService");
		
		createProcess();
		
		AuditDefine define = new AuditDefine();
		define.setClassId("C393FE11C4DC8E46E040007F01003F39");
		define.setRoleId("1");
		define.setGuid("CB9214B472AEE587E040007F0100724A");
		
		AuditDefineQuery query = new AuditDefineQuery();
		query.setClassId(define.getClassId());
		List<AuditDefine> list = auditDefineService.getList(query);
		Assert.assertEquals(2, list.size());
		
		auditDefineService.delete(define.getGuid());
		//删除后流程重置
		AuditProcess process = new AuditProcess();
		process.setGuid("guid");
		process = processService.getById(process.getGuid());
		Assert.assertSame(0, process.getStep());
		
			auditDefineService.delete("CB9214B472B0E587E040007F0100724A");
	}
	
	private void createProcess(){
		AuditProcess process = new AuditProcess();
		process.setClassId("C393FE11C4DC8E46E040007F01003F39");
		process.setGh("gh");
		process.setGuid("guid");
		
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
	}
}
