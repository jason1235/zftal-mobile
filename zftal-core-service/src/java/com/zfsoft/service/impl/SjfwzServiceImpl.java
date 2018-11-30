package com.zfsoft.service.impl;

import java.util.List;
import java.util.Map;

import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.ISjfwzDao;
import com.zfsoft.dao.entities.SjfwzModel;
import com.zfsoft.service.svcinterface.ISjfwzService;

/**
 * 
 * 类名称： SjfwzServiceImpl
 * 类描述：数据范围组Service
 * 创建人：caozf
 * 创建时间：2012-7-12
 */
public class SjfwzServiceImpl extends BaseServiceImpl<SjfwzModel, ISjfwzDao>
		implements ISjfwzService {

	@Override
	public List<SjfwzModel> cxSjfwzYhjs(Map<String, Object> maps)
			{
		return dao.cxSjfwzYhjs(maps);
	}


}
