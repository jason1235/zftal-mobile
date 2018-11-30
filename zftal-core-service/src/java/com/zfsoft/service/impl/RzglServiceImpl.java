package com.zfsoft.service.impl;


import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.IRzglDao;
import com.zfsoft.dao.entities.RzglModel;
import com.zfsoft.service.svcinterface.IRzglService;

/**
 * 
* 
* 类名称：RzglServiceImpl 
* 类描述：日志管理实现
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
public class RzglServiceImpl extends BaseServiceImpl<RzglModel,IRzglDao> implements IRzglService {
	

	/*private RzglDao dao;

	
	public RzglDao getDaoBase() {
		return dao;
	}
	
	public void setDao(RzglDao dao) {
		this.dao = dao;
	}*/

	/**
	 * @see  {@link com.zfsoft.comp.xtgl.rzgl.service.RzglService#cxRz(RzglModel)}.
	 *//*
	public List<RzglModel> cxRz(RzglModel model) throws Exception {
		
		return dao.getPagedList(model);
	}

	*//**
	 * @see  {@link com.zfsoft.comp.xtgl.rzgl.service.RzglService#cxDtrz(String)}.
	 *//*
	public RzglModel cxDtrz(String czbh) throws Exception {
	
		return (RzglModel) dao.getModel(czbh);
	}*/
}