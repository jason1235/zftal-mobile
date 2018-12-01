package com.zfsoft.hrm.staffturn.leaveschool.service.impl;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Assert;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.staffturn.leaveschool.entities.LeaveStep;
import com.zfsoft.hrm.staffturn.leaveschool.query.LeaveStepQuery;
import com.zfsoft.hrm.staffturn.leaveschool.service.svcinterface.ILeaveStepService;

/** 
 * 
 * @author jinjj
 * @date 2012-8-1 上午02:53:24 
 *  
 */
public class LeaveStepServiceTest extends TestCase {

	private ILeaveStepService leaveStepService;
	private LeaveStep step;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		leaveStepService = (ILeaveStepService)ServiceFactory.getService("leaveStepService");
		
		step = new LeaveStep();
		step.setDeptId("0001");
		step.setHandler("335");
	}
	
	public void testSave(){
		leaveStepService.save(step);
		Assert.assertTrue(step.getGuid() != null);
	}
	
	public void testUpdate(){
		getEntity(step.getDeptId());
		step.setHandler("801");
		leaveStepService.update(step);
		LeaveStep now = leaveStepService.getById(step.getGuid());
		Assert.assertEquals("801",now.getHandler());
	}
	
	public void testRemove(){
		getEntity(step.getDeptId());
		leaveStepService.remove(step.getGuid());
		LeaveStep now = leaveStepService.getById(step.getGuid());
		Assert.assertNull(now);
	}
	
	private void getEntity(String deptId){
		LeaveStepQuery query = new LeaveStepQuery();
		query.setDeptId(deptId);
		List<LeaveStep> list = leaveStepService.getList(query);
		step = list.get(0);
	}
}
