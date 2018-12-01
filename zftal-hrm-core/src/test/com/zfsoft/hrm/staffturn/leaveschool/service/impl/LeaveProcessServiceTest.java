package com.zfsoft.hrm.staffturn.leaveschool.service.impl;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Assert;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveProcess;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveProcessQuery;
import com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface.ILeaveProcessService;

/** 
 * 离校处理test
 * @author jinjj
 * @date 2012-8-3 上午03:58:03 
 *  
 */
public class LeaveProcessServiceTest extends TestCase {

	private ILeaveProcessService leaveProcessService;
	private LeaveProcess process;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		leaveProcessService = (ILeaveProcessService)ServiceFactory.getService("leaveProcessService");
		
		process = new LeaveProcess();
		process.setAccountId("0001");
	}
	
	public void testUpdate(){
		leaveProcessService.updateStatus(process);
	}
	
	public void testList(){
		LeaveProcessQuery query = new LeaveProcessQuery();
		query.setAccountId(process.getAccountId());
		query.setStatus("1");
		List<LeaveProcess> list = leaveProcessService.getList(query);
		Assert.assertTrue(list.size()>0);
	}
}
