package com.zfsoft.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.dao.daointerface.IJcsjDao;
import com.zfsoft.dao.entities.JcsjModel;
import com.zfsoft.service.svcinterface.IJcsjService;

/**
 * 
* 
* 类名称：JcsjServiceImpl 
* 类描述： 基础数据业务处理实现类
* 创建人：xucy 
* 创建时间：2012-4-13 下午01:44:39 
* 修改人：xucy 
* 修改时间：2012-4-13 下午01:44:39 
* 修改备注： 
* @version 
*
 */
public class JcsjServiceImpl extends BaseServiceImpl<JcsjModel, IJcsjDao> implements IJcsjService {

	//查询基础数据列表(不分页用于导出)
	public List<JcsjModel> cxJcsjList(JcsjModel model){
		return dao.cxJcsjList(model);
	}

	//删除基础数据
	public boolean scJcsj(JcsjModel model) {
	
			String pkValue = model.getPkValue();
			//TODO pkValue如果为NULL，这里要报空指针了
			String[] pks = pkValue.split(",");
			
			List<JcsjModel> list =new ArrayList<JcsjModel>();
			for (int i = 0; i < pks.length; i++) {
				JcsjModel jcsjmodel= new JcsjModel();
				jcsjmodel.setPkValue(pks[i]);
				list.add(jcsjmodel);
			}
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("list", list);
			
			int result =dao.batchDelete(param);
			return result > 0 ? true : false;
	
	}
	
	//根据类型代码查询基础数据
	public List<JcsjModel> getJcsjList(String lxdm){
		return dao.getJcsjList(lxdm);
	}
}
