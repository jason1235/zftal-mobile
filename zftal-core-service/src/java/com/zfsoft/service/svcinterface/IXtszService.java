package com.zfsoft.service.svcinterface;

import java.util.List;

import com.zfsoft.common.service.BaseService;
import com.zfsoft.dao.entities.XtszModel;



/**
 * 
* 
* 类名称：XtszService 
* 类描述： 系统设置
* 创建人：qph 
* 创建时间：2012-4-20
* 修改备注： 
*
 */
public interface IXtszService extends BaseService<XtszModel>{
	
	
	/**
	  * 
	 * 方法描述: 提供查询学年列表
	 * @param params
	 * @return list
	 * @throws java.lang.Exception
	  */
	public List cxXnlb(String[][] params) ;
	
	
	
	/**
	  * 
	 * 方法描述: 提供查询年度 列表
	 * @param params
	 * @return list
	 * @throws java.lang.Exception
	  */
	public List cxNdlb(String[][] params) ;
}
