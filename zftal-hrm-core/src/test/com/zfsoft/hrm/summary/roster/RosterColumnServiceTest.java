package com.zfsoft.hrm.summary.roster;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.hrm.summary.roster.entity.RosterColumn;
import com.zfsoft.hrm.summary.roster.service.svcinterface.IRosterColumnService;

/** 
 * 花名册字段test
 * @author jinjj
 * @date 2012-9-11 上午09:02:51 
 *  
 */
public class RosterColumnServiceTest extends BaseTxTestCase{

	private IRosterColumnService rosterColumnService;
	
	@Test
	public void test() throws Exception{
		RosterColumn column = new RosterColumn();
		column.setClassId("class_1");
		column.setColumnId("column_1");
		column.setRosterId("roster_1");
		
		rosterColumnService = (IRosterColumnService)this.applicationContext.getBean("rosterColumnService");
	
		rosterColumnService.save(column);
		
		column.setOrder("1");
		rosterColumnService.updateOrder(column, null);
		
		column.setSort("desc");
		rosterColumnService.updateSort(column);
		
		List<RosterColumn> list = rosterColumnService.getList(column.getRosterId());
		Assert.assertSame(1, list.size());
		
		rosterColumnService.delete(column);
		
		list = rosterColumnService.getList(column.getRosterId());
		Assert.assertSame(0, list.size());
	}
}
