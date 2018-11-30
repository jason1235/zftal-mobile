package com.zfsoft.hrm.initialize.infoclass;

import org.springframework.beans.factory.InitializingBean;

import com.zfsoft.hrm.initialize.infoclass.service.IInfoClassDataPatchService;

/** 
 * @author jinjj
 * @date 2013-2-25 下午02:19:55 
 *  
 */
public class InfoClassDataPatchInitializer implements InitializingBean {

	private IInfoClassDataPatchService infoClassDataPatchService;
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		infoClassDataPatchService.doPatch();
	}
	public void setInfoClassDataPatchService(
			IInfoClassDataPatchService infoClassDataPatchService) {
		this.infoClassDataPatchService = infoClassDataPatchService;
	}

}
