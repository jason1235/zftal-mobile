package com.zfsoft.hrm.baseinfo.search.service.test;

import java.util.List;

import junit.framework.TestCase;

import org.junit.Test;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.search.entities.CommonSearch;
import com.zfsoft.hrm.baseinfo.search.entities.CommonSearchRelation;
import com.zfsoft.hrm.baseinfo.search.entities.Condition;
import com.zfsoft.hrm.baseinfo.search.query.CommonSearchQuery;
import com.zfsoft.hrm.baseinfo.search.service.svcinterface.ICommonSearchService;

/**
 * 常用查询service 单元测试
 * @ClassName: CommonSearchServiceTest 
 * @author jinjj
 * @date 2012-6-15 下午02:36:41 
 *
 */
public class CommonSearchServiceTest extends TestCase {

	private ICommonSearchService service;
	private CommonSearch entity;
	private CommonSearchRelation relation;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		service = (ICommonSearchService)ServiceFactory.getService("baseCommonSearchService");
	}
	
	@Test
	public void testGetSearch() {
		entity = service.getSearch("1");
		assertEquals("1", entity.getGuid());
	}
	
	public void testGetSearchs() {
		CommonSearchQuery csq = new CommonSearchQuery();
		csq.setTitle("学");
		entity = service.getSearchs(csq).get(0);
		assertEquals("1", entity.getGuid());
	}
	
	public void testAddSearch(){
		entity = new CommonSearch();
		entity.setGuid("2");
		entity.setTitle("职称");
		service.addSearch(entity);
	}
	
	public void testInsertRelation(){
		relation = new CommonSearchRelation();
		relation.setGuid("2");
		relation.setConditionId("test");
		service.insertRelation(relation);
		relation.setConditionId("test2");
		service.insertRelation(relation);
	}
	
	public void testModifySearch(){
		entity = new CommonSearch();
		entity.setGuid("2");
		entity.setTitle("职称2");
		service.modifySearch(entity);
	}
	
	public void testDeleteRelation(){
		relation = new CommonSearchRelation();
		relation.setGuid("2");
		relation.setConditionId("test2");
		service.deleteRelation(relation);
	}
	
	public void testRemoveSearch(){
		service.removeSearch("2");
	}
	
	public void testGetCountData(){
		CommonSearch cs = service.getCountData("1");
		List<Condition> cons = cs.getConditions();
		printData(cons);
	}
	
	private void printData(List<Condition> cons){
		for(Condition con : cons){
			if(con.getChildren().size()>0){
				System.out.print("标题:"+con.getTitle());
				System.out.println("  结果:"+con.getResult().getProperty("count"));
				printData(con.getChildren());
			}else{
				System.out.print("标题:"+con.getTitle());
				System.out.println("  结果:"+con.getResult().getProperty("count"));
			}
		}
	}
}