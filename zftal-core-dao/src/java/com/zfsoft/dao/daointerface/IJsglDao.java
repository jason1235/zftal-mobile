package com.zfsoft.dao.daointerface;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.dao.entities.JsglModel;
import com.zfsoft.dao.entities.NewJsglModel;
import com.zfsoft.dao.entities.YhglModel;


/**
 * 
* 
* 类名称：JsglDao 
* 类描述： 角色管理数据层接口
* 创建人：Administrator 
* 创建时间：2012-4-1 下午03:53:08 
* 修改人：Administrator 
* 修改时间：2012-4-1 下午03:53:08 
* 修改备注： 
* @version 
*
 */
public interface IJsglDao extends BaseDao<JsglModel>{
		/**
		 * 
		* 方法描述: 查询岗位级别代码
		* 参数 @return 参数说明
		* 返回类型 List<HashMap<String,String>> 返回类型
		* @throws
		 */
		public List<JsglModel> cxGwjbList();
		


		/**
		 * 
		* 方法描述: 删除角色信息检查是否可删除
		* 参数 @param array 参数说明
		* 返回类型 List<JsglModel> 返回类型
		* @throws
		 */
		public List<JsglModel> jcSfksc(JsglModel model);
		
		/**
		 * 
		* 方法描述: 查询角色信息列表
		* 参数 @param model
		* 参数 @return 参数说明
		* 返回类型 List<JsglModel> 返回类型
		* @throws
		 */
		public List<JsglModel> cxJsdmList(JsglModel model);
		
		/**
		 * 
		* 方法描述: 查询单个角色信息
		* 参数 @param model
		* 参数 @return 参数说明
		* 返回类型 JsglModel 返回类型
		* @throws
		 */
		public JsglModel cxJsdmxx(JsglModel model);
		
		/**
		 * 
		* 方法描述: 查询功能模块列表根据用户所属角色
		* 参数 @param model
		* 参数 @return
		* 参数 @ 参数说明
		* 返回类型 List<JsglModel> 返回类型
		* @throws
		 */
		public List<JsglModel> cxGnmkdmList(JsglModel model);
		
		
		/**
		 * 
		* 方法描述: 查询所有功能模块列表
		* 参数 @param model
		* 参数 @return
		* 参数 @ 参数说明
		* 返回类型 List<JsglModel> 返回类型
		* @throws
		 */
		public List<JsglModel> cxAllGnmkdmList(JsglModel model);
		
		/**
		 * 
		* 方法描述: 查询功能模块操作代码列表根据用户所属角色
		* 参数 @param model
		* 参数 @return
		* 参数 @ 参数说明
		* 返回类型 List<JsglModel> 返回类型
		* @throws
		 */
		public List<JsglModel> cxGnmkczList(JsglModel model);
		
		/**
		 * 
		* 方法描述: 查询功能模块操作代码列表根据用户所属角色,6.0.1版本需要使用
		* 参数 @param model
		* 参数 @return
		* 参数 @ 参数说明
		* 返回类型 List<JsglModel> 返回类型
		* @throws
		 */
		public List<JsglModel> cxGnmkczListNew(JsglModel model);
		
		
		/** 方法描述: 查询功能模块操作代码列表
		* 参数 @param model
		* 参数 @return
		* 参数 @ 参数说明
		* 返回类型 List<JsglModel> 返回类型
		* @throws
		 */
		public List<JsglModel> cxAllGnmkczList(JsglModel model);
		
		/**
		 * 
		* 方法描述: 删除角色功能权限 
		* 参数 @param model
		* 参数 @ 参数说明
		* 返回类型 void 返回类型
		* @throws
		 */
		public int scJsgnmkdmxx(Map<String, Object> param);
		
		/**
		 * 
		* 方法描述: 插入角色功能权限
		* 参数 @param model
		* 参数 @ 参数说明
		* 返回类型 void 返回类型
		* @throws
		 */
		public int zjJsgnmkdmxx(JsglModel model);
		
