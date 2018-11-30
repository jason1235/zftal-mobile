package com.zfsoft.hrm.baseinfo.infoclass.service.svcinterface;

import java.util.List;

import com.zfsoft.hrm.baseinfo.infoclass.entities.InfoPropertyView;
import com.zfsoft.hrm.baseinfo.infoclass.query.InfoPropertyViewQuery;

/** 
 * 信息属性展示配置service
 * @author jinjj
 * @date 2012-11-13 上午10:44:33 
 *  
 */
public interface IInfoPropertyViewService {

	/**
	 * 保存
	 * @param list
	 */
	public void save(List<InfoPropertyView> list);
	
	/**
	 * 查询列表
	 * @param query
	 * @return
	 */
	public List<InfoPropertyView> getList(InfoPropertyViewQuery query);
	
	/**
	 * 删除多个
	 * @param query
	 */
	public void deleteBatch(InfoPropertyViewQuery query);
}
