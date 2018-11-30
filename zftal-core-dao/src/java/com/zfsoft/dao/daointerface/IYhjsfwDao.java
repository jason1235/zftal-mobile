package com.zfsoft.dao.daointerface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.dao.entities.YhjsfwModel;

/**
 * 
 * 类名称：IYhsjfwDao 
 * 类描述：用户数据范围
 * 创建人：caozf
 * 创建时间：2012-7-10
 */
public interface IYhjsfwDao extends BaseDao<YhjsfwModel>{

	/**
	 * 测试拦截学生家庭表
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public List<HashMap<String, String>> queryXsjtbByScope(Map<String,String> map);
	
	/**
	 * 保存用户角色数据范围
	 * @param map
	 * @return
	 * @throws Exception
	 */	
	public int zjYhjsfw(Map<String, String> map);
	
	/**
	 * 删除用户角色数据范围
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int scYhjsfw(YhjsfwModel model) ;
	
	/**
	 * 删除数据范围组
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int scSjfwz(YhjsfwModel model) ;
	
	/**
	 * 根据用户查询数据范围组
	 * @param t
	 * @return
	 * @throws Exception
	 */
	public List<YhjsfwModel> cxSjfwYh (Map<String,String> map);
	 
	/**
	 * 根据用户角色,删除数据范围组记录
	 * @param model
	 * @return
	 */	
	public int scSjfwzYhJs(YhjsfwModel model);
	
	/**
	 * 根据用户角色,删除数据范围记录
	 * @param model
	 * @return
	 */	
	public int scSjfwYhJs(YhjsfwModel model);
}
