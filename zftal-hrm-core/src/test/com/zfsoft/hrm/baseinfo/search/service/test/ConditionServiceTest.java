package com.zfsoft.hrm.baseinfo.search.service.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.search.entities.Condition;
import com.zfsoft.hrm.baseinfo.search.query.ConditionQuery;
import com.zfsoft.hrm.baseinfo.search.service.impl.ConditionServiceImpl;
import com.zfsoft.hrm.baseinfo.search.service.svcinterface.IConditionService;
import com.zfsoft.hrm.config.IConstants;

/**
 * {@link ConditionServiceImpl }的测试
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-6-14
 * @version V1.0.0
 */
public class ConditionServiceTest extends TestCase {

	private IConditionService service;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		service = (IConditionService)ServiceFactory.getService("baseSearchConditionService");
	}
	
	@Test
	public void test() {
		Condition catalog = new Condition();
		catalog.setTitle("学历");
		catalog.setText("最后学历：研究生、本科、专科、高中及以下");

		service.addCatalog(catalog);	//增加条件系列
		
		Condition item = new Condition();
		item.setTitle("研究生");
		item.setExpress("___educationLevel ## '1_'");
		item.setText("包含：博士、硕士、研究生");
		item.setParentId( catalog.getGuid() );
		
		service.addItem(item);			//增加条件
		
		ConditionQuery query = new ConditionQuery();
		query.setParentId( IConstants.ROOT );
		
		service.getCatalogList(query);

		service.getFullList(query);
		
		service.getItemList(new ConditionQuery());
		
		service.getById(item.getGuid());
		
		service.modifyCatalog(catalog);
		
		service.modifyItem(item);
		
		service.removeItem(item.getGuid());
		
	}
	
	/**
	 * 初始化数据
	 */
	public void initData() {
		
	}
	
}