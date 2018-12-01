package com.zfsoft.globalweb.action;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.log.User;
import com.zfsoft.common.query.QueryModel;
import com.zfsoft.common.service.BaseLog;
import com.zfsoft.dao.entities.XsmmModel;
import com.zfsoft.service.impl.LogEngineImpl;
import com.zfsoft.service.svcinterface.KlwhService;

/**
 * 类名称：KlwhAction 
 * 类描述： 口令维护控制 
 * 创建人：xucy 
 * 创建时间：2012-4-17 下午01:41:27 
 */
public class KlwhAction extends BaseAction implements ModelDriven<XsmmModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private XsmmModel model = new XsmmModel();

	private KlwhService klwhService;
	
	private BaseLog baseLog = LogEngineImpl.getInstance();	

	/**
	 * 
	 * 方法描述:学生列表 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String cxXsxx() {
		try {

			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(klwhService.getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 跳转到初始化规则页面
	 * 
	 * @return
	 */
	public String cshgz() {
		ValueStack vs = getValueStack();
		String pkValue = getRequest().getParameter("pkValue");
		String type = getRequest().getParameter("type");
		vs.set("pkValue", pkValue);
		vs.set("type", type);
		return "toCshgz";
	}

	/**
	 * 批量初始化
	 * 
	 * @return
	 */
	public String plCsh() throws Exception {
		User user = getUser();
		String pk = model.getPkValue();
		boolean result = klwhService.plCsh(model);
		String key = result ? "I99001" : "I99002";
		getValueStack().set("result", getText(key));
		if (result) {
			// 记操作日志
			baseLog.update(user, getText("log.message.ywmc",
					new String[] { "学生密码初始化", "XG_XTGL_XSMMB" }),
					"系统管理", getText("log.message.czms",
							new String[] { "批量初始化密码", "学号", pk }));
		}
		return "toCshgz";
	}

	/**
	 * 全部初始化
	 * 
	 * @return
	 */
	public String qbCsh() throws Exception {
		User user = getUser();
		boolean result = klwhService.qbCsh(model,getResponse());
		String key = result ? "I99001" : "I99002";
		getValueStack().set("result", getText(key));
		if (result) {
			// 记操作日志
			baseLog.insert(user, getText("log.message.ywmc",
					new String[] { "学生密码初始化", "XG_XTGL_XSMMB" }),
					"系统管理", "全部初始化密码");
		}
		return "qbCsh";
	}

	public XsmmModel getModel() {
		return model;
	}

	public void setModel(XsmmModel model) {
		this.model = model;
	}

	public KlwhService getKlwhService() {
		return klwhService;
	}

	public void setKlwhService(KlwhService klwhService) {
		this.klwhService = klwhService;
	}

}