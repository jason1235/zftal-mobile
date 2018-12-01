package com.zfsoft.hrm.baseinfo.infoclass.business.bizinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoPropertyView;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyViewQuery;

/** 
 * 信息属性展现配置BUSINESS
 * @author jinjj
 * @date 2012-11-13 上午10:31:02 
 *  
 */
public interface IInfoPropertyViewBusiness {

	/**
	 * 新增
	 * @param view
	 */
	public void save(InfoPropertyView view);
	
	/**
	 * 批量删除
	 * @param query
	 */
	public void deleteBatch(InfoPropertyViewQuery query);
	
	/**
	 * 查询列表
	 * @param query
	 * @return
	 */
	public List<InfoPropertyView> getList(InfoPropertyViewQuery query);
}
