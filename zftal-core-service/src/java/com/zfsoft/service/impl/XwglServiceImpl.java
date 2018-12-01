package com.zfsoft.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.service.BaseServiceImpl;
import com.zfsoft.util.base.StringUtil;
import com.zfsoft.dao.daointerface.IXwglDao;
import com.zfsoft.dao.entities.XwglModel;
import com.zfsoft.service.svcinterface.IXwglService;

/**
 * 
* 
* 类名称：XwglServiceImpl 
* 类描述： 新闻管理实现
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
public class XwglServiceImpl extends BaseServiceImpl<XwglModel,IXwglDao> implements IXwglService {
	

	
	/**
	 * @see  {@link com.zfsoft.IXwglService.xtgl.xwgl.service.XwglService#xgFbxw(String)}.
	 */
	public boolean xgFbxw(String idStr) {
		
		if (!StringUtil.isEmpty(idStr)){
			String[] ids = idStr.split(",");
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			
			for (int i = 0; i < ids.length; i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("xwbh", ids[i]);
			    list.add(map);
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("list", list);
			param.put("sffb", 1);
			int result = dao.batchUpdate(param);
			return result > 0 ? true : false;
		}
		return false;
	}
	
	
	/**
	 * @see  {@link com.zfsoft.IXwglService.xtgl.xwgl.service.XwglService#scXw(String)}.
	 */
	public boolean scXw(String idStr) {
		if (!StringUtil.isEmpty(idStr)){
			String[] ids = idStr.split(",");
			
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			for (int i = 0; i < ids.length; i++) {
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("xwbh", ids[i]);
				list.add(map);
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("list", list);
			int result = dao.batchDelete(param);
			return result > 0 ? true : false;
		}
		return false;
	}
	
	
	
	/**
	 * @see  {@link com.zfsoft.IXwglService.xtgl.xwgl.service.XwglService#xgQxfbxw(String)}.
	 */
	public boolean xgQxfbxw(String idStr)  {
		
		if (!StringUtil.isEmpty(idStr)){
			String[] ids = idStr.split(",");
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			
			for (int i = 0; i < ids.length; i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("xwbh", ids[i]);
			    list.add(map);
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("list", list);
			param.put("sffb", 2);
			int result = dao.batchUpdate(param);
			return result > 0 ? true : false;
		}
		
		return false;
	}


	/**
	 * @see  {@link com.zfsoft.IXwglService.xtgl.xwgl.service.XwglService#xgZdxw(String)}.
	 */
	public boolean xgZdxw(String idStr) {
		
		if (!StringUtil.isEmpty(idStr)){
			String[] ids = idStr.split(",");
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			
			for (int i = 0; i < ids.length; i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("xwbh", ids[i]);
			    list.add(map);
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("list", list);
			param.put("sfzd", 1);
			int result = dao.batchUpdate(param);
			return result > 0 ? true : false;
		}
		return false; 
	}
	
	
	/**
	 * @see  {@link com.zfsoft.IXwglService.xtgl.xwgl.service.XwglService#xgQxzdXw(String)}.
	 */
	public boolean xgQxzdXw(String idStr) {
		
		
		if (!StringUtil.isEmpty(idStr)){
			String[] ids = idStr.split(",");
			List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			
			for (int i = 0; i < ids.length; i++) {
				
				HashMap<String,String> map = new HashMap<String,String>();
				map.put("xwbh", ids[i]);
			    list.add(map);
			}
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("list", list);
			param.put("sfzd", 2);
			int result = dao.batchUpdate(param);
			return result > 0 ? true : false;
		}
		
		return false; 
	}

	
	/**
	 * @see  {@link com.zfsoft.comp.xtgl.xwgl.service.XwglService#fycxXw(XwglModel)}.
	 */
	/*public List<XwglModel> fycxXw(XwglModel model)  {
		
		return dao.getPagedList(model);
	}*/
}
