package com.zfsoft.dao.daointerface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.common.log.User;
import com.zfsoft.dao.entities.IndexModel;

/**
 * 
* 
* 类名称：IndexDao 
* 类描述： 主页面dao
* 创建人：yjd 
* 创建时间：2012-05-09 上午10:50:04 
* 修改备注： 
* @version 
*
 */
public interface IIndexDao extends BaseDao<IndexModel>{
	/**
	 * 查询老师三级功能代码，当前一级菜单下的三级菜单    三级菜单   根据jsxx  仅限老师用户  
	 * @return
	 */
	public List<HashMap<String, String>> cxLsSjGmdmGjJs(Map<String, Object> param) ;
	/**
	 * 查询老师三级功能代码     所有三级菜单   仅限老师用户  
	 * @return
	 */
	public List<HashMap<String, String>> cxLsSjGmdm(Map<String, Object> param) ;
	
	/**
	 * 查询老师二级功能代码 ，当前一级菜单下的二级菜单   根据jsxx  仅限老师用户
	 * @return
	 */
	public List<HashMap<String, String>> cxLsEJGmdmGjJs(Map<String, Object> param) ;
	
	/**
	 * 查询老师一级功能代码，根据jsxx  仅限老师用户
	 * @return
	 */
	public List<HashMap<String, String>> cxLsYjGmdmGjJs(List<String> jsxx) ;
	/**
     * 查询老师一级功能代码，根据jsxx  仅限老师用户，在没有三级菜单的情况下
     * @return
     */
	public List<HashMap<String, String>> cxLsYjGmdmGjJsWsj(List<String> jsxx) ;
	
	/**
	 * 查询学生是三级功能模块代码
	 * @return
	 */
	public List<HashMap<String, String>> cxXsGmdmSj(IndexModel model) ;
	
	/**
	 * 查询学生是一级功能模块代码
	 * @return
	 */
	public List<HashMap<String, String>> cxXsGmdm(IndexModel model) ;
	
	/**
	 * 方法描述: 查询用户角色
	 * 参数 @param model
	 * 参数 @return 
	 * 返回类型 List<HashMap<String,String>> 返回类型
	 */
	public List<HashMap<String, String>> cxJsxxLb(User user) ;
	
	/**
	 * 
	* 方法名: getTeaxx
	* 方法描述: 获取教职工信息
	* 参数 @param model
	* 参数 @return 
	* 返回类型 HashMap<String,String> 
	* @throws
	 */
	public HashMap<String, String> getTeaxx(IndexModel indexModel);
	
	
	public List<HashMap<String, String>> cxYhxx(IndexModel indexModel);
	
	/**
	 * 
	* 方法名: cxLsmrdyym
	* 方法描述: 查询老师一级模块对应页面 （当前用户）
	* 参数 @param param
	* 参数 @return 
	* 返回类型 List<HashMap<String,String>> 
	* @throws
	 */
	public List<HashMap<String,String>> cxLsmrdyym(Map<String,Object> param) ;
	
}