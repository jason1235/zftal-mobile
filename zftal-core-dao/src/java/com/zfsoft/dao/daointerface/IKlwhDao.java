package com.zfsoft.dao.daointerface;

import java.util.List;
import java.util.Map;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.dao.entities.XsmmModel;

/**
 * 
* 
* 类名称：KlwhDao 
* 类描述： 用户管理DAO
* 创建人：xucy 
* 创建时间：2012-4-17 下午01:45:13 
* @version 
*
 */
public interface IKlwhDao extends BaseDao<XsmmModel>{

	/**
	 * 
	* 方法描述: 学生列表
	* 参数 @return
	* 参数 @throws Exception 参数说明
	* 返回类型 List<XsmmModel> 返回类型
	* @throws
	 */
	public List<XsmmModel> fycxXsxxList(XsmmModel model);
	
	/**
	 * 
	* 方法描述: 根据学号获取学生信息
	* 参数 @return
	* 参数 @throws Exception 参数说明
	* 返回类型 List<XsmmModel> 返回类型
	* @throws
	 */
	public XsmmModel getXsxxByxh(XsmmModel model);
	
	/**
	 * 
	* 方法描述: 根据学号获取学生信息列表
	* 参数 @return
	* 参数 @throws Exception 参数说明
	* 返回类型 List<XsmmModel> 返回类型
	* @throws
	 */
	public List<XsmmModel> getXsxxList(XsmmModel model);

	//批量密码初始化
	public int plCsh(XsmmModel model)throws Exception;
	
	//批量密码初始化
	public int sdplCsh(Map<String, Object> param)throws Exception;
	
	//全部密码初始化
	public int qbCsh(XsmmModel model)throws Exception;
	
}
