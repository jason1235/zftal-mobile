package com.zfsoft.hrm.baseinfo.infoclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import base.BaseTxTestCase;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoPropertyView;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyViewQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.IInfoPropertyViewService;

/** 
 * @author jinjj
 * @date 2012-11-13 上午11:19:24 
 *  
 */
public class InfoPropertyViewServiceTest extends BaseTxTestCase {

	private IInfoPropertyViewService viewService;
	
	@Test
	public void test(){
		viewService = (IInfoPropertyViewService)this.applicationContext.getBean("infoPropertyViewService");
		List<InfoPropertyView> list1 = new ArrayList<InfoPropertyView>();
		List<InfoPropertyView> list2 = new ArrayList<InfoPropertyView>();
		List<InfoPropertyView> list3 = new ArrayList<InfoPropertyView>();
		
		InfoPropertyViewQuery query1 = new InfoPropertyViewQuery();
		query1.setClassId("1");
		InfoPropertyViewQuery query2 = new InfoPropertyViewQuery();
		query2.setClassId("2");
		query2.setUsername("aaa");
		InfoPropertyViewQuery query3 = new InfoPropertyViewQuery();
		query3.setClassId("1");
		for(int i=0;i<10;i++){
			InfoPropertyView view = new InfoPropertyView();
			view.setClassId("1");
			view.setPropertyId(i+"");
			list1.add(view);
		}
		for(int i=0;i<5;i++){
			InfoPropertyView view = new InfoPropertyView();
			view.setClassId("2");
			view.setPropertyId(i+"");
			view.setUsername("aaa");
			list2.add(view);
		}
		for(int i=0;i<4;i++){
			InfoPropertyView view = new InfoPropertyView();
			view.setClassId("1");
			view.setPropertyId(i+"");
			list3.add(view);
		}
		viewService.save(list1);
		list1 = viewService.getList(query1);
		Assert.assertEquals(10, list1.size());
		
		viewService.save(list2);
		list2 = viewService.getList(query2);
		Assert.assertEquals(5, list2.size());
		
		viewService.save(list3);
		list3 = viewService.getList(query3);
		Assert.assertEquals(4, list3.size());
		list2 = viewService.getList(query2);
		Assert.assertEquals(5, list2.size());
	}
}
