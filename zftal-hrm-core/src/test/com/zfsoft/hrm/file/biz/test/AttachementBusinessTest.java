package com.zfsoft.hrm.file.biz.test;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import base.BaseTxTestCase;

import com.zfsoft.hrm.core.util.Byte_File_Object;
import com.zfsoft.hrm.file.biz.bizinterface.IAttachementBusiness;
import com.zfsoft.hrm.file.biz.impl.AttachementBusinessImpl;
import com.zfsoft.hrm.file.entity.Attachement;

/**
 * {@link AttachementBusinessImpl}的单元测试类
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @date 2012-10-29
 * @version V1.0.0
 */
public class AttachementBusinessTest extends BaseTxTestCase {
	
	private IAttachementBusiness attachementBusiness;
	
	@Before
	public void setUp() {
		attachementBusiness = (IAttachementBusiness)this.applicationContext.getBean("fileAttachementBusiness");
	}
	
	@Test
	@Rollback(false)
	public void test() {
		
		File file = new File("C:\\test.txt");
		byte[] content = Byte_File_Object.getBytesFromFile( file );
		
		Attachement bean = new Attachement();
		
		bean.setFormId("test001");
		bean.setName("测试文件.txt");
		bean.setRemark("测试数据");
		bean.setSize( (long) content.length );
		bean.setType( "world" );
		bean.setContent( content );
		bean.setUploadMan("admin");
		
		try {
			attachementBusiness.save( bean );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
