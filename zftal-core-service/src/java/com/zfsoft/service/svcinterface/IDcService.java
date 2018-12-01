package com.zfsoft.service.svcinterface;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.zfsoft.common.log.User;
import com.zfsoft.common.service.BaseService;
import com.zfsoft.dao.entities.DczdpzModel;

/**
 * 
* 
* 类名称：DcServiceImpl 
* 类描述： 导出Service
* 创建人：xucy 
* 创建时间：2012-4-24 上午08:41:27 
* 修改人：xucy 
* 修改时间：2012-4-24 上午08:41:27 
* 修改备注： 
* @version 
*
 */
public interface IDcService extends BaseService<DczdpzModel>{
	
	/**
	 * 
	* 方法描述: 查询导出字段
	* 参数 @return 参数说明
	* 返回类型  List<DczdpzModel>  返回类型
	*/
	public List<DczdpzModel> cxDczdList(DczdpzModel model,User user); 
	
	/**
	 * 
	* 方法描述: 查询需要导出字段
	* 参数 @return 参数说明
	* 返回类型  List<DczdpzModel>  返回类型
	*/
	public List<DczdpzModel> cxZdList(DczdpzModel model,User user); 
	
	/**
	 * 
	* 方法描述: 查询默认导出字段
	* 参数 @return 参数说明
	* 返回类型  DczdpzModel  返回类型
	*/
	public DczdpzModel cxMrzd(DczdpzModel model,User user);
	
	/**
	 * 
	* 方法描述: 查询选择状态字段
	* 参数 @return 参数说明
	* 返回类型 DczdpzModel 返回类型
	*/
	public DczdpzModel cxXzzd(DczdpzModel model,User user);

	/**
	 * 
	* 方法描述: 保存设置导出字段
	* 参数 @return 参数说明
	* 返回类型 boolean  返回类型
	*/
	public boolean bcZdsz(DczdpzModel model,User user);
	
	/**
	 * 
	* 方法描述: 导出数据
	* 参数 @return 参数说明
	* 返回类型  返回类型
	*/
	public String dcSj(DczdpzModel model,List<?> list,User user,HttpServletResponse response);
	
}

