//package com.zfsoft.hrm.normal.info.service.test;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import junit.framework.TestCase;
//
//import com.zfsoft.common.factory.ServiceFactory;
//import com.zfsoft.hrm.normal.info.service.svcinterface.IOverallInfoService;
//
///** 
// * 个人综合信息业务操作实现类-单元测试
// * @ClassName: OverallInfoServiceTest 
// * @author jinjj
// * @date 2012-6-18 上午10:44:40 
// *  
// */
//public class OverallInfoServiceTest extends TestCase {
//
//	private IOverallInfoService overallInfoService;
//	
//	protected void setUp() throws Exception {
//		super.setUp();
//		overallInfoService = (IOverallInfoService)ServiceFactory.getService("overallInfoService");
//	}
//
//	public void testCount(){
//		String condition = "zhxl like #{props.xl}";
//		Map<String,Object> map = new HashMap<String,Object>();
//		map.put("xl", "1_");
//		int cnt = overallInfoService.count(condition, map);
//		System.out.println(cnt);
//	}
//}
