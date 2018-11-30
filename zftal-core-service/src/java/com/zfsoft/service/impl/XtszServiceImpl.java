package com.zfsoft.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.IXtszDao;
import com.zfsoft.dao.entities.XtszModel;
import com.zfsoft.service.svcinterface.IXtszService;
import com.zfsoft.util.date.TimeUtil;

/**
 * 
* 
* 类名称：XtszServiceImpl 
* 类描述： 系统设置 实现
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
public class XtszServiceImpl extends BaseServiceImpl<XtszModel, IXtszDao> implements IXtszService {
	


	/**
	 * @see  {@link com.zfsoft.comp.xtgl.xtsz.service.XtszService#cxNdlb()}.
	 */
	public List cxNdlb(String[][] params) {
		List list = new ArrayList();
		long year = Long.parseLong(TimeUtil.getYear()) ;
		for (int i = -5; i < 5; i++) {
			Map param = new HashMap();

			String text = (year + i) + "";
			String value = text;
			param.put("text", text);
			param.put("value", value);
			list.add(param);
		}

		return list;
	}

	/**
	 * @see  {@link com.zfsoft.comp.xtgl.xtsz.service.XtszService#cxXnlb()}.
	 */
	public List cxXnlb(String[][] params)
			{
		List list = new ArrayList();
		long year = Long.parseLong(TimeUtil.getYear()) ;
		
		for (int i = -5; i < 5; i++) {
			Map param = new HashMap();
			
			String text = (year + i) + "-" + (year + i + 1);
			String value = text;
			param.put("text", text);
			param.put("value", value);
			list.add(param);
		}

		return list;
	}
}
