package com.zfsoft.dao.daointerface;

import java.util.List;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.dao.entities.JcsjModel;

/**
 * 
* 
* 类名称：JcsjDao 
* 类描述：基础数据DAO
* 创建人：xucy 
* 创建时间：2012-4-13 下午01:45:13 
* 修改人：xucy 
* 修改时间：2012-4-13 下午01:45:13 
* 修改备注： 
* @version 
*
 */
public interface IJcsjDao extends BaseDao<JcsjModel>{

	/**
	 * 
	* 方法描述: 查询基础数据列表(不分页)
	* 参数 @return
	* 参数 @throws Exception 参数说明
	* 返回类型 List<JcsjModel> 返回类型
	* @throws
	 */
	public List<JcsjModel> cxJcsjList(JcsjModel model);
	
	
	/**
	 * 
	* 方法描述:基础数据列表
	* 参数 @param array 参数说明
	* 返回类型  List<JcsjModel> 返回类型
	* @throws
	 */
	public  List<JcsjModel> getJcsjList(String lxdm);
	
}
