package com.zfsoft.dao.daointerface;



import java.util.List;
import java.util.Map;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.dao.entities.DczdpzModel;


/**
 * 
* 
* 类名称：DcDao 
* 类描述： 导出DcDao
* 创建人：xucy 
* 创建时间：2012-4-24 上午08:41:27 
* 修改人：xucy 
* 修改时间：2012-4-24 上午08:41:27 
* 修改备注： 
* @version 
*
 */
public interface IDcDao extends BaseDao<DczdpzModel>{

	/**
	 * 
	* 方法描述: 查询全部可以导出字段
	* 参数 @return 参数说明
	* 返回类型  List<DczdpzModel>  返回类型
	*/
	public List<DczdpzModel> cxDczdList(DczdpzModel model);
	
	/**
	 * 
	* 方法描述: 根据职工号查询全部可以导出字段
	* 参数 @return 参数说明
	* 返回类型  List<DczdpzModel>  返回类型
	*/
	public List<DczdpzModel> cxDczdListByPublic(Map<String, Object> param);
	
	/**
	 * 
	* 方法描述: 根据职工号查询全部可以导出字段
	* 参数 @return 参数说明
	* 返回类型  List<DczdpzModel>  返回类型
	*/
	public List<DczdpzModel> cxDczdListByZgh(DczdpzModel model);
	
	
	/**
	 * 
	* 方法描述: 查询需要导出字段
	* 参数 @return 参数说明
	* 返回类型  List<DczdpzModel>  返回类型
	*/
	public List<DczdpzModel> cxZdList(DczdpzModel model) ;

	/**
	 * 
	* 方法描述: 根据职工号查询需要导出字段
	* 参数 @return 参数说明
	* 返回类型  List<DczdpzModel>  返回类型
	*/
	public List<DczdpzModel> cxZdListByZgh(DczdpzModel model) ;
	
	/**
	 * 
	* 方法描述: 查询默认导出字段
	* 参数 @return 参数说明
	* 返回类型 DczdpzModel  返回类型
	*/
	public DczdpzModel cxMrzd(DczdpzModel model) ;
	
	/**
	 * 
	* 方法描述: 根据职工号查询默认导出字段
	* 参数 @return 参数说明
	* 返回类型 DczdpzModel  返回类型
	*/
	public DczdpzModel cxMrzdByZgh(DczdpzModel model) ;
	
	/**
	 * 
	* 方法描述: 查询选择状态字段
	* 参数 @return 参数说明
	* 返回类型  DczdpzModel  返回类型
	*/
	public DczdpzModel cxXzzd(DczdpzModel model) ;
	
	/**
	 * 
	* 方法描述: 根据职工号查询选中状态字段
	* 参数 @return 参数说明
	* 返回类型  DczdpzModel  返回类型
	*/
	public DczdpzModel cxXzzdByZgh(DczdpzModel model) ;
	
	/**
	 * 
	* 方法描述: 删除导出字段
	* 参数 @return 参数说明
	* 返回类型 void 返回类型
	*/
	public int scDczd(DczdpzModel model) ;
	
	/**
	 * 
	* 方法描述: 查询导出字段model
	* 参数 @return 参数说明
	* 返回类型  DczdpzModel  返回类型
	*/
	public DczdpzModel cxDczdpzMode(DczdpzModel model) ;
	
	/**
	 * 
	* 方法描述: 保存设置导出字段
	* 参数 @return 参数说明
	* 返回类型 void 返回类型
	*/
	public void bcFxzzd(Map param) ;
	
	
}
