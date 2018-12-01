package com.zfsoft.hrm.config;

import junit.framework.TestCase;

import org.junit.Test;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.config.type.InfoCatalogType;
import com.zfsoft.hrm.config.type.InfoClassType;
import com.zfsoft.hrm.config.type.InfoPropertyType;
import com.zfsoft.hrm.config.type.OrgType;

/**
 * {@link TypeFactory}的测试类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-7-28
 * @version V1.0.0
 */
public class TypeFactoryTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();
		ServiceFactory.getService("infoClassTypes");
	}
	
	@Test
	public void testGetType() {
		
		System.out.println( "------------- InfoClassTypeTest ------------" );
		Type[] types = TypeFactory.getTypes( InfoClassType.class );
		
		for (Type type : types) {
			System.out.println( type.getName() + "\t" + type.getText() );
		}
		
		System.out.println( "------------- InfoPropertyTypeTest ------------" );
		
		types = TypeFactory.getTypes( InfoPropertyType.class );
		
		for (Type type : types) {
			System.out.println( type.getName() + "\t" + type.getText() );
		}
		
		System.out.println( "------------- OrgTypeTest ------------" );
		
		types = TypeFactory.getTypes( OrgType.class );
		
		for (Type type : types) {
			System.out.println( type.getName() + "\t" + type.getText() );
		}
		
		System.out.println( "------------- InfoCatalogTest ------------" );
		
		types = TypeFactory.getTypes( InfoCatalogType.class );
		
		for( Type type : types ) {
			System.out.println( type.getName() + "\t" + type.getText() );
		}
	}
	
}
