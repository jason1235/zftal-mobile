package com.zfsoft.service.svcinterface;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.service.BaseService;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.NewJsglModel;
import com.zfsoft.dao.entities.YhglModel;
import com.zfsoft.dao.page.PageList;





/**
 * 
* 
* 类名称：JsglService 
* 类描述： 角色管理业务接口
* 创建人：Administrator 
* 创建时间：2012-4-1 下午03:52:26 
* 修改人：Administrator 
* 修改时间：2012-4-1 下午03:52:26 
* 修改备注： 
* @version 
*
 */
public interface IJsglService extends BaseService<JsglModel>{
	
	public List<NewJsglModel> getAllJsglist(NewJsglModel model);

	/**
	 * 
	* 方法描述: 查询岗位级别列表
	* 参数 @return 参数说明
	* 返回类型 List<HashMap<String,String>> 返回类型
	* @throws
	 */
	public List<JsglModel> cxGwjbList();
	
	
	/**
	 * 
	* 方法描述: 删除角色代码信息
	* 参数 @return 参数说明
	* 返回类型 List<HashMap<String,String>> 返回类型
	* @throws
	 */
	public String scJsdmxx(JsglModel model);
	
	
	/**
	 * 
	* 方法描述: 查询功能模块代码列表根据用户所属角色
	* 参数 @return 参数说明
	* 返回类型 List<HashMap<String,String>> 返回类型
	* @throws
	 */
	public List<JsglModel> cxGnmkdmList(JsglModel model);
	
	/**
	 * 
	* 方法描述: 查询所有功能模块代码列表
	* 参数 @return 参数说明
	* 返回类型 List<JsglModel> 返回类型
	* @throws
	 */
	public List<JsglModel> cxAllGnmkdmList(JsglModel model);
	
	/**
	 * 
	* 方法描述: 查询第一级功能模块代码
	* 参数 @return 参数说明
	* 返回类型 List<HashMap<String,String>> 返回类型
	*/
	//public List<JsglModel> cxDyjGnmkdm(JsglModel model) ;
	
	/**
	 * 
	* 方法描述: 查询功能模块操作代码列表
	* 参数 @return 参数说明
	* 返回类型 List<JsglModel>
	*/
	public List<JsglModel> cxGnmkczList(JsglModel model) ;
	
	/**
	 * 
	* 方法描述: 查询功能模块操作代码列表6.0.1重构版本
	* 参数 @return 参数说明
	* 返回类型 List<JsglModel>
	*/
	public List<JsglModel> cxJsgnqxListNew(JsglModel model) ;
	
	/**
	 * 方法描述: 查询功能模块操作权限列表根据用户所属角色
	 * 参数 @param model
	 * 返回类型 List<JsglModel>
	 */
	public List<JsglModel> cxAllGnmkczList(JsglModel model);
	
	/**
	 * 
	* 方法描述: 查询功能模块操作权限字符串
	* 参数 @return 参数说明
	* 返回类型  拼接好的功能操作代码字符串(N010101:zj:增加-N010101:xg:修改-N010101:sc:删除)
	*         失败则返回""
	*/
	public String getGnmkczStr(JsglModel model) ;
	
	/**
	 * 
	* 方法描述: 批量保存角色功能模块权限
	* 参数 @return 参数说明
	* 返回类型  
	*/
	public boolean zjJsGnmkqxBatch(JsglModel model);
	
	/**
	 * 
	* 方法描述: 删除角色功能模块权限
	* 参数 @return 参数说明
	* 返回类型  
	*/
	public boolean scJsgnqx(JsglModel model);
	
	/**
	 * 
	* 方法描述: 查询单个角色功能权限代码
	* 参数 @return 参数说明
	* 返回类型  
	*/
	public List<JsglModel> cxJsgnqxList(JsglModel model);
	
	/**
	 * 
	* 方法描述: 查询单个角色功能权限代码
	* 参数 @return 参数说明
	* 返回类型  
	*/
	public String getJsgnqxStr(JsglModel model);

	/**
	 * 
	* 方法描述: 保存单个角色单个用户
	* 参数 @return 参数说明
	* 返回类型  boolean 返回类型
	*/
	public boolean saveJsyhfpxx(JsglModel model);
	
	/**
	 * 
	* 方法描述:  查询角色所分配用户信息
	* 参数 @return 参数说明
	* 返回类型 String  返回类型
	*/
	public String getJsyhxxList(List<YhglModel> list) ;
	
	/**
	 * 
	 * 方法描述:  查询功能模块
	 * 参数 @return 参数说明
	 * 返回类型 String  返回类型
	*/
	public List<JsglModel> getAllGnmkList(JsglModel model);
	
	/**
	 * 方法描述: 根据职工号查询角色信息
	 * 参数 @return
	 * 参数 @ 参数说明
	 * 返回类型 List<JsglModel> 返回类型
	 */
	public List<JsglModel> cxJsxxZgh(Map<String,String> map) ;	
	
	/**
	 * 
	 * 方法描述:  查询学院组织机构列表
	 * 参数 @return 参数说明
	 * 返回类型 String  返回类型
	*/
	public List<HashMap<String, String>> queryXyZzjg(Map<String,String> map) ;

	/**
	 * 
	 * 方法描述:  查询专业组织机构列表
	 * 参数 @return 参数说明
	 * 返回类型 String  返回类型
	*/
	public List<HashMap<String, String>> queryZyZzjg(Map<String,String> map) ;
	
	/**
	 * 
	 * 方法描述:  查询年级组织机构列表
	 * 参数 @return 参数说明
	 * 返回类型 String  返回类型
	*/
	public List<HashMap<String, String>> queryNjZzjg(Map<String,String> map) ;
	
	/**
	 * 
	 * 方法描述:  查询班级组织机构列表
	 * 参数 @return 参数说明
	 * 返回类型 String  返回类型
	*/
	public List<HashMap<String, String>> queryBjZzjg(Map<String,String> map) ;	

	/**
	 * 未分配用户
	 * @param model
	 * @return
	 * @
	 * @author HuangXiaoping created on 2012-7-18 
	 * @since ZFTAL 1.0
	 */
	public List<YhglModel> getPagedListWfpYh(JsglModel model) ;
	
	/**
     * 已分配用户
     * @param model
     * @return
     * @
     * @author HuangXiaoping created on 2012-7-18 
     * @since ZFTAL 1.0
     */
    public List<YhglModel> getPagedListYfpYh(JsglModel model) ;
    
    /**
     * 查询所有一级功能模块列表
     * @return
     */
    public List<JsglModel> getGnmkYj(JsglModel model);
    
    /**
     * 查询该用户是否具有二级授权功能
     * @return
     */
    public boolean getYhEjsq(JsglModel jsglModel); 
	// 保存角色对应用户信息
	public boolean saveFp(JsglModel model)  ;
	
	/**
	 * 方法描述: 删除用户角色信息
	 */
	public boolean removeFp(JsglModel model);


	public PageList<NewJsglModel> getJsglModelList(NewJsglModel model);


	public void remove(String jsdm);


	public void insert(NewJsglModel newJsglQuery);


	public void update(NewJsglModel newJsglQuery);


	/** 
	* @author: zhangxu
	* @Title: insertMenuByJsdm 
	* @Description: 
	* @param @param param    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void insertMenuByJsdm(Map<String, Object> param);


	/** 
	* @author: zhangxu
	* @Title: deleteMenuByJsdm 
	* @Description: 
	* @param @param param    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	public void deleteMenuByJsdm(Map<String, Object> param);

}
