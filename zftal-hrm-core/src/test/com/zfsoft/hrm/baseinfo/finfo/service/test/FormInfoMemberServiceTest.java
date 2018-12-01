package com.zfsoft.hrm.baseinfo.finfo.service.test;

import junit.framework.TestCase;

import org.junit.Test;

import com.zfsoft.common.factory.ServiceFactory;
import com.zfsoft.hrm.baseinfo.finfo.entities.FormInfoMember;
import com.zfsoft.hrm.baseinfo.finfo.service.impl.FormInfoMemberServiceImpl;
import com.zfsoft.hrm.baseinfo.finfo.service.svcinterface.IFormInfoMemberService;
import com.zfsoft.hrm.config.IConstants;

/**
 * {@link FormInfoMemberServiceImpl}的单元测试
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-8-24
 * @version V1.0.0
 */
public class FormInfoMemberServiceTest extends TestCase {
	
	private IFormInfoMemberService formInfoMemberService;

	@Override
	public void setUp() throws Exception {
		formInfoMemberService = (IFormInfoMemberService) ServiceFactory.getService("baseFormInfoMemberService");
	}
	
	@Test
	public void test() {
		
		FormInfoMember[] members = formInfoMemberService.getMembers( IConstants.FINFO_NAME_FELLOWS,false );
		
		for ( FormInfoMember member : members ) {
			System.out.println( member.getTitle() );
		}
		
		//增加测试
		formInfoMemberService.saveMember( members[0] );
		
		members[0].setText("test");
	}
}
