package com.zfsoft.globalweb.action;

import com.opensymphony.xwork2.ModelDriven;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.dao.entities.SjfwzModel;
import com.zfsoft.service.svcinterface.ISjfwzService;

/**
 * 
 * 类名称：SjfwzAction 
 * 类描述：数据范围组
 * 创建人：caozf 
 * 创建时间：2012-7-10
 */
public class SjfwzAction extends BaseAction implements	ModelDriven<SjfwzModel> {
	private static final long serialVersionUID = 1L;
	private SjfwzModel model = new SjfwzModel();
	private ISjfwzService sjfwzService;
	
	@Override
	public SjfwzModel getModel() {
		// TODO Auto-generated method stub
		return model;
	}
	public ISjfwzService getSjfwzService() {
		return sjfwzService;
	}
	public void setSjfwzService(ISjfwzService sjfwzService) {
		this.sjfwzService = sjfwzService;
	}

	
 

}
