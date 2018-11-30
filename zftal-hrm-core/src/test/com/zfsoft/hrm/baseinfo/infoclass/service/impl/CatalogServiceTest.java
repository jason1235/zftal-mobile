package com.zfsoft.hrm.baseinfo.infoclass.service.impl;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.infoclass.entities.Catalog;
import com.zfsoft.hrm.baseinfo.infoclass.query.CatalogQuery;
import com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface.ICatalogService;

/**
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-3
 * @version V1.0.0
 */
public class CatalogServiceTest extends TestCase {
	
	private ICatalogService servie;
	
	@Before
	public void setUp(){
		servie = (ICatalogService) ServiceFactory.getService( "baseInfoCatalogService" );
	}
	
	@Test
	public void test() {
		
		servie.getList( getQuery() );
		
		Catalog bean = getBean();
		
		servie.add( bean );
		
		servie.modify( bean );
		
		servie.remove( bean.getGuid() );
	}
	
	/**
	 * 获取查询条件
	 */
	private CatalogQuery getQuery() {
		CatalogQuery query = new CatalogQuery();
		
		query.setType( "teacher" );
		
		return query;
	}
	
	/**
	 * 获取实体信息
	 */
	private Catalog getBean() {
		Catalog bean = new Catalog();
		
		bean.setName( "目录测试" );
		bean.setType( "teacher" );
		
		return bean;
	}

}
