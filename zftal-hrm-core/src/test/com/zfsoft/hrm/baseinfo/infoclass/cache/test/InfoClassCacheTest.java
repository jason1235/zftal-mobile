package com.zfsoft.hrm.baseinfo.infoclass.cache.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.infoclass.cache.InfoClassCache;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoClass;
import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoProperty;

/**
 * 信息类缓存测试类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-7
 * @version V1.0.0
 */
public class InfoClassCacheTest extends TestCase {

	@Before
	public void setUp(){
		ServiceFactory.getService( "baseInfoCatalogService" );
	}
	
	@Test
	public void test() {
		
		InfoProperty property = new InfoProperty();
		
		List<InfoProperty> list = new ArrayList<InfoProperty>();
		list.add( property );
		
		///////////////////////////////////////////////////////////////
		List<InfoClass> classes = InfoClassCache.getInfoClasses();
		for (InfoClass clazz : classes) {
			System.out.println( clazz.getGuid() + "\t" + clazz.getProperties().size() );
			clazz.setProperties(list);
		}
		
		classes = InfoClassCache.getInfoClasses();
		
		for (InfoClass clazz : classes) {
			System.out.println( clazz.getGuid() + "\t" + clazz.getProperties().size() );
		}
		
		///////////////////////////////////////////////////////////////
		System.out.println( "----------------------------" );
		
		InfoClass clazz = InfoClassCache.getInfoClass( "C3952EF83F93C42FE040007F01001F23" );
		
		System.out.println( clazz.getName() + clazz.getProperties().size() );
		
		clazz.setName( "fsdfsdf" );
		clazz.setProperties( list );
		
		clazz = InfoClassCache.getInfoClass( "C3952EF83F93C42FE040007F01001F23" );
		
		System.out.println( clazz.getName() + clazz.getProperties().size() );
		
		///////////////////////////////////////////////////////////////
		System.out.println( "----------------------------" );
		clazz = InfoClassCache.getInfoClass( "C3952EF83F93C42FE040007F01001F23" );
		
		for ( InfoProperty p : clazz.getProperties() ) {
			System.out.println( p.getName() );
			p.setName("test");
		}
		
		clazz = InfoClassCache.getInfoClass( "C3952EF83F93C42FE040007F01001F23" );
		
		System.out.println( clazz.getProperties().get(0).getName() );
	}
	
}
