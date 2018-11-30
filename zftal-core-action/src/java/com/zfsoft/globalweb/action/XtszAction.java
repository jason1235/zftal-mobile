package com.zfsoft.globalweb.action;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.GlobalString;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.log.User;
import com.zfsoft.common.service.BaseLog;
import com.zfsoft.dao.entities.XtszModel;
import com.zfsoft.service.impl.LogEngineImpl;
import com.zfsoft.service.svcinterface.IJcsjService;
import com.zfsoft.service.svcinterface.IXtszService;

/**
 * 
 * 
 * 类名称：XtszAction 类描述：系统设置 创建人：qph 创建时间：2012-4-20 修改备注：
 * 
 */
public class XtszAction extends BaseAction implements ModelDriven<XtszModel> {

	private static final long serialVersionUID = 1L;

	private XtszModel model = new XtszModel();
	private IJcsjService jcsjService;
	private IXtszService xtszService;
	private BaseLog baseLog = LogEngineImpl.getInstance();

	/**
	 * 界面请求：更新系统设置
	 * 
	 * @return
	 */
	public String xtsz() {
		try {

			XtszModel model = xtszService.getModel("");
			BeanUtils.copyProperties(this.model, model);
			setList();

			return "xtsz";

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}

	/**
	 * 加载页面数据列表
	 * 
	 * @throws Exception
	 */
	private void setList() throws Exception {
		ValueStack rs = this.getValueStack();
		List lxRows = xtszService.cxXnlb(null);

		rs.set("curStudyYear", lxRows);

		List xqRows = jcsjService.getJcsjList(GlobalString.JCSJ_XUEQDM);
		rs.set("curStudyScope", xqRows);

		List ndRows = xtszService.cxNdlb(null);

		rs.set("curYear", ndRows);
	}

	/**
	 * 操作请求：更新系统设置
	 * 
	 * @return
	 */
	public String xtszXg() {
		try {
			User user = (User) getSession().getAttribute("user");

			boolean result = xtszService.update(model);

			// 记操作日志
			baseLog.update(user, getText("log.message.ywmc",
					new String[] { "系统设置", "xg_xtgl_xtszb" }),
					"系统管理", "修改系统设置");
			String key = result ? "I99001" : "I99002";
			getValueStack().set("message", getText(key));

			XtszModel model = xtszService.getModel("");
			BeanUtils.copyProperties(this.model, model);
			setList();

			return xtsz();

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}

	public XtszModel getModel() {
		return model;
	}

	public IJcsjService getJcsjService() {
		return jcsjService;
	}

	public void setJcsjService(IJcsjService jcsjService) {
		this.jcsjService = jcsjService;
	}

	public IXtszService getXtszService() {
		return xtszService;
	}

	public void setXtszService(IXtszService xtszService) {
		this.xtszService = xtszService;
	}

}
