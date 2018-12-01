package com.zfsoft.globalweb.action;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.common.log.User;
import com.zfsoft.common.query.QueryModel;
import com.zfsoft.common.service.BaseLog;
import com.zfsoft.dao.entities.DczdpzModel;
import com.zfsoft.dao.entities.JcsjModel;
import com.zfsoft.service.impl.LogEngineImpl;
import com.zfsoft.service.svcinterface.IDcService;
import com.zfsoft.service.svcinterface.IJcsjService;

/**
 * 
 * 
 * 类名称：JcsjAction 
 * 类描述： 基础数据控制 
 * 创建人：xucy 
 * 创建时间：2012-4-17 下午01:14:25 
 * 修改人：xucy
 * 修改时间：2012-4-17 下午01:14:25 
 * 修改备注：
 * 
 * @version
 * 
 */
public class JcsjAction extends BaseAction implements ModelDriven<JcsjModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JcsjModel model = new JcsjModel();

	DczdpzModel dcmodel = new DczdpzModel();

	private IJcsjService jcsjService;

	private IDcService dcService;
	
	private BaseLog baseLog = LogEngineImpl.getInstance();

	/**
	 *查询类型表
	 * 
	 * @throws Exception
	 */
	public void setValueStack() throws Exception {
		ValueStack vs = getValueStack();
		List<JcsjModel> lxdmList = jcsjService.getModelList();
		vs.set("lxdmList", lxdmList);
	}

	/**
	 * 
	 * 方法描述: 基础数据列表 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String cxJcsj() {
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(jcsjService.getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			setValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}

		return SUCCESS;
	}

	/**
	 * 
	 * 方法描述: 增加基础数据 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String zjJcsj() {
		try {
			setValueStack();
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 方法描述: 保存增加基础数据 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String zjBcJcsj() {
		try {
			User user = getUser();
			boolean result = jcsjService.insert(model);
			String key = result ? "I99001" : "I99002";
			getValueStack().set("result", getText(key));
			setValueStack();
			if (result) {
				// 记操作日志
				baseLog.insert(user, getText("log.message.ywmc",
						new String[] { "基础数据维护", "XG_XTGL_JCSJB" }),
						"系统管理", "新增基础数据");
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "zjJcsj";
	}

	/**
	 * 
	 * 方法描述: 修改基础数据 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String xgJcsj() {
		try {
			JcsjModel jcsjModel = new JcsjModel();
			jcsjModel.setPkValue((getRequest().getParameter("pkValue")));
			// 查询单个信息
			jcsjModel = jcsjService.getModel(jcsjModel);
			try {
				BeanUtils.copyProperties(model, jcsjModel);
			} catch (Exception e) {
				e.printStackTrace();
			}
			setValueStack();

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 
	 * 方法描述: 保存修改基础数据 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String xgBcJcsj() {
		try {
			User user = getUser();
			JcsjModel jcsjModel = new JcsjModel();
			jcsjModel.setPkValue((getRequest().getParameter("pkValue")));
			// 查询单个信息
			jcsjModel = jcsjService.getModel(jcsjModel);

			// 修改基础数据
			boolean result = jcsjService.update(model);
			String key = result ? "I99001" : "I99002";
			getValueStack().set("result", getText(key));

			setValueStack();
			
			if (result) {
				// 记操作日志
				baseLog.update(user, getText("log.message.ywmc",
						new String[] { "基础数据维护", "XG_XTGL_JCSJB" }),
						"系统管理", getText("log.message.czms",
								new String[] { "修改基础数据", "类型和代码",
										model.getPkValue() }));

			}

		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return "xgJcsj";
	}

	/**
	 * 
	 * 方法描述: 删除基础数据 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String scJcsj() throws Exception {
		User user = getUser();
		String pks = getRequest().getParameter("ids");
		model.setPkValue(pks);
		boolean result = jcsjService.scJcsj(model);
		String key = result ? "I99005" : "I99006";
		getValueStack().set(DATA, getText(key));
		if (result) {

			// 记操作日志
			String opDesc = getText("log.message.czms", new String[] {
					"批量删除基础数据", "类型和代码", pks });

			baseLog.delete(user, getText("log.message.ywmc",
					new String[] { "基础数据维护", "XG_XTGL_JCSJB" }),
					"系统管理", opDesc);
		}
		return DATA;
	}

	/**
	 * 
	 * 方法描述: 验证代码是否已经存在 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String valideDm() throws Exception {
		
		// 查询单个用户信息
		JcsjModel jcsjModel = jcsjService.getModel(model);

		if (null != jcsjModel) {
			getValueStack().set(DATA, "该代码已经存在!");
		}
		return DATA;
	}

	/**
	 * 
	 * 方法描述: 导出基础数据 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String dcJcsj() throws Exception {
		User user = getUser();
		//TODO 模型驱动使用问题，以下两行代码完全没有必要
		String dcclbh = getRequest().getParameter("dcclbh");
		dcmodel.setDcclbh(dcclbh);

		List<JcsjModel> jcsjlist = jcsjService.cxJcsjList(model);// 结果集

		dcService.dcSj(dcmodel, jcsjlist, user,getResponse());
		return null;
	}

	public JcsjModel getModel() {
		return model;
	}

	public void setModel(JcsjModel model) {
		this.model = model;
	}
  
	public IJcsjService getJcsjService() {
		return jcsjService;
	}

	public void setJcsjService(IJcsjService jcsjService) {
		this.jcsjService = jcsjService;
	}

	public IDcService getDcService() {
		return dcService;
	}

	public void setDcService(IDcService dcService) {
		this.dcService = dcService;
	}

	public DczdpzModel getDcmodel() {
		return dcmodel;
	}

	public void setDcmodel(DczdpzModel dcmodel) {
		this.dcmodel = dcmodel;
	}

}
