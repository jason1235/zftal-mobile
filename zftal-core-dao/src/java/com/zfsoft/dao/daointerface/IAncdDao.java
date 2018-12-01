package com.zfsoft.dao.daointerface;


import java.util.List;

import com.zfsoft.dao.entities.AncdModel;


/**
 * 
* 
* 类名称：AncdDao 
* 类描述：按钮菜单DAO
* 创建人：yijd
* 创建时间：2012-4-25 上午10:22:13 
* 修改人：yijd
* 修改时间：2012-4-25 上午10:22:13 
* 修改备注： 
* @version 
*
 */
public interface IAncdDao {
	/**
	 * 方法描述: 查询按钮菜单 老师
	 * 参数 @param ancdMode 菜单模型
	 * 返回类型 List<AncdModel> 返回类型
	 * @throws Exception
	 */
	public List<AncdModel> cxAncdLs(AncdModel ancdModel);
	
	/**
	 * 方法描述: 查询按钮菜单  学生
	 * 参数 @param ancdMode 菜单模型
	 * 返回类型 List<AncdModel> 返回类型
	 * @throws Exception
	 */
	public List<AncdModel> cxAncdXs(AncdModel ancdModel);
	
}
