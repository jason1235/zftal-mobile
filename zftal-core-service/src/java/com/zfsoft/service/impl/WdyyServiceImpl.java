package com.zfsoft.service.impl;

import java.util.List;

import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.IWdyyDao;
import com.zfsoft.dao.entities.WdyyModel;
import com.zfsoft.service.svcinterface.IWdyyService;


/**
 * 
* 
* 类名称：WdyyServiceImpl 
* 类描述： 我的应用业务处理实现类
* 创建人：yijd
* 创建时间：2012-5-7 上午17:22:13 
* 修改备注： 
* @version 
*
 */
public class WdyyServiceImpl extends BaseServiceImpl<WdyyModel, IWdyyDao> implements IWdyyService  {
	/**
	 * 删除我的应用
	 */
	public boolean scWdyy(WdyyModel model) {
		int result=dao.scWdyy(model);
		if(result>0){
			return true;
		}
		return false;
	}

	public List<WdyyModel> cxLsWdyy(WdyyModel model) {
		return dao.cxLsWdyy(model);
	}

	public List<WdyyModel> cxXsWdyy(WdyyModel model) {
		return dao.cxXsWdyy(model);
	}
	
}