		/**
		 * 
		* 方法描述: 查询单个角色功能权限列表
		* 参数 @param model
		* 参数 @return
		* 参数 @ 参数说明
		* 返回类型 List<JsglModel> 返回类型
		* @throws
		 */
		public List<JsglModel> cxJsGnqxList(JsglModel model);
		
		/**
		 * 
		* 方法描述: 保存角色分配用户信息
		* 参数 @param model
		* 参数 @return
		* 参数 @ 参数说明
		* 返回类型 List<JsglModel> 返回类型
		* @throws
		 */
		public int zjJsfpyhxx(JsglModel model);
		
		/**
		 * 
		* 方法描述: 删除角色分配用户信息
		* 参数 @param model
		* 参数 @return
		* 参数 @ 参数说明
		* 返回类型 List<JsglModel> 返回类型
		* @throws
		 */
		public int scJsfpyhxx(JsglModel model);
		
		/**
		 * 方法描述: 查询用户对应的功能代码（三级功能代码） 老师
		 * 参数 @param yhm  用户名
		 * 参数 @return
		 * 参数 @ 参数说明
		 * 返回类型 List<JsglModel> 返回类型
		 * 修改人：yijd 
		 * 修改时间：2012-4-24 下午02:00:08 
		 * 修改备注：
		 */
		public List<JsglModel> cxSjGndm(String yhm);
		
		/**
		 * 方法描述: 查询用户对应的功能代码（二级功能代码） 老师
		 * 参数 @param yhm  用户名
		 * 参数 @return
		 * 参数 @ 参数说明
		 * 返回类型 List<JsglModel> 返回类型
		 * 修改人：yijd 
		 * 修改时间：2012-4-24 下午02:00:08 
		 * 修改备注：
		 */
		public List<JsglModel> cxErGndm(String yhm);
		
		/**
		 * 方法描述: 查询用户对应的功能代码（一级功能代码） 老师
		 * 参数 @param yhm  用户名
		 * 参数 @return
		 * 参数 @ 参数说明
		 * 返回类型 List<JsglModel> 返回类型
		 * 修改人：yijd 
		 * 修改时间：2012-4-24 下午02:00:08 
		 * 修改备注：
		 */
		public List<JsglModel> cxYiGndm(String yhm);
		
		/**
		 * 方法描述: 查询用户对应的功能操作
		 * 参数 @param yhm  用户名
		 * 参数 @return
		 * 参数 @ 参数说明
		 * 返回类型 List<JsglModel> 返回类型
		 * 修改人：yijd 
		 * 修改时间：2012-4-24 下午02:00:08 
		 * 修改备注：
		 */
		public List<JsglModel> cxYhCz(String yhm);
		
		/**
		 * 方法描述: 查询用户对应的功能代码（三级功能代码) 学生
		 * 参数 @return
		 * 参数 @ 参数说明
		 * 返回类型 List<JsglModel> 返回类型
		 * 修改人：yijd 
		 * 修改时间：2012-4-24 下午02:00:08 
		 * 修改备注：
		 */
		public List<JsglModel> cxSjGndmXs();
		
		/**
		 * 方法描述: 查询用户对应的功能代码（二级功能代码） 学生
		 * 参数 @return
		 * 参数 @ 参数说明
		 * 返回类型 List<JsglModel> 返回类型
		 * 修改人：yijd 
		 * 修改时间：2012-4-24 下午02:00:08 
		 * 修改备注：
		 */
		public List<JsglModel> cxErGndmXs();
		
		/**
		 * 方法描述: 查询用户对应的功能代码（一级功能代码） 学生
		 * 参数 @return
		 * 参数 @ 参数说明
		 * 返回类型 List<JsglModel> 返回类型
		 * 修改人：yijd 
		 * 修改时间：2012-4-24 下午02:00:08 
		 * 修改备注：
		 */
		public List<JsglModel> cxYiGndmXs();

