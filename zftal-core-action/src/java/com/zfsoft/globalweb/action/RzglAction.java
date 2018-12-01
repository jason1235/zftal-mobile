package com.zfsoft.globalweb.action;


import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;

import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.query.QueryModel;
import com.zfsoft.dao.entities.RzglModel;
import com.zfsoft.service.svcinterface.IRzglService;

/**
 * 
* 类名称：RzglAction
* 类描述：日志管理
* 创建人：qph
* 创建时间：2012-4-20 
* 修改备注： 
*
*/
public class RzglAction extends BaseAction implements ModelDriven<RzglModel> {

	private static final long serialVersionUID = 1L;

	private IRzglService service;
	
	private RzglModel model = new RzglModel();
	
	public RzglModel getModel() {
		return model;
	}
	
	/**
	 * 操作日志查询
	 * @return
	 */
	public String cxRz() {

		try {
			if (QUERY.equals(model.getDoType())){
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(service.getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "cxRz";
	}

	
	/**
	 * 查看日志详细信息
	 * @return
	 */
	public String ckRzxx(){
		try {
			RzglModel model = service.getModel(this.model.getCzbh());
			BeanUtils.copyProperties(this.model, model);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		} 
		return "ckRzxx";
	}

	public IRzglService getService() {
		return service;
	}

	public void setService(IRzglService service) {
		this.service = service;
	}
	
}
