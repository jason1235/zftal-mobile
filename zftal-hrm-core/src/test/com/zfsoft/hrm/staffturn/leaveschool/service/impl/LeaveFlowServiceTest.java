package com.zfsoft.hrm.staffturn.leaveschool.service.impl;

import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Assert;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveFlowInfo;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveFlowInfoQuery;
import com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface.ILeaveFlowService;

/** 
 * 离校流程管理test
 * @author jinjj
 * @date 2012-8-3 上午02:52:44 
 *  
 */
public class LeaveFlowServiceTest extends TestCase {

	private ILeaveFlowService leaveFlowService;
	private LeaveFlowInfo flow;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		leaveFlowService = (ILeaveFlowService)ServiceFactory.getService("leaveFlowService");
		
		flow = new LeaveFlowInfo();
		flow.setAccountId("0001");
		flow.setProcessDept("10101");
		flow.setSalaryStatus("0");
		flow.setLeaveStatus("0");
		flow.setType("1");
	}
	
	public void testSave(){
		leaveFlowService.save(flow);
	}
	
	public void testUpdate(){
		flow.setLeaveDate(new Date());
		flow.setComment("111");
		flow.setLeaveStatus("1");
		leaveFlowService.update(flow);
		LeaveFlowInfo entity = leaveFlowService.getById(flow.getAccountId());
		Assert.assertEquals(entity.getComment(), flow.getComment());
	}
	
	public void testQuery(){
		LeaveFlowInfo entity = leaveFlowService.getById(flow.getAccountId());
		Assert.assertEquals(entity.getAccountId(), flow.getAccountId());
	}
	
	public void testList(){
		CodeUtil.initialize();
		LeaveFlowInfoQuery query = new LeaveFlowInfoQuery();
		List<LeaveFlowInfo> list = leaveFlowService.getPagingList(query);
		Assert.assertTrue(list.size()>0);
	}
}
