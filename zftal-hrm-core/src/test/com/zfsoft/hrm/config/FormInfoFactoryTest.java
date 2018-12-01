package com.zfsoft.hrm.config;

import junit.framework.TestCase;

import org.junit.Test;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfo;

/**
 * {@link FormInfoFactory}的测试类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-22
 * @version V1.0.0
 */
public class FormInfoFactoryTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		ServiceFactory.getService("formInfoMemberDao");
	}
	
	@Test
	public void test() {
		
		FormInfo[] infos = FormInfoFactory.getInfos();
		
		System.out.println( "name\ttext\ttype" );
		
		for (FormInfo info : infos) {
			System.out.println( info.getName() + "\t" + info.getText() + "\t" + info.getType() );
		}
		
	}
}
