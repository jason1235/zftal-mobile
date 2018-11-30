package com.zfsoft.globalweb.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.xwork2.util.ValueStack;
import com.zfsoft.common.action.BaseAction;
import com.zfsoft.dao.entities.BjdmModel;
import com.zfsoft.dao.entities.BmdmModel;
import com.zfsoft.dao.entities.ZydmModel;
import com.zfsoft.service.common.ICommonSqlService;

/**
 * 
* 
* 类名称：CommonSqlAction 
* 类描述： 公共Action
* 创建人：caozf 
* 创建时间：2012-7-17 上午08:41:27 
* 修改备注： 
* @version 
*
 */
public class CommonSqlAction extends BaseAction  {

	private static final long serialVersionUID = 1L;
	
	private BjdmModel bjdmMode;
	
	private ICommonSqlService commonSqlService;

	/**
	 * 查询所有/该学院下的专业代码
	 * @return
	 */
	public String cxZydm(){
		ValueStack vs = getValueStack();
		Map<String,String> map = new HashMap<String,String>();
		String bmdm_id = getRequest().getParameter("bmdm_id");
		map.put("bmdm_id_lsbm", bmdm_id);
		try {
			List<ZydmModel> zydms = commonSqlService.queryZydm(map);
			vs.set(DATA,zydms);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	/**
	 * 查询所有学院代码
	 * @return
	 */
	public String cxAllXydm(){
		ValueStack vs = getValueStack();
		try {
			List<BmdmModel> bmdms = commonSqlService.queryAllXy();
			vs.set(DATA,bmdms);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	/**
	 * 查询班级代码
	 * @return
	 */
	public String cxBjdm(){
		ValueStack vs = getValueStack();
		try {
			List<BjdmModel> bjdms = commonSqlService.queryBjdm(bjdmMode);
			vs.set(DATA,bjdms);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	
	public ICommonSqlService getCommonSqlService() {
		return commonSqlService;
	}

	public void setCommonSqlService(ICommonSqlService commonSqlService) {
		this.commonSqlService = commonSqlService;
	}

	public BjdmModel getBjdmMode() {
		return bjdmMode;
	}

	public void setBjdmMode(BjdmModel bjdmMode) {
		this.bjdmMode = bjdmMode;
	}
	
	
}

