package com.zfsoft.service.impl;

import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.IXsglfwDao;
import com.zfsoft.dao.entities.XsglfwModel;
import com.zfsoft.service.svcinterface.IXsglfwService;

/**
 * 
* 
* 类名称：XsglfwService 
* 类描述：学生管理范围业务处理接口
* 创建人：caozf 
* 创建时间：2012-6-25 下午01:44:18 
* @version 
*
 */
public class XsglfwServiceImpl extends BaseServiceImpl<XsglfwModel,IXsglfwDao> implements IXsglfwService {

	//增加学生管理范围信息
	public void insertXsglfw(XsglfwModel model) throws Exception{
		dao.insert(model);
	}
	
}
