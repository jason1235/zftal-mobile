package test.zfsoft.hrm.baseinfo.code.util;

import junit.framework.TestCase;

import com.zfsoft.hrm.baseinfo.code.util.CodeUtil;

/** 
 * @ClassName: CodeUtilTestCase 
 * @Description: 
 * @author jinjj
 * @date 2012-5-23 上午09:05:36 
 *  
 */
public class CodeUtilTestCase extends TestCase {

	public void testGetValue(){
		CodeUtil.initialize();
		String value = CodeUtil.getItemValue("test-catalog-0001", "test-item-0001");
		assertEquals("测试", value);
	}
	
	public void testReload(){
		CodeUtil.initialize();
		CodeUtil.reload();
	}
}
