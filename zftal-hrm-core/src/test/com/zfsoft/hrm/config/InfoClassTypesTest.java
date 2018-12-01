package com.zfsoft.hrm.config;

import junit.framework.TestCase;

import org.junit.Test;

import com.zfsoft.common.factory.ServiceFactory;

public class InfoClassTypesTest extends TestCase {
	
	private Types types;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		types = (Types) ServiceFactory.getService("infoClassTypes");
	}
	
	@Test
	public void testGetType() {
		
		System.out.println( types.getTypeClass().getName() );
	}
}
