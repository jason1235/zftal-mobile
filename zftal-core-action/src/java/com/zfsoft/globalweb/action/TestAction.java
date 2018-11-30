package com.zfsoft.globalweb.action;

import com.zfsoft.common.action.BaseCRUDAction;
import com.zfsoft.common.service.BaseLog;
import com.zfsoft.common.service.BaseService;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.service.impl.LogEngineImpl;
import com.zfsoft.service.svcinterface.ITestService;

public class TestAction extends BaseCRUDAction<YhglModel>{
	
	private static final long serialVersionUID = -2876385762445091581L;
	
	private ITestService testService;
	
	public TestAction (){
		 super("基础数据维护","XG_XTGL_JCSJB","系统管理","基础数据");
	}
	
	@Override
	protected BaseLog getEntityLog() {
		return LogEngineImpl.getInstance();
	}
	
	@Override
	protected BaseService getEntityService() {
		return testService;
	}


//	public String saveTest(){
//		try {
//			YhglModel yhglModel = super.getModel();
//			
//			super.save();
//			
//		} catch (Exception e) {
//	 
//		}
//		return "";
//	}
	
	
	public ITestService getTestService() {
		return testService;
	}


	public void setTestService(ITestService testService) {
		this.testService = testService;
	}
}
