package com.zfsoft.dao.daointerface;


import java.util.List;

import com.zfsoft.common.dao.BaseDao;
import com.zfsoft.dao.entities.WdyyModel;


/**
 * 
* 
* 类名称：WdyyDao 
* 类描述：我的应用DAO
* 创建人：yijd
* 创建时间：2012-5-7 上午17:22:13 
* 修改备注： 
* @version 
*
 */
public interface IWdyyDao extends BaseDao<WdyyModel>{
	/**
	 * 删除我的应用
	 * @param model  删除条件
	 * @return
	 */
	public int scWdyy(WdyyModel model);
	
	/**
	 * 查询老师我的应用
	 * @param model 查询条件
	 * @return
	 */
	public List<WdyyModel> cxLsWdyy(WdyyModel model);
	
	/**
	 * 查询学生我的应用
	 * @param model 查询条件
	 * @return
	 */
	public List<WdyyModel> cxXsWdyy(WdyyModel model);
}
