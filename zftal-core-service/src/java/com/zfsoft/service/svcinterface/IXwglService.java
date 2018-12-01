package com.zfsoft.service.svcinterface;


import com.zfsoft.common.service.BaseService;
import com.zfsoft.dao.entities.XwglModel;


/**
 * 
* 
* 类名称：XwglService 
* 类描述： 新闻管理
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
public interface IXwglService extends BaseService<XwglModel>{

	
/*	*//**
	 * 查询单条新闻
	 * @param bean
	 * @return
	 * @
	 *//*
	public XwglModel cxDtxw(XwglModel bean) ;
*/
	
	
	/**
	 * 修改单条新闻
	 * @param bean
	 * @return
	 * @
	 */
/*	public boolean xgDtxw(XwglModel bean) ;
*/
	
	/**
	 * 新增新闻
	 * @param bean
	 * @return
	 * @
	 */
	/*public boolean zjXw(XwglModel bean) ;*/

	
	/**
	 *删除新闻
	 * @param bean
	 * @return
	 * @
	 */
	public boolean scXw(String idStr) ;
	
	
	/**
	 * 发布新闻
	 * 
	 * @param bean
	 * @return
	 * @
	 */
	public boolean xgFbxw(String idStr) ;
	
	
	/**
	 * 取消发布新闻
	 * 
	 * @param bean
	 * @return
	 * @
	 */
	public boolean xgQxfbxw(String idStr) ;
	
	
	/**
	 * 置顶新闻
	 * 
	 * @param bean
	 * @return
	 * @
	 */
	public boolean xgZdxw(String idStr) ;
	
	
	
	/**
	 * 取消置顶新闻
	 * 
	 * @param bean
	 * @return
	 * @
	 */
	public boolean xgQxzdXw(String idStr) ;
	


	/**
	 * 分页查询新闻
	 * @param model
	 * @return
	 * @
	 */
/*	public List<XwglModel> fycxXw(XwglModel model) ;*/
}
