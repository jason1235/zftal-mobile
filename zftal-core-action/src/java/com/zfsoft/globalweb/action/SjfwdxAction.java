package com.zfsoft.globalweb.action;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.query.QueryModel;
import com.zfsoft.dao.entities.SjfwdxModel;
import com.zfsoft.service.svcinterface.ISjfwdxService;

/**
 * 
 * 类名称：SjfwdxAction 
 * 类描述：数据范围对象
 * 创建人：caozf 
 * 创建时间：2012-7-10
 */
public class SjfwdxAction extends BaseAction implements
		ModelDriven<SjfwdxModel> {
	private static final long serialVersionUID = 1L;
	private SjfwdxModel model = new SjfwdxModel();
	private ISjfwdxService sjfwdxService;
	
	//查询数据范围对象
	public String cxSjfwdx(){
		ValueStack vs = getValueStack();
		try{
			QueryModel queryModel = model.getQueryModel();
			queryModel.setCurrentPage(Integer.valueOf(getRequest().getParameter("page")));
			List<SjfwdxModel>  lists = sjfwdxService.getPagedList(model);
			queryModel.setItems(lists);
			vs.set(DATA, queryModel);
		}catch(Exception e){
			logException(e);
		}
		return DATA;
	}
	
	@Override
	public SjfwdxModel getModel() {
		return model;
	}

	public ISjfwdxService getSjfwdxService() {
		return sjfwdxService;
	}

	public void setSjfwdxService(ISjfwdxService sjfwdxService) {
		this.sjfwdxService = sjfwdxService;
	}
}
