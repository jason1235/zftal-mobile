package com.zfsoft.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.IYhjsfwDao;
import com.zfsoft.dao.entities.YhjsfwModel;
import com.zfsoft.service.svcinterface.IYhjsfwService;

/**
 * 
 * 类名称：YhjsfwService 
 * 类描述：用户角色范围Service
 * 创建人：caozf
 * 创建时间：2012-7-12
 */
public class YhjsfwServiceImpl extends BaseServiceImpl<YhjsfwModel, IYhjsfwDao>
		implements IYhjsfwService {

	@Override
	public List<HashMap<String, String>> queryXsjtb(Map<String, String> map)
			 {
		 
		return dao.queryXsjtbByScope(map);
	}

	@Override
	public boolean zjYhjsfw(Map<String, String> map)  {
		int result = dao.zjYhjsfw(map);
//		return result > 0 ? true : false;
		return true;
	}
	
	@Override
	public boolean scYhjsfw(YhjsfwModel model)  {
		int result = dao.scYhjsfw(model);
		//return result > 0 ? true : false;
		//若没有作用到数据行，则返回都为成功
		return true;
	}

	@Override
	public List<YhjsfwModel> cxSjfwYh(Map<String,String> map)  {

		return dao.cxSjfwYh(map);
	}

	@Override
	public boolean scSjfwz(YhjsfwModel model)  {
		int result = dao.scSjfwz(model);
 		//return result > 0 ? true : false;
		//若没有作用到数据行，则返回都为成功
		return true;
	}

}
