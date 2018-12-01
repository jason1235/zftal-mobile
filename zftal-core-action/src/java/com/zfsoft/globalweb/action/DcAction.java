package com.zfsoft.globalweb.action;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.entities.DczdpzModel;
import com.zfsoft.service.svcinterface.IDcService;

/**
 * 
* 
* 类名称：DcAction 
* 类描述： 导出控制
* 创建人：xucy 
* 创建时间：2012-4-24 上午08:41:27 
* 修改人：xucy 
* 修改时间：2012-4-24 上午08:41:27 
* 修改备注： 
* @version 
*
 */
public class DcAction extends BaseAction implements ModelDriven<DczdpzModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DczdpzModel model = new DczdpzModel();
	
	private IDcService dcService;
	
	public DczdpzModel getModel() {
		return model;
	}

	public void setModel(DczdpzModel model) {
		this.model = model;
	}
	
	public IDcService getDcService() {
		return dcService;
	}

	public void setDcService(IDcService dcService) {
		this.dcService = dcService;
	}

	/**
	 * 
	* 方法描述: 导出字段设置
	* 参数 @return 参数说明
	* 返回类型 String 返回类型
	* @throws
	 */
	public String szDczd() throws Exception{
		 User user = getUser();
		ValueStack vs = getValueStack();

		List<DczdpzModel> dczdList = dcService.cxDczdList(model,user);//所有字段
		
		DczdpzModel mrzdmodel = dcService.cxMrzd(model,user);//默认字段
		
		DczdpzModel xzzdmodel = dcService.cxXzzd(model,user);//选中字段
		
		vs.set("dczdList", dczdList);
		vs.set("mrzdmodel", mrzdmodel);
		vs.set("xzzdmodel", xzzdmodel);
		return "szDczd";
	}
	
	/**
	 * 
	* 方法描述: 保存导出字段设置
	* 参数 @return 参数说明
	* 返回类型 String 返回类型
	* @throws
	 */
	public String bcZdsz() throws Exception{
		ValueStack vs = getValueStack();
		 User user = getUser();
		boolean result = dcService.bcZdsz(model,user);
		String key = result ? "I99001" : "I99002";
		getValueStack().set("result", getText(key));
		
		List<DczdpzModel> dczdList = dcService.cxDczdList(model,user);//所有字段
		
		DczdpzModel mrzdmodel = dcService.cxMrzd(model,user);//默认字段
		
		DczdpzModel xzzdmodel = dcService.cxXzzd(model,user);//选中字段
		
		vs.set("dczdList", dczdList);
		vs.set("mrzdmodel", mrzdmodel);
		vs.set("xzzdmodel", xzzdmodel);
		
		return "szDczd";
	}
	
}