		/**
		 * 方法描述: 根据职工号查询角色信息
		 * 参数 @return
		 * 参数 @ 参数说明
		 * 返回类型 List<JsglModel> 返回类型
		 */
		public List<JsglModel> cxJsxxZgh(Map<String,String> map);
		
		/**
		 * 方法描述: 查询学院组织机构列表
		 * 参数 @return
		 * 参数 @ 参数说明
		 * 返回类型 List<JsglModel> 返回类型
		 */
		public List<HashMap<String, String>> queryXyZzjg(Map<String,String> map);
		
		/**
		 * 方法描述: 查询专业组织机构列表
		 * 参数 @return
		 * 参数 @ 参数说明
		 * 返回类型 List<JsglModel> 返回类型
		 */
		public List<HashMap<String, String>> queryZyZzjg(Map<String,String> map); 
		
		/**
		 * 方法描述: 查询专业组织机构列表
		 * 参数 @return
		 * 参数 @ 参数说明
		 * 返回类型 List<JsglModel> 返回类型
		 */
		public List<HashMap<String, String>> queryNjZzjg(Map<String,String> map);
		
		/**
		 * 方法描述: 查询班级组织机构列表
		 * 参数 @return
		 * 参数 @ 参数说明
		 * 返回类型 List<JsglModel> 返回类型
		 */
		public List<HashMap<String, String>> queryBjZzjg(Map<String,String> map);
		
		/**
	     * 未分配用户
	     * @param model
	     * @return
	     * @
	     * @author HuangXiaoping created on 2012-7-18 
	     * @since ZFTAL 1.0
	     */
	    public List<YhglModel> getPagedListWfpYh(JsglModel model);
	    
	    /**
	     * 已分配用户
	     * @param model
	     * @return
	     * @
	     * @author HuangXiaoping created on 2012-7-18 
	     * @since ZFTAL 1.0
	     */
	    public List<YhglModel> getPagedListYfpYh(JsglModel model);
	    
	    /**
	     * 查询所有一级功能模块列表
	     * @return
	     */
	    public List<JsglModel> getGnmkYj(JsglModel model);
	    
	    /**
	     * 查询该用户是否具有二级授权功能
	     * @return
	     */
	    public List<JsglModel> getYhEjsq(JsglModel jsglModel); 
	    
	    /**
	     * 根据模块IDs,查询角色信息列表 ,排除可二级授权的角色
	     * @param gndms
	     * @return
	     */
	    public List<JsglModel> getJsxxGnmkId(JsglModel model);
		/**
		 * 
		* 方法描述: 删除角色分配用户信息(单条)
		* 参数 @param model
		* 参数 @return
		* 参数 @ 参数说明
		* 返回类型 List<JsglModel> 返回类型
		* @throws
		 */
		public int scJsfpyhxxSingle(JsglModel model);



		public int getJsglModeCount(NewJsglModel model);



		public List<NewJsglModel> getJsglModeList(NewJsglModel model);
		
		public List<NewJsglModel> getAllJsglist(NewJsglModel model);



		/** 
		* @author: zhangxu
		* @Title: insertMenuByJsdm 
		* @Description: 
		* @param @param param
		* @param @return    设定文件 
		* @return Object    返回类型 
		* @throws 
		*/
		public void insertMenuByJsdm(Map<String, Object> param);



		/** 
		* @author: zhangxu
		* @Title: deleteMenuByJsdm 
		* @Description: 
		* @param @param param
		* @param @return    设定文件 
		* @return Object    返回类型 
		* @throws 
		*/
		public void deleteMenuByJsdm(Map<String, Object> param);



		/** 
		* @author: zhangxu
		* @Title: cxJsgnqxListNew 
		* @Description: 
		* @param @param model
		* @param @return    设定文件 
		* @return List<JsglModel>    返回类型 
		* @throws 
		*/
		public List<JsglModel> cxJsgnqxListNew(JsglModel model);
}
