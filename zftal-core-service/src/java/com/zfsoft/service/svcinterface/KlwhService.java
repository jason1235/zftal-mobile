package com.zfsoft.service.svcinterface;



import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.zfsoft.common.service.BaseService;
import com.zfsoft.dao.entities.XsmmModel;


/**
* 类名称：KlwhService 
* 类描述： 用户信息业务处理接口
* 创建人：xucy 
* 创建时间：2012-4-17 下午01:44:18 
* @version 
*
 */
public interface KlwhService extends BaseService<XsmmModel> {

	/**
	 * 
	* 方法描述: 密码批量初始化
	* 参数 @return 参数说明
	* 返回类型  boolean 返回类型
	*/
	public boolean plCsh(XsmmModel model)throws Exception;
	
	/**
	 * 
	* 方法描述: 密码全部初始化
	* 参数 @return 参数说明
	* 返回类型  boolean 返回类型
	*/
	public boolean qbCsh(XsmmModel model,HttpServletResponse response)throws Exception;
	
	/**
	 * 
	* 方法描述: 根据学号获取学生信息列表
	* 参数 @return
	* 参数 @throws Exception 参数说明
	* 返回类型 List<XsmmModel> 返回类型
	* @throws
	 */
	public List<XsmmModel> getXsxxList(XsmmModel model) throws Exception;
}
