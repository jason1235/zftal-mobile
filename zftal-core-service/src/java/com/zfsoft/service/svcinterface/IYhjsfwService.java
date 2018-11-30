package com.zfsoft.service.svcinterface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.service.BaseService;
import com.zfsoft.dao.entities.YhjsfwModel;

/**
 * 
 * 类名称：IYhjsfwService 
 * 类描述：用户角色范围Service
 * 创建人：caozf
 * 创建时间：2012-7-12
 */
public interface IYhjsfwService extends BaseService<YhjsfwModel>{
	
	/**
	 * 测试拦截学生家庭表
	 * @param t
	 * @return
	 * @
	 */
	public List<HashMap<String, String>> queryXsjtb(Map<String,String> map) ;
	
	/**
	 * 保存用户角色数据范围
	 * @param map
	 * @return
	 * @
	 */	
	public boolean zjYhjsfw(Map<String, String> map) ;	
	
	/**
	 * 删除用户角色数据范围
	 * @param model
	 * @return
	 * @
	 */
	public boolean scYhjsfw(YhjsfwModel model) ;
	
	/**
	 * 删除数据范围组
	 * @param map
	 * @return
	 * @
	 */
	public boolean scSjfwz(YhjsfwModel model)  ;	
	
	/**
	 * 根据用户查询数据范围组
	 * @param t
	 * @return
	 * @
	 */
	public List<YhjsfwModel> cxSjfwYh (Map<String,String> map) ;	
}
